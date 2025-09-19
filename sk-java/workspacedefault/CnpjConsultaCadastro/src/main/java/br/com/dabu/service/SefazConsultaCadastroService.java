package br.com.dabu.service;

import br.com.dabu.config.SefazEndpoints;
import br.com.dabu.model.SefazConsultaCadastroResponseJDOM;
import br.com.dabu.util.CertificadoManager;
import br.com.dabu.util.CertificadoUtil;
import br.com.dabu.util.XMLUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

/**
 * Serviço para integração com SEFAZ NfeConsultaCadastro usando JDOM
 */
public class SefazConsultaCadastroService {
    
    private static final Logger logger = LoggerFactory.getLogger(SefazConsultaCadastroService.class);
    
    private final CertificadoManager certificadoManager;
    
    public SefazConsultaCadastroService() {
        this.certificadoManager = new CertificadoManager();
    }
    
    /**
     * Inicializa certificado automaticamente da pasta resources/certificado
     * 
     * @param senha Senha do certificado
     * @return true se configurado com sucesso, false caso contrário
     */
    public boolean inicializarCertificado(String senha) {
        return certificadoManager.carregarCertificadoAutomatico(senha);
    }
    
    /**
     * Configura certificado específico da pasta resources/certificado
     * 
     * @param nomeArquivo Nome do arquivo (ex: "certificado.p12")
     * @param senha Senha do certificado
     * @return true se configurado com sucesso, false caso contrário
     */
    public boolean configurarCertificado(String nomeArquivo, String senha) {
        return certificadoManager.carregarCertificado(nomeArquivo, senha);
    }
    
    /**
     * Consulta cadastro na SEFAZ usando JDOM
     * 
     * @param uf Unidade Federativa
     * @param cnpj CNPJ a ser consultado (apenas números)
     * @return Resposta da SEFAZ ou null em caso de erro
     */
    public SefazConsultaCadastroResponseJDOM consultarCadastro(String uf, String cnpj) {
        if (uf == null || uf.trim().isEmpty()) {
            logger.error("UF não pode ser nula ou vazia");
            return null;
        }
        
        if (cnpj == null || cnpj.trim().isEmpty()) {
            logger.error("CNPJ não pode ser nulo ou vazio");
            return null;
        }
        
        if (!certificadoManager.isCertificadoCarregado()) {
            logger.error("Certificado não configurado. Configure o certificado antes de fazer consultas.");
            return null;
        }
        
        String endpoint = SefazEndpoints.getEndpointConsultaCadastro(uf.toUpperCase());
        if (endpoint == null) {
            logger.error("Endpoint não encontrado para UF: {}", uf);
            return null;
        }
        
        try {
            logger.info("Consultando cadastro na SEFAZ - UF: {}, CNPJ: {}", uf, cnpj);
            
            // Cria XML da requisição usando JDOM
            String xmlRequest = XMLUtil.criarXMLConsultaCadastro(uf.toUpperCase(), cnpj);
            if (xmlRequest == null) {
                logger.error("Erro ao criar XML da requisição");
                return null;
            }
            
            logger.info("=== XML REQUEST SEFAZ ===");
            logger.info("XML Request (tamanho: {} bytes):\n{}", xmlRequest.length(), xmlRequest);
            
            // Cria envelope SOAP usando JDOM
            String soapEnvelope = XMLUtil.criarEnvelopeSOAP(xmlRequest, uf);
            if (soapEnvelope == null) {
                logger.error("Erro ao criar envelope SOAP");
                return null;
            }
            
            logger.info("=== SOAP ENVELOPE SEFAZ ===");
            logger.info("SOAP Envelope (tamanho: {} bytes):\n{}", soapEnvelope.length(), soapEnvelope);
            
            // Executa requisição SOAP
            String xmlResponse = executarRequisicaoSOAP(endpoint, soapEnvelope, uf);
            if (xmlResponse == null) {
                return null;
            }
            
            logger.info("=== XML RESPONSE SEFAZ ===");
            logger.info("XML Response (tamanho: {} bytes):\n{}", xmlResponse.length(), xmlResponse);
            
            // Extrai corpo da resposta
            String corpoResposta = XMLUtil.extrairCorpoResposta(xmlResponse);
            
            logger.info("=== CORPO RESPOSTA SEFAZ ===");
            logger.info("Corpo Resposta (tamanho: {} bytes):\n{}", 
                corpoResposta != null ? corpoResposta.length() : 0, 
                corpoResposta);
            
            // Cria resposta usando JDOM
            SefazConsultaCadastroResponseJDOM responseJDOM = SefazConsultaCadastroResponseJDOM.fromXML(corpoResposta);
            
            logger.info("=== RESULTADO PARSING JDOM ===");
            if (responseJDOM != null) {
                logger.info("✓ Parsing JDOM realizado com sucesso");
                if (responseJDOM.getInfCons() != null) {
                    logger.info("✓ InfCons encontrado");
                    logger.info("  - UF: {}", responseJDOM.getInfCons().getUf());
                    logger.info("  - VerAplic: {}", responseJDOM.getInfCons().getVerAplic());
                    logger.info("  - CStat: {}", responseJDOM.getInfCons().getcStat());
                    logger.info("  - XMotivo: {}", responseJDOM.getInfCons().getxMotivo());
                    logger.info("  - Total InfCad: {}", 
                        responseJDOM.getInfCons().getInfCad() != null ? 
                        responseJDOM.getInfCons().getInfCad().size() : 0);
                } else {
                    logger.warn("⚠ InfCons é null");
                }
            } else {
                logger.error("✗ Falha no parsing JDOM");
            }
            
            return responseJDOM;
            
        } catch (Exception e) {
            logger.error("Erro na consulta SEFAZ - UF: {}, CNPJ: {}: {}", uf, cnpj, e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Executa requisição SOAP
     */
    private String executarRequisicaoSOAP(String endpoint, String soapEnvelope, String uf) {
        try {
            // Cria SSLContext com certificado
            SSLContext sslContext = CertificadoUtil.criarSSLContext(
                certificadoManager.getKeyStore(), 
                certificadoManager.getSenhaCertificado()
            );
            
            if (sslContext == null) {
                logger.error("Erro ao criar SSLContext");
                return null;
            }
            
            // Configura HttpClient com SSL
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
            HttpClient httpClient = HttpClientBuilder.create()
                    .setSSLSocketFactory(sslSocketFactory)
                    .build();
            
            // Cria requisição POST
            HttpPost request = new HttpPost(endpoint);
            request.setHeader("Content-Type", "text/xml; charset=utf-8");
            request.setHeader("SOAPAction", SefazEndpoints.getSoapAction(uf));
            request.setEntity(new StringEntity(soapEnvelope, "UTF-8"));
            
            logger.info("=== HTTP REQUEST SEFAZ ===");
            logger.info("Endpoint: {}", endpoint);
            logger.info("SOAPAction: {}", SefazEndpoints.getSoapAction(uf));
            logger.info("Content-Type: text/xml; charset=utf-8");
            logger.info("Request Body Size: {} bytes", soapEnvelope.length());
            
            // Executa requisição
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            
            logger.info("=== HTTP RESPONSE SEFAZ ===");
            logger.info("Status Code: {}", statusCode);
            logger.info("Status Line: {}", response.getStatusLine().toString());
            
            // Log dos headers da resposta
            logger.info("Response Headers:");
            for (org.apache.http.Header header : response.getAllHeaders()) {
                logger.info("  {}: {}", header.getName(), header.getValue());
            }
            
            if (statusCode == 200) {
                String xmlResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("Consulta SEFAZ realizada com sucesso - UF: {}", uf);
                logger.info("Response Body Size: {} bytes", xmlResponse.length());
                return xmlResponse;
            } else {
                logger.error("Erro na consulta SEFAZ - UF: {}, Status: {}", uf, statusCode);
                // Tenta ler o corpo da resposta mesmo em caso de erro
                try {
                    String errorResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
                    logger.error("Error Response Body ({} bytes):\n{}", errorResponse.length(), errorResponse);
                } catch (Exception e) {
                    logger.error("Não foi possível ler o corpo da resposta de erro: {}", e.getMessage());
                }
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Erro ao executar requisição SOAP - UF: {}: {}", uf, e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Verifica se o certificado está configurado
     */
    public boolean isCertificadoConfigurado() {
        return certificadoManager.isCertificadoCarregado();
    }
    
    /**
     * Obtém informações do certificado configurado
     */
    public String getInformacoesCertificado() {
        return certificadoManager.getInformacoesCertificado();
    }
    
    /**
     * Lista certificados disponíveis na pasta resources/certificado
     */
    public java.util.List<String> listarCertificadosDisponiveis() {
        return certificadoManager.listarCertificadosDisponiveis();
    }
}



