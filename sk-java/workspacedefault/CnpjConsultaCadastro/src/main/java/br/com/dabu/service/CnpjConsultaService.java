package br.com.dabu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dabu.model.CnpjConsultaResult;
import br.com.dabu.model.ReceitaWSResponse;
import br.com.dabu.model.SefazConsultaCadastroResponseJDOM;


/**
 * Serviço principal para consulta de CNPJ
 * Integra ReceitaWS e SEFAZ NfeConsultaCadastro usando JDOM
 */
public class CnpjConsultaService {
    
    private static final Logger logger = LoggerFactory.getLogger(CnpjConsultaService.class);
    
    private final ReceitaWSService receitaWSService;
    private final SefazConsultaCadastroService sefazService;
    
    public CnpjConsultaService() {
        this.receitaWSService = new ReceitaWSService();
        this.sefazService = new SefazConsultaCadastroService();
    }
    
    /**
     * Inicializa certificado automaticamente da pasta resources/certificado
     * 
     * @param senha Senha do certificado
     * @return true se configurado com sucesso, false caso contrário
     */
    public boolean inicializarCertificado(String senha) {
        return sefazService.inicializarCertificado(senha);
    }
    
    /**
     * Configura certificado específico da pasta resources/certificado
     * 
     * @param nomeArquivo Nome do arquivo (ex: "certificado.p12")
     * @param senha Senha do certificado
     * @return true se configurado com sucesso, false caso contrário
     */
    public boolean configurarCertificado(String nomeArquivo, String senha) {
        return sefazService.configurarCertificado(nomeArquivo, senha);
    }
    
    /**
     * Consulta completa de CNPJ integrando ReceitaWS e SEFAZ
     * 
     * @param cnpj CNPJ a ser consultado (com ou sem formatação)
     * @return Resultado consolidado da consulta
     */
    public CnpjConsultaResult consultarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            logger.error("CNPJ não pode ser nulo ou vazio");
            return criarResultadoErro(cnpj, "CNPJ não pode ser nulo ou vazio");
        }
        
        // Remove formatação do CNPJ
        String cnpjLimpo = limparCNPJ(cnpj);
        
        if (!validarCNPJ(cnpjLimpo)) {
            logger.error("CNPJ inválido: {}", cnpj);
            return criarResultadoErro(cnpj, "CNPJ inválido");
        }
        
        logger.info("Iniciando consulta completa para CNPJ: {}", cnpjLimpo);
        
        CnpjConsultaResult resultado = new CnpjConsultaResult(cnpjLimpo);
        
        try {
            // Etapa 1: Consulta na ReceitaWS
            logger.info("Etapa 1: Consultando ReceitaWS...");
            ReceitaWSResponse dadosReceita = receitaWSService.consultarCNPJ(cnpjLimpo);
            
            if (dadosReceita == null) {
                logger.warn("Não foi possível obter dados da ReceitaWS para CNPJ: {}", cnpjLimpo);
                return criarResultadoErro(cnpjLimpo, "Erro na consulta ReceitaWS");
            }
            
            resultado.setDadosReceitaWS(dadosReceita);
            
            // Extrai UF dos dados da ReceitaWS
            String uf = dadosReceita.getUf();
            if (uf == null || uf.trim().isEmpty()) {
                logger.warn("UF não encontrada nos dados da ReceitaWS para CNPJ: {}", cnpjLimpo);
                return criarResultadoErro(cnpjLimpo, "UF não encontrada nos dados da ReceitaWS");
            }
            
            logger.info("UF obtida da ReceitaWS: {}", uf);
            
            // Etapa 2: Consulta na SEFAZ usando a UF obtida
            logger.info("Etapa 2: Consultando SEFAZ para UF: {}...", uf);
            
            if (!sefazService.isCertificadoConfigurado()) {
                logger.warn("Certificado não configurado. Pulando consulta SEFAZ.");
                resultado.setMensagemErro("Certificado não configurado para consulta SEFAZ");
            } else {
                SefazConsultaCadastroResponseJDOM dadosSefaz = sefazService.consultarCadastro(uf, cnpjLimpo);
                
                if (dadosSefaz != null) {
                    resultado.setDadosSefazJDOM(dadosSefaz);
                    logger.info("Consulta SEFAZ realizada com sucesso");
                } else {
                    logger.warn("Não foi possível obter dados da SEFAZ para CNPJ: {} UF: {}", cnpjLimpo, uf);
                }
            }
            
            // Etapa 3: Consolida dados
            consolidarDados(resultado);
            
            resultado.setSucesso(true);
            logger.info("Consulta completa finalizada com sucesso para CNPJ: {}", cnpjLimpo);
            
            return resultado;
            
        } catch (Exception e) {
            logger.error("Erro inesperado na consulta de CNPJ {}: {}", cnpjLimpo, e.getMessage(), e);
            return criarResultadoErro(cnpjLimpo, "Erro inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Consolida dados da ReceitaWS e SEFAZ em campos principais
     */
    private void consolidarDados(CnpjConsultaResult resultado) {
        ReceitaWSResponse dadosReceita = resultado.getDadosReceitaWS();
        SefazConsultaCadastroResponseJDOM dadosSefaz = resultado.getDadosSefazJDOM();
        
        if (dadosReceita != null) {
            resultado.setRazaoSocial(dadosReceita.getRazaoSocial());
            resultado.setNomeFantasia(dadosReceita.getNomeFantasia());
            resultado.setSituacaoCadastral(dadosReceita.getDescricaoSituacaoCadastral());
            resultado.setUf(dadosReceita.getUf());
            resultado.setMunicipio(dadosReceita.getMunicipio());
        }
        
        if (dadosSefaz != null && dadosSefaz.getInfCons() != null && 
            dadosSefaz.getInfCons().getInfCad() != null && 
            !dadosSefaz.getInfCons().getInfCad().isEmpty()) {
            
            SefazConsultaCadastroResponseJDOM.InfCad infCad = dadosSefaz.getInfCons().getInfCad().get(0);
            
            resultado.setInscricaoEstadual(infCad.getIe());
            resultado.setSituacaoIE(obterDescricaoSituacaoIE(infCad.getcSit()));
            resultado.setCredenciadoNFe("1".equals(infCad.getIndCredNFe()));
            resultado.setCredenciadoCTe("1".equals(infCad.getIndCredCTe()));
        }
    }
    
    /**
     * Obtém descrição da situação da IE
     */
    private String obterDescricaoSituacaoIE(String cSit) {
        if (cSit == null) {
            return "Não informado";
        }
        
        switch (cSit) {
            case "0":
                return "Não habilitado";
            case "1":
                return "Habilitado";
            default:
                return "Situação " + cSit;
        }
    }
    
    /**
     * Cria resultado de erro
     */
    private CnpjConsultaResult criarResultadoErro(String cnpj, String mensagem) {
        CnpjConsultaResult resultado = new CnpjConsultaResult(cnpj);
        resultado.setSucesso(false);
        resultado.setMensagemErro(mensagem);
        return resultado;
    }
    
    /**
     * Remove formatação do CNPJ, deixando apenas números
     */
    private String limparCNPJ(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        return cnpj.replaceAll("[^0-9]", "");
    }
    
    /**
     * Valida se o CNPJ possui 14 dígitos e dígitos verificadores corretos
     */
    private boolean validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return false;
        }
        
        // Verifica se não são todos os dígitos iguais
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        
        // Valida dígitos verificadores
        return validarDigitosVerificadores(cnpj);
    }
    
    /**
     * Valida os dígitos verificadores do CNPJ
     */
    private boolean validarDigitosVerificadores(String cnpj) {
        int[] digitos = cnpj.chars().map(c -> c - '0').toArray();
        
        // Primeiro dígito verificador
        int soma = 0;
        int peso = 5;
        for (int i = 0; i < 12; i++) {
            soma += digitos[i] * peso;
            peso = (peso == 2) ? 9 : peso - 1;
        }
        int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        
        if (digitos[12] != primeiroDigito) {
            return false;
        }
        
        // Segundo dígito verificador
        soma = 0;
        peso = 6;
        for (int i = 0; i < 13; i++) {
            soma += digitos[i] * peso;
            peso = (peso == 2) ? 9 : peso - 1;
        }
        int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        
        return digitos[13] == segundoDigito;
    }
    
    /**
     * Verifica se o certificado está configurado
     */
    public boolean isCertificadoConfigurado() {
        return sefazService.isCertificadoConfigurado();
    }
    
    /**
     * Obtém informações do certificado configurado
     */
    public String getInformacoesCertificado() {
        return sefazService.getInformacoesCertificado();
    }
    
    /**
     * Lista certificados disponíveis na pasta resources/certificado
     */
    public java.util.List<String> listarCertificadosDisponiveis() {
        return sefazService.listarCertificadosDisponiveis();
    }
    
    /**
     * Consulta apenas SEFAZ para uma UF específica
     * 
     * @param uf Unidade Federativa
     * @param cnpj CNPJ a ser consultado (com ou sem formatação)
     * @return Resultado da consulta SEFAZ
     */
    public CnpjConsultaResult consultarSefaz(String uf, String cnpj) {
        if (uf == null || uf.trim().isEmpty()) {
            logger.error("UF não pode ser nula ou vazia");
            CnpjConsultaResult resultado = new CnpjConsultaResult();
            resultado.setSucesso(false);
            resultado.setMensagemErro("UF não informada");
            return resultado;
        }
        
        if (cnpj == null || cnpj.trim().isEmpty()) {
            logger.error("CNPJ não pode ser nulo ou vazio");
            CnpjConsultaResult resultado = new CnpjConsultaResult();
            resultado.setSucesso(false);
            resultado.setMensagemErro("CNPJ não informado");
            return resultado;
        }
        
        // Remove formatação
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
        
        // Valida CNPJ
        if (!validarCNPJ(cnpjLimpo)) {
            logger.error("CNPJ inválido: {}", cnpj);
            CnpjConsultaResult resultado = new CnpjConsultaResult();
            resultado.setSucesso(false);
            resultado.setMensagemErro("CNPJ inválido");
            return resultado;
        }
        
        logger.info("Consultando SEFAZ para UF: {}, CNPJ: {}", uf, cnpjLimpo);
        
        try {
            SefazConsultaCadastroResponseJDOM sefazResponse = sefazService.consultarCadastro(uf.toUpperCase(), cnpjLimpo);
            
            CnpjConsultaResult resultado = new CnpjConsultaResult(cnpjLimpo);
            
            if (sefazResponse != null) {
                logger.info("Consulta SEFAZ realizada com sucesso para UF: {}", uf);
                resultado.setSucesso(true);
                resultado.setDadosSefazJDOM(sefazResponse);
            } else {
                logger.error("Erro na consulta SEFAZ para UF: {}, CNPJ: {}", uf, cnpjLimpo);
                resultado.setSucesso(false);
                resultado.setMensagemErro("Erro na consulta SEFAZ");
            }
            
            return resultado;
            
        } catch (Exception e) {
            logger.error("Exceção na consulta SEFAZ para UF: {}, CNPJ: {}: {}", uf, cnpjLimpo, e.getMessage(), e);
            CnpjConsultaResult resultado = new CnpjConsultaResult(cnpjLimpo);
            resultado.setSucesso(false);
            resultado.setMensagemErro("Exceção na consulta SEFAZ: " + e.getMessage());
            return resultado;
        }
    }
}



