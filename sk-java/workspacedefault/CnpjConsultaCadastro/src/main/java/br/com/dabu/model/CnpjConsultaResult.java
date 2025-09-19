package br.com.dabu.model;

/**
 * Modelo para resultado consolidado da consulta de CNPJ
 * Combina informações da ReceitaWS e SEFAZ
 */
public class CnpjConsultaResult {
    
    private String cnpj;
    private boolean sucesso;
    private String mensagemErro;
    
    // Dados da ReceitaWS
    private ReceitaWSResponse dadosReceitaWS;
    
    // Dados da SEFAZ (versão JDOM)
    private SefazConsultaCadastroResponseJDOM dadosSefazJDOM;
    
    // Dados consolidados principais
    private String razaoSocial;
    private String nomeFantasia;
    private String situacaoCadastral;
    private String uf;
    private String municipio;
    private String inscricaoEstadual;
    private String situacaoIE;
    private boolean credenciadoNFe;
    private boolean credenciadoCTe;
    private String consultaTimestamp;
    
    public CnpjConsultaResult() {
    }
    
    public CnpjConsultaResult(String cnpj) {
        this.cnpj = cnpj;
    }
    
    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public boolean isSucesso() {
        return sucesso;
    }
    
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
    
    public String getMensagemErro() {
        return mensagemErro;
    }
    
    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
    
    public ReceitaWSResponse getDadosReceitaWS() {
        return dadosReceitaWS;
    }
    
    public void setDadosReceitaWS(ReceitaWSResponse dadosReceitaWS) {
        this.dadosReceitaWS = dadosReceitaWS;
    }
    
    public SefazConsultaCadastroResponseJDOM getDadosSefazJDOM() {
        return dadosSefazJDOM;
    }
    
    public void setDadosSefazJDOM(SefazConsultaCadastroResponseJDOM dadosSefazJDOM) {
        this.dadosSefazJDOM = dadosSefazJDOM;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }
    
    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }
    
    public String getUf() {
        return uf;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public String getMunicipio() {
        return municipio;
    }
    
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }
    
    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
    
    public String getSituacaoIE() {
        return situacaoIE;
    }
    
    public void setSituacaoIE(String situacaoIE) {
        this.situacaoIE = situacaoIE;
    }
    
    public boolean isCredenciadoNFe() {
        return credenciadoNFe;
    }
    
    public void setCredenciadoNFe(boolean credenciadoNFe) {
        this.credenciadoNFe = credenciadoNFe;
    }
    
    public boolean isCredenciadoCTe() {
        return credenciadoCTe;
    }
    
    public void setCredenciadoCTe(boolean credenciadoCTe) {
        this.credenciadoCTe = credenciadoCTe;
    }
    
    public String getConsultaTimestamp() {
        return consultaTimestamp;
    }
    
    public void setConsultaTimestamp(String consultaTimestamp) {
        this.consultaTimestamp = consultaTimestamp;
    }
    
    @Override
    public String toString() {
        return "CnpjConsultaResult{" +
                "cnpj='" + cnpj + '\'' +
                ", sucesso=" + sucesso +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", uf='" + uf + '\'' +
                ", municipio='" + municipio + '\'' +
                ", inscricaoEstadual='" + inscricaoEstadual + '\'' +
                ", credenciadoNFe=" + credenciadoNFe +
                '}';
    }
}



