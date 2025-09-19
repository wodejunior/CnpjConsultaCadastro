package br.com.dabu.service;

import br.com.dabu.model.ReceitaWSResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Serviço para integração com a API ReceitaWS
 */
public class ReceitaWSService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReceitaWSService.class);
    
    private static final String RECEITA_WS_BASE_URL = "https://receitaws.com.br/v1/cnpj/";
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public ReceitaWSService() {
        this.httpClient = HttpClientBuilder.create()
                .setConnectionManagerShared(true)
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Consulta dados de CNPJ na ReceitaWS
     * 
     * @param cnpj CNPJ a ser consultado (apenas números)
     * @return Resposta da ReceitaWS ou null em caso de erro
     */
    public ReceitaWSResponse consultarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            logger.error("CNPJ não pode ser nulo ou vazio");
            return null;
        }
        
        // Remove formatação do CNPJ
        String cnpjLimpo = limparCNPJ(cnpj);
        
        if (!validarCNPJ(cnpjLimpo)) {
            logger.error("CNPJ inválido: {}", cnpj);
            return null;
        }
        
        String url = RECEITA_WS_BASE_URL + cnpjLimpo;
        
        try {
            logger.info("Consultando CNPJ {} na ReceitaWS: {}", cnpjLimpo, url);
            
            HttpGet request = new HttpGet(url);
            request.setHeader("Accept", "application/json");
            request.setHeader("User-Agent", "CNPJ-Consulta-Service/1.0");
            
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            
            if (statusCode == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.debug("Resposta ReceitaWS: {}", jsonResponse);
                
                // Verifica se a resposta contém erro antes de fazer parse
                if (jsonResponse.contains("\"status\":\"ERROR\"")) {
                    logger.error("Erro na consulta ReceitaWS para CNPJ {}: {}", cnpjLimpo, jsonResponse);
                    return null;
                }
                
                ReceitaWSResponse receitaResponse = objectMapper.readValue(jsonResponse, ReceitaWSResponse.class);
                
                // Log dos dados recebidos para debug
                logger.debug("Dados ReceitaWS processados:");
                logger.debug("- CNPJ: {}", receitaResponse.getCnpj());
                logger.debug("- Razão Social: {}", receitaResponse.getRazaoSocial());
                logger.debug("- Nome Fantasia: {}", receitaResponse.getNomeFantasia());
                logger.debug("- UF: {}", receitaResponse.getUf());
                logger.debug("- Situação: {}", receitaResponse.getDescricaoSituacaoCadastral());
                
                // Verifica se campos essenciais estão presentes
                if (receitaResponse.getRazaoSocial() == null || receitaResponse.getRazaoSocial().trim().isEmpty()) {
                    logger.warn("Razão Social não encontrada na resposta ReceitaWS para CNPJ: {}", cnpjLimpo);
                }
                
                if (receitaResponse.getUf() == null || receitaResponse.getUf().trim().isEmpty()) {
                    logger.warn("UF não encontrada na resposta ReceitaWS para CNPJ: {}", cnpjLimpo);
                }
                
                logger.info("Consulta ReceitaWS realizada com sucesso para CNPJ: {}", cnpjLimpo);
                return receitaResponse;
                
            } else if (statusCode == 429) {
                logger.warn("Rate limit atingido na ReceitaWS para CNPJ: {}", cnpjLimpo);
                return null;
            } else {
                logger.error("Erro na consulta ReceitaWS para CNPJ {}: Status {}", cnpjLimpo, statusCode);
                return null;
            }
            
        } catch (IOException e) {
            logger.error("Erro de comunicação com ReceitaWS para CNPJ {}: {}", cnpjLimpo, e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Erro inesperado na consulta ReceitaWS para CNPJ {}: {}", cnpjLimpo, e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Remove formatação do CNPJ, deixando apenas números
     * 
     * @param cnpj CNPJ com ou sem formatação
     * @return CNPJ apenas com números
     */
    private String limparCNPJ(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        return cnpj.replaceAll("[^0-9]", "");
    }
    
    /**
     * Valida se o CNPJ possui 14 dígitos
     * 
     * @param cnpj CNPJ apenas com números
     * @return true se válido, false caso contrário
     */
    private boolean validarCNPJ(String cnpj) {
        return cnpj != null && cnpj.matches("\\d{14}");
    }
    
    /**
     * Formata CNPJ para exibição
     * 
     * @param cnpj CNPJ apenas com números
     * @return CNPJ formatado (XX.XXX.XXX/XXXX-XX)
     */
    public static String formatarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return cnpj;
        }
        
        return cnpj.substring(0, 2) + "." +
               cnpj.substring(2, 5) + "." +
               cnpj.substring(5, 8) + "/" +
               cnpj.substring(8, 12) + "-" +
               cnpj.substring(12, 14);
    }
    
    /**
     * Verifica se a empresa está ativa baseado na situação cadastral
     * 
     * @param response Resposta da ReceitaWS
     * @return true se ativa, false caso contrário
     */
    public static boolean isEmpresaAtiva(ReceitaWSResponse response) {
        if (response == null || response.getSituacaoCadastral() == null) {
            return false;
        }
        
        String situacao = response.getSituacaoCadastral().toLowerCase();
        return "02".equals(response.getSituacaoCadastral()) || 
               situacao.contains("ativa") || 
               situacao.contains("ativo");
    }
}



