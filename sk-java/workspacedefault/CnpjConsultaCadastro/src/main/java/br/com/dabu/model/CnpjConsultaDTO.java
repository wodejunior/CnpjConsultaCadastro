package br.com.dabu.model;


/**
 * DTO (Data Transfer Object) para facilitar a reutilização dos dados de consulta de CNPJ
 * Organiza e padroniza os dados das APIs ReceitaWS e SEFAZ
 */
public class CnpjConsultaDTO {
    
    // Dados básicos consolidados
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String situacaoCadastral;
    private String uf;
    private String municipio;
    private String consultaTimestamp;
    
    // Status das consultas
    private boolean receitaWSSucesso;
    private boolean sefazSucesso;
    private String mensagemErro;
    
    // Dados ReceitaWS
    private ReceitaWSData receitaWSData;
    
    // Dados SEFAZ
    private SefazData sefazData;
    
    // Construtor padrão
    public CnpjConsultaDTO() {
        this.receitaWSData = new ReceitaWSData();
        this.sefazData = new SefazData();
    }
    
    /**
     * Construtor que popula o DTO a partir do resultado da consulta
     */
    public CnpjConsultaDTO(CnpjConsultaResult resultado) {
        this();
        
        // Dados básicos
        this.cnpj = resultado.getCnpj();
        this.razaoSocial = resultado.getRazaoSocial();
        this.nomeFantasia = resultado.getNomeFantasia();
        this.situacaoCadastral = resultado.getSituacaoCadastral();
        this.uf = resultado.getUf();
        this.municipio = resultado.getMunicipio();
        this.consultaTimestamp = resultado.getConsultaTimestamp();
        this.mensagemErro = resultado.getMensagemErro();
        
        // Status das consultas
        this.receitaWSSucesso = resultado.getDadosReceitaWS() != null;
        this.sefazSucesso = resultado.getDadosSefazJDOM() != null;
        
        // Popula dados ReceitaWS
        if (resultado.getDadosReceitaWS() != null) {
            this.receitaWSData = new ReceitaWSData(resultado.getDadosReceitaWS());
        }
        
        // Popula dados SEFAZ
        if (resultado.getDadosSefazJDOM() != null) {
            this.sefazData = new SefazData(resultado.getDadosSefazJDOM());
        }
    }
    
    /**
     * Classe interna para dados da ReceitaWS
     */
    public static class ReceitaWSData {
        private String cnpj;
        private String razaoSocial;
        private String nomeFantasia;
        private String situacao;
        private String dataSituacao;
        private String motivoSituacao;
        private String uf;
        private String municipio;
        private String cep;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String dataInicioAtividade;
        private String porte;
        private String capitalSocial;
        private String naturezaJuridica;
        private String cnaeFiscal;
        private String cnaeFiscalDescricao;
        private Boolean opcaoPeloSimples;
        private Boolean opcaoPeloMei;
        private String dddTelefone1;
        private String dddTelefone2;
        private String codigoNaturezaJuridica;
        private String dataOpcaoPeloSimples;
        private String dataExclusaoDoSimples;
        private String situacaoEspecial;
        private String dataSituacaoEspecial;
        
        // Novos campos do JSON
        private String tipo;
        private String naturezaJuridicaCompleta;
        private String email;
        private String telefone;
        private String ultimaAtualizacao;
        private String status;
        private String efr;
        private String dddFax;
        
        // Classes internas para estruturas complexas
        private java.util.List<AtividadePrincipal> atividadePrincipal;
        private java.util.List<AtividadeSecundaria> atividadesSecundarias;
        private java.util.List<QuadroSocietario> qsa;
        private SimplesInfo simples;
        private SimeiInfo simei;
        private BillingInfo billing;
        
        public ReceitaWSData() {}
        
        public ReceitaWSData(ReceitaWSResponse response) {
            this.cnpj = response.getCnpj();
            this.razaoSocial = response.getRazaoSocial();
            this.nomeFantasia = response.getNomeFantasia();
            this.situacao = response.getDescricaoSituacaoCadastral();
            this.dataSituacao = response.getDataSituacaoCadastral();
            this.motivoSituacao = response.getMotivoSituacaoCadastral();
            this.uf = response.getUf();
            this.municipio = response.getMunicipio();
            this.cep = response.getCep();
            this.logradouro = response.getLogradouro();
            this.numero = response.getNumero();
            this.complemento = response.getComplemento();
            this.bairro = response.getBairro();
            this.dataInicioAtividade = response.getDataInicioAtividade();
            this.porte = response.getDescricaoPorte();
            this.capitalSocial = response.getCapitalSocial();
            this.naturezaJuridica = response.getCodigoNaturezaJuridica();
            this.cnaeFiscal = response.getCnaeFiscal();
            this.cnaeFiscalDescricao = response.getCnaeFiscalDescricao();
            this.opcaoPeloSimples = response.getOpcaoPeloSimples();
            this.opcaoPeloMei = response.getOpcaoPeloMei();
            this.telefone = response.getDddTelefone1();
            this.email = null; // Email não disponível na ReceitaWS
            this.dddTelefone1 = response.getDddTelefone1();
            this.dddTelefone2 = response.getDddTelefone2();
            this.codigoNaturezaJuridica = response.getCodigoNaturezaJuridica();
            this.dataOpcaoPeloSimples = response.getDataOpcaoPeloSimples();
            this.dataExclusaoDoSimples = response.getDataExclusaoDoSimples();
            this.situacaoEspecial = response.getSituacaoEspecial();
            this.dataSituacaoEspecial = response.getDataSituacaoEspecial();
            
            // Novos campos
            this.tipo = response.getTipo();
            this.naturezaJuridicaCompleta = response.getNaturezaJuridica();
            this.email = response.getEmail();
            this.telefone = response.getTelefone();
            this.ultimaAtualizacao = response.getUltimaAtualizacao();
            this.status = response.getStatus();
            this.efr = response.getEfr();
            this.dddFax = response.getDddFax();
            
            // Mapear atividades principais
            if (response.getAtividadePrincipal() != null) {
                this.atividadePrincipal = new java.util.ArrayList<>();
                for (ReceitaWSResponse.AtividadePrincipal ap : response.getAtividadePrincipal()) {
                    this.atividadePrincipal.add(new AtividadePrincipal(ap.getCode(), ap.getText()));
                }
            }
            
            // Mapear atividades secundárias
            if (response.getAtividadesSecundarias() != null) {
                this.atividadesSecundarias = new java.util.ArrayList<>();
                for (ReceitaWSResponse.AtividadeSecundaria as : response.getAtividadesSecundarias()) {
                    this.atividadesSecundarias.add(new AtividadeSecundaria(as.getCode(), as.getText()));
                }
            }
            
            // Mapear QSA
            if (response.getQsa() != null) {
                this.qsa = new java.util.ArrayList<>();
                for (ReceitaWSResponse.QuadroSocietario q : response.getQsa()) {
                    this.qsa.add(new QuadroSocietario(q.getNome(), q.getQual()));
                }
            }
            
            // Mapear Simples
            if (response.getSimples() != null) {
                this.simples = new SimplesInfo(
                    response.getSimples().getOptante(),
                    response.getSimples().getDataOpcao(),
                    response.getSimples().getDataExclusao(),
                    response.getSimples().getUltimaAtualizacao()
                );
            }
            
            // Mapear Simei
            if (response.getSimei() != null) {
                this.simei = new SimeiInfo(
                    response.getSimei().getOptante(),
                    response.getSimei().getDataOpcao(),
                    response.getSimei().getDataExclusao(),
                    response.getSimei().getUltimaAtualizacao()
                );
            }
            
            // Mapear Billing
            if (response.getBilling() != null) {
                this.billing = new BillingInfo(
                    response.getBilling().getFree(),
                    response.getBilling().getDatabase()
                );
            }
        }
        
        // Getters e Setters
        public String getCnpj() { return cnpj; }
        public void setCnpj(String cnpj) { this.cnpj = cnpj; }
        
        public String getRazaoSocial() { return razaoSocial; }
        public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
        
        public String getNomeFantasia() { return nomeFantasia; }
        public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
        
        public String getSituacao() { return situacao; }
        public void setSituacao(String situacao) { this.situacao = situacao; }
        
        public String getDataSituacao() { return dataSituacao; }
        public void setDataSituacao(String dataSituacao) { this.dataSituacao = dataSituacao; }
        
        public String getMotivoSituacao() { return motivoSituacao; }
        public void setMotivoSituacao(String motivoSituacao) { this.motivoSituacao = motivoSituacao; }
        
        public String getUf() { return uf; }
        public void setUf(String uf) { this.uf = uf; }
        
        public String getMunicipio() { return municipio; }
        public void setMunicipio(String municipio) { this.municipio = municipio; }
        
        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }
        
        public String getLogradouro() { return logradouro; }
        public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
        
        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }
        
        public String getComplemento() { return complemento; }
        public void setComplemento(String complemento) { this.complemento = complemento; }
        
        public String getBairro() { return bairro; }
        public void setBairro(String bairro) { this.bairro = bairro; }
        
        public String getDataInicioAtividade() { return dataInicioAtividade; }
        public void setDataInicioAtividade(String dataInicioAtividade) { this.dataInicioAtividade = dataInicioAtividade; }
        
        public String getPorte() { return porte; }
        public void setPorte(String porte) { this.porte = porte; }
        
        public String getCapitalSocial() { return capitalSocial; }
        public void setCapitalSocial(String capitalSocial) { this.capitalSocial = capitalSocial; }
        
        public String getNaturezaJuridica() { return naturezaJuridica; }
        public void setNaturezaJuridica(String naturezaJuridica) { this.naturezaJuridica = naturezaJuridica; }
        
        public String getCnaeFiscal() { return cnaeFiscal; }
        public void setCnaeFiscal(String cnaeFiscal) { this.cnaeFiscal = cnaeFiscal; }
        
        public String getCnaeFiscalDescricao() { return cnaeFiscalDescricao; }
        public void setCnaeFiscalDescricao(String cnaeFiscalDescricao) { this.cnaeFiscalDescricao = cnaeFiscalDescricao; }
        
        public Boolean getOpcaoPeloSimples() { return opcaoPeloSimples; }
        public void setOpcaoPeloSimples(Boolean opcaoPeloSimples) { this.opcaoPeloSimples = opcaoPeloSimples; }
        
        public Boolean getOpcaoPeloMei() { return opcaoPeloMei; }
        public void setOpcaoPeloMei(Boolean opcaoPeloMei) { this.opcaoPeloMei = opcaoPeloMei; }
        
        public String getDddTelefone1() { return dddTelefone1; }
        public void setDddTelefone1(String dddTelefone1) { this.dddTelefone1 = dddTelefone1; }
        
        public String getDddTelefone2() { return dddTelefone2; }
        public void setDddTelefone2(String dddTelefone2) { this.dddTelefone2 = dddTelefone2; }
        
        public String getCodigoNaturezaJuridica() { return codigoNaturezaJuridica; }
        public void setCodigoNaturezaJuridica(String codigoNaturezaJuridica) { this.codigoNaturezaJuridica = codigoNaturezaJuridica; }
        
        public String getDataOpcaoPeloSimples() { return dataOpcaoPeloSimples; }
        public void setDataOpcaoPeloSimples(String dataOpcaoPeloSimples) { this.dataOpcaoPeloSimples = dataOpcaoPeloSimples; }
        
        public String getDataExclusaoDoSimples() { return dataExclusaoDoSimples; }
        public void setDataExclusaoDoSimples(String dataExclusaoDoSimples) { this.dataExclusaoDoSimples = dataExclusaoDoSimples; }
        
        public String getSituacaoEspecial() { return situacaoEspecial; }
        public void setSituacaoEspecial(String situacaoEspecial) { this.situacaoEspecial = situacaoEspecial; }
        
        public String getDataSituacaoEspecial() { return dataSituacaoEspecial; }
        public void setDataSituacaoEspecial(String dataSituacaoEspecial) { this.dataSituacaoEspecial = dataSituacaoEspecial; }
        
        // Getters e Setters para novos campos
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
        
        public String getNaturezaJuridicaCompleta() { return naturezaJuridicaCompleta; }
        public void setNaturezaJuridicaCompleta(String naturezaJuridicaCompleta) { this.naturezaJuridicaCompleta = naturezaJuridicaCompleta; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getTelefone() { return telefone; }
        public void setTelefone(String telefone) { this.telefone = telefone; }
        
        public String getUltimaAtualizacao() { return ultimaAtualizacao; }
        public void setUltimaAtualizacao(String ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getEfr() { return efr; }
        public void setEfr(String efr) { this.efr = efr; }
        
        public String getDddFax() { return dddFax; }
        public void setDddFax(String dddFax) { this.dddFax = dddFax; }
        
        public java.util.List<AtividadePrincipal> getAtividadePrincipal() { return atividadePrincipal; }
        public void setAtividadePrincipal(java.util.List<AtividadePrincipal> atividadePrincipal) { this.atividadePrincipal = atividadePrincipal; }
        
        public java.util.List<AtividadeSecundaria> getAtividadesSecundarias() { return atividadesSecundarias; }
        public void setAtividadesSecundarias(java.util.List<AtividadeSecundaria> atividadesSecundarias) { this.atividadesSecundarias = atividadesSecundarias; }
        
        public java.util.List<QuadroSocietario> getQsa() { return qsa; }
        public void setQsa(java.util.List<QuadroSocietario> qsa) { this.qsa = qsa; }
        
        public SimplesInfo getSimples() { return simples; }
        public void setSimples(SimplesInfo simples) { this.simples = simples; }
        
        public SimeiInfo getSimei() { return simei; }
        public void setSimei(SimeiInfo simei) { this.simei = simei; }
        
        public BillingInfo getBilling() { return billing; }
        public void setBilling(BillingInfo billing) { this.billing = billing; }
    }
    
    /**
     * Classe interna para dados da SEFAZ
     */
    public static class SefazData {
        private String ufConsultada;
        private String ambiente;
        private String versao;
        private String cnpj;
        private String inscricaoEstadual;
        private String situacaoIE;
        private String dataSituacaoIE;
        private boolean credenciadoNFe;
        private boolean credenciadoCTe;
        private boolean credenciadoMDFe;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String cep;
        private String municipio;
        private String codigoMunicipio;
        private String uf;
        
        public SefazData() {}
        
        public SefazData(SefazConsultaCadastroResponseJDOM response) {
            if (response.getInfCons() != null) {
                this.ufConsultada = response.getInfCons().getUf();
                this.ambiente = response.getInfCons().getVerAplic(); // Usando método disponível
                this.versao = response.getInfCons().getVerAplic(); // Usando método disponível
                
                if (response.getInfCons().getInfCad() != null && !response.getInfCons().getInfCad().isEmpty()) {
                    SefazConsultaCadastroResponseJDOM.InfCad infCad = response.getInfCons().getInfCad().get(0);
                    
                    this.cnpj = infCad.getCnpj();
                    this.inscricaoEstadual = infCad.getIe();
                    this.situacaoIE = obterDescricaoSituacaoIE(infCad.getcSit());
                    this.dataSituacaoIE = infCad.getdUltSit(); // Usando método disponível
                    this.credenciadoNFe = "1".equals(infCad.getIndCredNFe());
                    this.credenciadoCTe = "1".equals(infCad.getIndCredCTe());
                    this.credenciadoMDFe = false; // Não disponível no modelo atual
                    
                    if (infCad.getEnder() != null) {
                        this.logradouro = infCad.getEnder().getxLgr();
                        this.numero = infCad.getEnder().getNro();
                        this.complemento = infCad.getEnder().getxCpl();
                        this.bairro = infCad.getEnder().getxBairro();
                        this.cep = infCad.getEnder().getCep();
                        this.municipio = infCad.getEnder().getxMun();
                        this.codigoMunicipio = infCad.getEnder().getcMun();
                        this.uf = null; // UF não disponível no endereço
                    }
                }
            }
        }
        
        private String obterDescricaoSituacaoIE(String cSit) {
            if (cSit == null) return "Não informado";
            switch (cSit) {
                case "0": return "Não habilitado";
                case "1": return "Habilitado";
                default: return "Situação " + cSit;
            }
        }
        
        // Getters e Setters
        public String getUfConsultada() { return ufConsultada; }
        public void setUfConsultada(String ufConsultada) { this.ufConsultada = ufConsultada; }
        
        public String getAmbiente() { return ambiente; }
        public void setAmbiente(String ambiente) { this.ambiente = ambiente; }
        
        public String getVersao() { return versao; }
        public void setVersao(String versao) { this.versao = versao; }
        
        public String getCnpj() { return cnpj; }
        public void setCnpj(String cnpj) { this.cnpj = cnpj; }
        
        public String getInscricaoEstadual() { return inscricaoEstadual; }
        public void setInscricaoEstadual(String inscricaoEstadual) { this.inscricaoEstadual = inscricaoEstadual; }
        
        public String getSituacaoIE() { return situacaoIE; }
        public void setSituacaoIE(String situacaoIE) { this.situacaoIE = situacaoIE; }
        
        public String getDataSituacaoIE() { return dataSituacaoIE; }
        public void setDataSituacaoIE(String dataSituacaoIE) { this.dataSituacaoIE = dataSituacaoIE; }
        
        public boolean isCredenciadoNFe() { return credenciadoNFe; }
        public void setCredenciadoNFe(boolean credenciadoNFe) { this.credenciadoNFe = credenciadoNFe; }
        
        public boolean isCredenciadoCTe() { return credenciadoCTe; }
        public void setCredenciadoCTe(boolean credenciadoCTe) { this.credenciadoCTe = credenciadoCTe; }
        
        public boolean isCredenciadoMDFe() { return credenciadoMDFe; }
        public void setCredenciadoMDFe(boolean credenciadoMDFe) { this.credenciadoMDFe = credenciadoMDFe; }
        
        public String getLogradouro() { return logradouro; }
        public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
        
        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }
        
        public String getComplemento() { return complemento; }
        public void setComplemento(String complemento) { this.complemento = complemento; }
        
        public String getBairro() { return bairro; }
        public void setBairro(String bairro) { this.bairro = bairro; }
        
        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }
        
        public String getMunicipio() { return municipio; }
        public void setMunicipio(String municipio) { this.municipio = municipio; }
        
        public String getCodigoMunicipio() { return codigoMunicipio; }
        public void setCodigoMunicipio(String codigoMunicipio) { this.codigoMunicipio = codigoMunicipio; }
        
        public String getUf() { return uf; }
        public void setUf(String uf) { this.uf = uf; }
    }
    
    // Getters e Setters principais
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    
    public String getSituacaoCadastral() { return situacaoCadastral; }
    public void setSituacaoCadastral(String situacaoCadastral) { this.situacaoCadastral = situacaoCadastral; }
    
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    
    public String getConsultaTimestamp() { return consultaTimestamp; }
    public void setConsultaTimestamp(String consultaTimestamp) { this.consultaTimestamp = consultaTimestamp; }
    
    public boolean isReceitaWSSucesso() { return receitaWSSucesso; }
    public void setReceitaWSSucesso(boolean receitaWSSucesso) { this.receitaWSSucesso = receitaWSSucesso; }
    
    public boolean isSefazSucesso() { return sefazSucesso; }
    public void setSefazSucesso(boolean sefazSucesso) { this.sefazSucesso = sefazSucesso; }
    
    public String getMensagemErro() { return mensagemErro; }
    public void setMensagemErro(String mensagemErro) { this.mensagemErro = mensagemErro; }
    
    public ReceitaWSData getReceitaWSData() { return receitaWSData; }
    public void setReceitaWSData(ReceitaWSData receitaWSData) { this.receitaWSData = receitaWSData; }
    
    public SefazData getSefazData() { return sefazData; }
    public void setSefazData(SefazData sefazData) { this.sefazData = sefazData; }
    
    /**
     * Retorna um resumo das consultas realizadas
     */
    public String getResumoConsultas() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("=== RESUMO DAS CONSULTAS ===\n");
        resumo.append("CNPJ: ").append(cnpj).append("\n");
        resumo.append("ReceitaWS: ").append(receitaWSSucesso ? "✓ Sucesso" : "✗ Erro").append("\n");
        resumo.append("SEFAZ: ").append(sefazSucesso ? "✓ Sucesso" : "✗ Erro").append("\n");
        resumo.append("Timestamp: ").append(consultaTimestamp != null ? consultaTimestamp : "N/A").append("\n");
        
        if (mensagemErro != null && !mensagemErro.isEmpty()) {
            resumo.append("Erro: ").append(mensagemErro).append("\n");
        }
        
        return resumo.toString();
    }
    
    /**
     * Verifica se ambas as consultas foram bem-sucedidas
     */
    public boolean isConsultaCompleta() {
        return receitaWSSucesso && sefazSucesso;
    }
    
    /**
     * Verifica se pelo menos uma consulta foi bem-sucedida
     */
    public boolean isConsultaParcial() {
        return receitaWSSucesso || sefazSucesso;
    }
    
    // Classes internas para estruturas complexas
    public static class AtividadePrincipal {
        private String code;
        private String text;
        
        public AtividadePrincipal() {}
        
        public AtividadePrincipal(String code, String text) {
            this.code = code;
            this.text = text;
        }
        
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
    
    public static class AtividadeSecundaria {
        private String code;
        private String text;
        
        public AtividadeSecundaria() {}
        
        public AtividadeSecundaria(String code, String text) {
            this.code = code;
            this.text = text;
        }
        
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
    
    public static class QuadroSocietario {
        private String nome;
        private String qual;
        
        public QuadroSocietario() {}
        
        public QuadroSocietario(String nome, String qual) {
            this.nome = nome;
            this.qual = qual;
        }
        
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        
        public String getQual() { return qual; }
        public void setQual(String qual) { this.qual = qual; }
    }
    
    public static class SimplesInfo {
        private Boolean optante;
        private String dataOpcao;
        private String dataExclusao;
        private String ultimaAtualizacao;
        
        public SimplesInfo() {}
        
        public SimplesInfo(Boolean optante, String dataOpcao, String dataExclusao, String ultimaAtualizacao) {
            this.optante = optante;
            this.dataOpcao = dataOpcao;
            this.dataExclusao = dataExclusao;
            this.ultimaAtualizacao = ultimaAtualizacao;
        }
        
        public Boolean getOptante() { return optante; }
        public void setOptante(Boolean optante) { this.optante = optante; }
        
        public String getDataOpcao() { return dataOpcao; }
        public void setDataOpcao(String dataOpcao) { this.dataOpcao = dataOpcao; }
        
        public String getDataExclusao() { return dataExclusao; }
        public void setDataExclusao(String dataExclusao) { this.dataExclusao = dataExclusao; }
        
        public String getUltimaAtualizacao() { return ultimaAtualizacao; }
        public void setUltimaAtualizacao(String ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
    }
    
    public static class SimeiInfo {
        private Boolean optante;
        private String dataOpcao;
        private String dataExclusao;
        private String ultimaAtualizacao;
        
        public SimeiInfo() {}
        
        public SimeiInfo(Boolean optante, String dataOpcao, String dataExclusao, String ultimaAtualizacao) {
            this.optante = optante;
            this.dataOpcao = dataOpcao;
            this.dataExclusao = dataExclusao;
            this.ultimaAtualizacao = ultimaAtualizacao;
        }
        
        public Boolean getOptante() { return optante; }
        public void setOptante(Boolean optante) { this.optante = optante; }
        
        public String getDataOpcao() { return dataOpcao; }
        public void setDataOpcao(String dataOpcao) { this.dataOpcao = dataOpcao; }
        
        public String getDataExclusao() { return dataExclusao; }
        public void setDataExclusao(String dataExclusao) { this.dataExclusao = dataExclusao; }
        
        public String getUltimaAtualizacao() { return ultimaAtualizacao; }
        public void setUltimaAtualizacao(String ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
    }
    
    public static class BillingInfo {
        private Boolean free;
        private Boolean database;
        
        public BillingInfo() {}
        
        public BillingInfo(Boolean free, Boolean database) {
            this.free = free;
            this.database = database;
        }
        
        public Boolean getFree() { return free; }
        public void setFree(Boolean free) { this.free = free; }
        
        public Boolean getDatabase() { return database; }
        public void setDatabase(Boolean database) { this.database = database; }
    }
}




