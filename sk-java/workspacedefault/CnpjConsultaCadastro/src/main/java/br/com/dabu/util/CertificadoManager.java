package br.com.dabu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;

/**
 * Gerenciador de certificados que carrega automaticamente da pasta resources/certificado
 */
public class CertificadoManager {
    
    private static final Logger logger = LoggerFactory.getLogger(CertificadoManager.class);
    
    private static final String CERTIFICADO_PATH = "/certificado/";
    private static final List<String> NOMES_PADRAO = Arrays.asList(
        "certificado.p12",
        "certificado.pfx",
        "homologacao.p12",
        "homologacao.pfx",
        "WJC_SISTEMAS_LTDA_51926609000127.pfx"
    );
    
    private KeyStore keyStore;
    private String senhaCertificado;
    private String nomeCertificado;
    
    /**
     * Carrega certificado automaticamente da pasta resources/certificado
     * Tenta diferentes nomes de arquivo padrão
     * 
     * @param senha Senha do certificado
     * @return true se carregou com sucesso, false caso contrário
     */
    public boolean carregarCertificadoAutomatico(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            logger.error("Senha do certificado não pode ser nula ou vazia");
            return false;
        }
        
        logger.info("Procurando certificado na pasta resources/certificado...");
        
        // Tenta carregar certificados com nomes padrão
        for (String nomeArquivo : NOMES_PADRAO) {
            String caminhoCompleto = CERTIFICADO_PATH + nomeArquivo;
            
            try (InputStream inputStream = getClass().getResourceAsStream(caminhoCompleto)) {
                if (inputStream != null) {
                    logger.info("Encontrado certificado: {}", nomeArquivo);
                    
                    KeyStore ks = KeyStore.getInstance("PKCS12");
                    ks.load(inputStream, senha.toCharArray());
                    
                    // Valida certificado
                    if (CertificadoUtil.validarCertificado(ks)) {
                        this.keyStore = ks;
                        this.senhaCertificado = senha;
                        this.nomeCertificado = nomeArquivo;
                        
                        logger.info("Certificado {} carregado e validado com sucesso", nomeArquivo);
                        logger.info("Informações do certificado:\n{}", 
                            CertificadoUtil.obterInformacoesCertificado(ks));
                        
                        return true;
                    } else {
                        logger.warn("Certificado {} inválido ou expirado", nomeArquivo);
                    }
                }
            } catch (Exception e) {
                logger.debug("Erro ao tentar carregar {}: {}", nomeArquivo, e.getMessage());
            }
        }
        
        logger.warn("Nenhum certificado válido encontrado na pasta resources/certificado");
        logger.info("Certificados procurados: {}", NOMES_PADRAO);
        logger.info("Coloque seu certificado A1 (.p12 ou .pfx) na pasta src/main/resources/certificado/");
        
        return false;
    }
    
    /**
     * Carrega certificado de um arquivo específico na pasta resources/certificado
     * 
     * @param nomeArquivo Nome do arquivo (ex: "meu-certificado.p12")
     * @param senha Senha do certificado
     * @return true se carregou com sucesso, false caso contrário
     */
    public boolean carregarCertificado(String nomeArquivo, String senha) {
        if (nomeArquivo == null || nomeArquivo.trim().isEmpty()) {
            logger.error("Nome do arquivo não pode ser nulo ou vazio");
            return false;
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            logger.error("Senha do certificado não pode ser nula ou vazia");
            return false;
        }
        
        String caminhoCompleto = CERTIFICADO_PATH + nomeArquivo;
        
        try (InputStream inputStream = getClass().getResourceAsStream(caminhoCompleto)) {
            if (inputStream == null) {
                logger.error("Certificado não encontrado: {}", caminhoCompleto);
                return false;
            }
            
            logger.info("Carregando certificado: {}", nomeArquivo);
            
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(inputStream, senha.toCharArray());
            
            // Valida certificado
            if (CertificadoUtil.validarCertificado(ks)) {
                this.keyStore = ks;
                this.senhaCertificado = senha;
                this.nomeCertificado = nomeArquivo;
                
                logger.info("Certificado {} carregado e validado com sucesso", nomeArquivo);
                logger.info("Informações do certificado:\n{}", 
                    CertificadoUtil.obterInformacoesCertificado(ks));
                
                return true;
            } else {
                logger.error("Certificado {} inválido ou expirado", nomeArquivo);
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Erro ao carregar certificado {}: {}", nomeArquivo, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Lista certificados disponíveis na pasta resources/certificado
     * 
     * @return Lista com nomes dos certificados encontrados
     */
    public List<String> listarCertificadosDisponiveis() {
        // Como estamos usando resources, não podemos listar dinamicamente
        // Retorna os nomes padrão que são procurados
        return NOMES_PADRAO;
    }
    
    /**
     * Verifica se há certificado carregado
     */
    public boolean isCertificadoCarregado() {
        return keyStore != null;
    }
    
    /**
     * Obtém o KeyStore carregado
     */
    public KeyStore getKeyStore() {
        return keyStore;
    }
    
    /**
     * Obtém a senha do certificado
     */
    public String getSenhaCertificado() {
        return senhaCertificado;
    }
    
    /**
     * Obtém o nome do certificado carregado
     */
    public String getNomeCertificado() {
        return nomeCertificado;
    }
    
    /**
     * Obtém informações do certificado carregado
     */
    public String getInformacoesCertificado() {
        if (keyStore == null) {
            return "Nenhum certificado carregado";
        }
        
        StringBuilder info = new StringBuilder();
        info.append("Certificado: ").append(nomeCertificado).append("\n");
        info.append(CertificadoUtil.obterInformacoesCertificado(keyStore));
        
        return info.toString();
    }
    
    /**
     * Limpa certificado carregado
     */
    public void limparCertificado() {
        this.keyStore = null;
        this.senhaCertificado = null;
        this.nomeCertificado = null;
        logger.info("Certificado removido da memória");
    }
}

