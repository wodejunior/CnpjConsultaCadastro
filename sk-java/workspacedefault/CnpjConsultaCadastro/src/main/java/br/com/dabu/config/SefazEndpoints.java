package br.com.dabu.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuração completa dos endpoints da SEFAZ para consulta cadastro por UF
 * Inclui ambientes de PRODUÇÃO e HOMOLOGAÇÃO para todas as UFs brasileiras
 */
public class SefazEndpoints {
    
    // Endpoints de PRODUÇÃO
    private static final Map<String, String> ENDPOINTS_PRODUCAO = new HashMap<>();
    
    // Endpoints de HOMOLOGAÇÃO
    private static final Map<String, String> ENDPOINTS_HOMOLOGACAO = new HashMap<>();
    
    static {
        // === PRODUÇÃO ===
        // UFs que utilizam SVRS (Sefaz Virtual do RS)
        String svrsProducao = "https://nfe.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro4.asmx";
        ENDPOINTS_PRODUCAO.put("AC", svrsProducao);
        ENDPOINTS_PRODUCAO.put("AL", svrsProducao);
        ENDPOINTS_PRODUCAO.put("AP", svrsProducao);
        ENDPOINTS_PRODUCAO.put("CE", svrsProducao);
        ENDPOINTS_PRODUCAO.put("DF", svrsProducao);
        ENDPOINTS_PRODUCAO.put("ES", svrsProducao);
        ENDPOINTS_PRODUCAO.put("MA", svrsProducao);
        ENDPOINTS_PRODUCAO.put("PA", svrsProducao);
        ENDPOINTS_PRODUCAO.put("PB", svrsProducao);
        ENDPOINTS_PRODUCAO.put("PI", svrsProducao);
        ENDPOINTS_PRODUCAO.put("RJ", svrsProducao);
        ENDPOINTS_PRODUCAO.put("RN", svrsProducao);
        ENDPOINTS_PRODUCAO.put("RO", svrsProducao);
        ENDPOINTS_PRODUCAO.put("RR", svrsProducao);
        ENDPOINTS_PRODUCAO.put("SC", svrsProducao);
        ENDPOINTS_PRODUCAO.put("SE", svrsProducao);
        ENDPOINTS_PRODUCAO.put("TO", svrsProducao);
        
        // UFs com endpoints próprios
        ENDPOINTS_PRODUCAO.put("AM", "https://nfe.sefaz.am.gov.br/services2/services/CadConsultaCadastro4");
        ENDPOINTS_PRODUCAO.put("BA", "https://nfe.sefaz.ba.gov.br/webservices/CadConsultaCadastro4/CadConsultaCadastro4.asmx");
        ENDPOINTS_PRODUCAO.put("GO", "https://nfe.sefaz.go.gov.br/nfe/services/CadConsultaCadastro4?wsdl");
        ENDPOINTS_PRODUCAO.put("MT", "https://nfe.sefaz.mt.gov.br/nfews/v2/services/CadConsultaCadastro4?wsdl");
        ENDPOINTS_PRODUCAO.put("MS", "https://nfe.sefaz.ms.gov.br/ws/CadConsultaCadastro4");
        ENDPOINTS_PRODUCAO.put("MG", "https://nfe.fazenda.mg.gov.br/nfe2/services/CadConsultaCadastro4");
        ENDPOINTS_PRODUCAO.put("PR", "https://nfe.sefa.pr.gov.br/nfe/CadConsultaCadastro4?wsdl");
        ENDPOINTS_PRODUCAO.put("PE", "https://nfe.sefaz.pe.gov.br/nfe-service/services/CadConsultaCadastro4?wsdl");
        ENDPOINTS_PRODUCAO.put("RS", "https://cad.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro4.asmx");
        ENDPOINTS_PRODUCAO.put("SP", "https://nfe.fazenda.sp.gov.br/ws/cadconsultacadastro4.asmx");
        
        // === HOMOLOGAÇÃO ===
        // UFs que utilizam SVRS (Sefaz Virtual do RS)
        String svrsHomologacao = "https://nfe-homologacao.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro4.asmx";
        ENDPOINTS_HOMOLOGACAO.put("AC", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("AL", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("AP", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("CE", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("DF", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("ES", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("MA", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("PA", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("PB", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("PI", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("RJ", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("RN", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("RO", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("RR", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("SC", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("SE", svrsHomologacao);
        ENDPOINTS_HOMOLOGACAO.put("TO", svrsHomologacao);
        
        // UFs com endpoints próprios de homologação
        ENDPOINTS_HOMOLOGACAO.put("AM", "https://homnfe.sefaz.am.gov.br/services2/services/CadConsultaCadastro4");
        ENDPOINTS_HOMOLOGACAO.put("BA", "https://hnfe.sefaz.ba.gov.br/webservices/CadConsultaCadastro4/CadConsultaCadastro4.asmx");
        ENDPOINTS_HOMOLOGACAO.put("GO", "https://homolog.sefaz.go.gov.br/nfe/services/CadConsultaCadastro4?wsdl");
        ENDPOINTS_HOMOLOGACAO.put("MT", "https://homologacao.sefaz.mt.gov.br/nfews/v2/services/CadConsultaCadastro4?wsdl");
        ENDPOINTS_HOMOLOGACAO.put("MS", "https://homologacao.sefaz.ms.gov.br/ws/CadConsultaCadastro4");
        ENDPOINTS_HOMOLOGACAO.put("MG", "https://hnfe.fazenda.mg.gov.br/nfe2/services/CadConsultaCadastro4");
        ENDPOINTS_HOMOLOGACAO.put("PR", "https://homologacao.nfe.sefa.pr.gov.br/nfe/CadConsultaCadastro4?wsdl");
        ENDPOINTS_HOMOLOGACAO.put("PE", "https://nfehomolog.sefaz.pe.gov.br/nfe-service/services/CadConsultaCadastro4?wsdl");
        ENDPOINTS_HOMOLOGACAO.put("RS", "https://cad-homologacao.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro4.asmx");
        ENDPOINTS_HOMOLOGACAO.put("SP", "https://homologacao.nfe.fazenda.sp.gov.br/ws/cadconsultacadastro4.asmx");
    }
    
    /**
     * Obtém o endpoint de consulta cadastro para uma UF específica (PRODUÇÃO)
     * 
     * @param uf Unidade Federativa (sigla)
     * @return URL do endpoint ou null se UF não encontrada
     */
    public static String getEndpointConsultaCadastro(String uf) {
        return getEndpointConsultaCadastro(uf, true);
    }
    
    /**
     * Obtém o endpoint de consulta cadastro para uma UF específica
     * 
     * @param uf Unidade Federativa (sigla)
     * @param producao true para produção, false para homologação
     * @return URL do endpoint ou null se UF não encontrada
     */
    public static String getEndpointConsultaCadastro(String uf, boolean producao) {
        if (uf == null) {
            return null;
        }
        
        String ufUpper = uf.toUpperCase();
        if (producao) {
            return ENDPOINTS_PRODUCAO.get(ufUpper);
        } else {
            return ENDPOINTS_HOMOLOGACAO.get(ufUpper);
        }
    }
    
    /**
     * Verifica se uma UF possui endpoint de consulta cadastro
     * 
     * @param uf Unidade Federativa (sigla)
     * @return true se possui endpoint, false caso contrário
     */
    public static boolean hasEndpointConsultaCadastro(String uf) {
        return hasEndpointConsultaCadastro(uf, true);
    }
    
    /**
     * Verifica se uma UF possui endpoint de consulta cadastro
     * 
     * @param uf Unidade Federativa (sigla)
     * @param producao true para produção, false para homologação
     * @return true se possui endpoint, false caso contrário
     */
    public static boolean hasEndpointConsultaCadastro(String uf, boolean producao) {
        if (uf == null) {
            return false;
        }
        
        String ufUpper = uf.toUpperCase();
        if (producao) {
            return ENDPOINTS_PRODUCAO.containsKey(ufUpper);
        } else {
            return ENDPOINTS_HOMOLOGACAO.containsKey(ufUpper);
        }
    }
    
    /**
     * Obtém todas as UFs disponíveis para consulta
     * 
     * @return Array com as siglas das UFs
     */
    public static String[] getUFsDisponiveis() {
        return ENDPOINTS_PRODUCAO.keySet().toArray(new String[0]);
    }
    
    /**
     * Verifica se uma UF utiliza SVRS (Sefaz Virtual do RS)
     * 
     * @param uf Unidade Federativa (sigla)
     * @return true se utiliza SVRS, false caso contrário
     */
    public static boolean isSVRS(String uf) {
        return isSVRS(uf, true);
    }
    
    /**
     * Verifica se uma UF utiliza SVRS (Sefaz Virtual do RS)
     * 
     * @param uf Unidade Federativa (sigla)
     * @param producao true para produção, false para homologação
     * @return true se utiliza SVRS, false caso contrário
     */
    public static boolean isSVRS(String uf, boolean producao) {
        if (uf == null) {
            return false;
        }
        
        String endpoint = getEndpointConsultaCadastro(uf.toUpperCase(), producao);
        return endpoint != null && endpoint.contains("svrs.rs.gov.br");
    }
    
    /**
     * Obtém o namespace XML para a UF
     * 
     * @param uf Unidade Federativa (sigla)
     * @return Namespace XML
     */
    public static String getNamespace(String uf) {
        // Namespace padrão para consulta cadastro
        return "http://www.portalfiscal.inf.br/nfe";
    }
    
    /**
     * Obtém a ação SOAP para consulta cadastro
     * 
     * @param uf Unidade Federativa (sigla)
     * @return Ação SOAP
     */
    public static String getSoapAction(String uf) {
        // Ação SOAP padrão para consulta cadastro
        return "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4/consultaCadastro";
    }
    
    /**
     * Lista todas as UFs com seus endpoints de produção
     * 
     * @return Map com UF -> Endpoint de produção
     */
    public static Map<String, String> getEndpointsProducao() {
        return new HashMap<>(ENDPOINTS_PRODUCAO);
    }
    
    /**
     * Lista todas as UFs com seus endpoints de homologação
     * 
     * @return Map com UF -> Endpoint de homologação
     */
    public static Map<String, String> getEndpointsHomologacao() {
        return new HashMap<>(ENDPOINTS_HOMOLOGACAO);
    }
    
    /**
     * Obtém informações detalhadas sobre uma UF
     * 
     * @param uf Unidade Federativa (sigla)
     * @return String com informações da UF
     */
    public static String getInfoUF(String uf) {
        if (uf == null) {
            return "UF não informada";
        }
        
        String ufUpper = uf.toUpperCase();
        boolean temProducao = ENDPOINTS_PRODUCAO.containsKey(ufUpper);
        boolean temHomologacao = ENDPOINTS_HOMOLOGACAO.containsKey(ufUpper);
        boolean usaSVRS = isSVRS(ufUpper);
        
        StringBuilder info = new StringBuilder();
        info.append("UF: ").append(ufUpper).append("\n");
        info.append("Produção: ").append(temProducao ? "✓" : "✗").append("\n");
        info.append("Homologação: ").append(temHomologacao ? "✓" : "✗").append("\n");
        info.append("Usa SVRS: ").append(usaSVRS ? "✓" : "✗").append("\n");
        
        if (temProducao) {
            info.append("Endpoint Produção: ").append(ENDPOINTS_PRODUCAO.get(ufUpper)).append("\n");
        }
        
        if (temHomologacao) {
            info.append("Endpoint Homologação: ").append(ENDPOINTS_HOMOLOGACAO.get(ufUpper));
        }
        
        return info.toString();
    }
}

