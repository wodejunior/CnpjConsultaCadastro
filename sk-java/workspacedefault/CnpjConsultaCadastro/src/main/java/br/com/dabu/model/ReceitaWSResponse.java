package br.com.dabu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Modelo para resposta da API ReceitaWS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceitaWSResponse {

    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("identificador_matriz_filial")
    private String identificadorMatrizFilial;

    @JsonProperty("descricao_matriz_filial")
    private String descricaoMatrizFilial;

    @JsonProperty("nome")
    private String razaoSocial;

    @JsonProperty("fantasia")
    private String nomeFantasia;

    @JsonProperty("situacao_cadastral")
    private String situacaoCadastral;

    @JsonProperty("situacao")
    private String descricaoSituacaoCadastral;

    @JsonProperty("data_situacao")
    private String dataSituacaoCadastral;

    @JsonProperty("motivo_situacao")
    private String motivoSituacaoCadastral;

    @JsonProperty("nome_cidade_exterior")
    private String nomeCidadeExterior;

    @JsonProperty("codigo_natureza_juridica")
    private String codigoNaturezaJuridica;

    @JsonProperty("abertura")
    private String dataInicioAtividade;

    @JsonProperty("cnae_fiscal")
    private String cnaeFiscal;

    @JsonProperty("cnae_fiscal_descricao")
    private String cnaeFiscalDescricao;

    @JsonProperty("descricao_tipo_logradouro")
    private String descricaoTipoLogradouro;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("numero")
    private String numero;

    @JsonProperty("complemento")
    private String complemento;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("codigo_municipio")
    private String codigoMunicipio;

    @JsonProperty("municipio")
    private String municipio;

    @JsonProperty("ddd_telefone_1")
    private String dddTelefone1;

    @JsonProperty("ddd_telefone_2")
    private String dddTelefone2;

    @JsonProperty("ddd_fax")
    private String dddFax;

    @JsonProperty("qualificacao_do_responsavel")
    private String qualificacaoDoResponsavel;

    @JsonProperty("capital_social")
    private String capitalSocial;

    @JsonProperty("porte")
    private String descricaoPorte;

    @JsonProperty("opcao_pelo_simples")
    private Boolean opcaoPeloSimples;

    @JsonProperty("data_opcao_pelo_simples")
    private String dataOpcaoPeloSimples;

    @JsonProperty("data_exclusao_do_simples")
    private String dataExclusaoDoSimples;

    @JsonProperty("opcao_pelo_mei")
    private Boolean opcaoPeloMei;

    @JsonProperty("situacao_especial")
    private String situacaoEspecial;

    @JsonProperty("data_situacao_especial")
    private String dataSituacaoEspecial;

    @JsonProperty("cnaes_secundarios")
    private List<CnaeSecundario> cnaesSecundarios;

    @JsonProperty("qsa")
    private List<QuadroSocietario> qsa;

    // Novos campos do JSON
    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("natureza_juridica")
    private String naturezaJuridica;

    @JsonProperty("atividade_principal")
    private List<AtividadePrincipal> atividadePrincipal;

    @JsonProperty("atividades_secundarias")
    private List<AtividadeSecundaria> atividadesSecundarias;

    @JsonProperty("email")
    private String email;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("ultima_atualizacao")
    private String ultimaAtualizacao;

    @JsonProperty("status")
    private String status;

    @JsonProperty("efr")
    private String efr;

    @JsonProperty("simples")
    private Simples simples;

    @JsonProperty("simei")
    private Simei simei;

    @JsonProperty("extra")
    private Object extra;

    @JsonProperty("billing")
    private Billing billing;

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIdentificadorMatrizFilial() {
        return identificadorMatrizFilial;
    }

    public void setIdentificadorMatrizFilial(String identificadorMatrizFilial) {
        this.identificadorMatrizFilial = identificadorMatrizFilial;
    }

    public String getDescricaoMatrizFilial() {
        return descricaoMatrizFilial;
    }

    public void setDescricaoMatrizFilial(String descricaoMatrizFilial) {
        this.descricaoMatrizFilial = descricaoMatrizFilial;
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

    public String getDescricaoSituacaoCadastral() {
        return descricaoSituacaoCadastral;
    }

    public void setDescricaoSituacaoCadastral(String descricaoSituacaoCadastral) {
        this.descricaoSituacaoCadastral = descricaoSituacaoCadastral;
    }

    public String getDataSituacaoCadastral() {
        return dataSituacaoCadastral;
    }

    public void setDataSituacaoCadastral(String dataSituacaoCadastral) {
        this.dataSituacaoCadastral = dataSituacaoCadastral;
    }

    public String getMotivoSituacaoCadastral() {
        return motivoSituacaoCadastral;
    }

    public void setMotivoSituacaoCadastral(String motivoSituacaoCadastral) {
        this.motivoSituacaoCadastral = motivoSituacaoCadastral;
    }

    public String getNomeCidadeExterior() {
        return nomeCidadeExterior;
    }

    public void setNomeCidadeExterior(String nomeCidadeExterior) {
        this.nomeCidadeExterior = nomeCidadeExterior;
    }

    public String getCodigoNaturezaJuridica() {
        return codigoNaturezaJuridica;
    }

    public void setCodigoNaturezaJuridica(String codigoNaturezaJuridica) {
        this.codigoNaturezaJuridica = codigoNaturezaJuridica;
    }

    public String getDataInicioAtividade() {
        return dataInicioAtividade;
    }

    public void setDataInicioAtividade(String dataInicioAtividade) {
        this.dataInicioAtividade = dataInicioAtividade;
    }

    public String getCnaeFiscal() {
        return cnaeFiscal;
    }

    public void setCnaeFiscal(String cnaeFiscal) {
        this.cnaeFiscal = cnaeFiscal;
    }

    public String getCnaeFiscalDescricao() {
        return cnaeFiscalDescricao;
    }

    public void setCnaeFiscalDescricao(String cnaeFiscalDescricao) {
        this.cnaeFiscalDescricao = cnaeFiscalDescricao;
    }

    public String getDescricaoTipoLogradouro() {
        return descricaoTipoLogradouro;
    }

    public void setDescricaoTipoLogradouro(String descricaoTipoLogradouro) {
        this.descricaoTipoLogradouro = descricaoTipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDddTelefone1() {
        return dddTelefone1;
    }

    public void setDddTelefone1(String dddTelefone1) {
        this.dddTelefone1 = dddTelefone1;
    }

    public String getDddTelefone2() {
        return dddTelefone2;
    }

    public void setDddTelefone2(String dddTelefone2) {
        this.dddTelefone2 = dddTelefone2;
    }

    public String getDddFax() {
        return dddFax;
    }

    public void setDddFax(String dddFax) {
        this.dddFax = dddFax;
    }

    public String getQualificacaoDoResponsavel() {
        return qualificacaoDoResponsavel;
    }

    public void setQualificacaoDoResponsavel(String qualificacaoDoResponsavel) {
        this.qualificacaoDoResponsavel = qualificacaoDoResponsavel;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getDescricaoPorte() {
        return descricaoPorte;
    }

    public void setDescricaoPorte(String descricaoPorte) {
        this.descricaoPorte = descricaoPorte;
    }

    public Boolean getOpcaoPeloSimples() {
        return opcaoPeloSimples;
    }

    public void setOpcaoPeloSimples(Boolean opcaoPeloSimples) {
        this.opcaoPeloSimples = opcaoPeloSimples;
    }

    public String getDataOpcaoPeloSimples() {
        return dataOpcaoPeloSimples;
    }

    public void setDataOpcaoPeloSimples(String dataOpcaoPeloSimples) {
        this.dataOpcaoPeloSimples = dataOpcaoPeloSimples;
    }

    public String getDataExclusaoDoSimples() {
        return dataExclusaoDoSimples;
    }

    public void setDataExclusaoDoSimples(String dataExclusaoDoSimples) {
        this.dataExclusaoDoSimples = dataExclusaoDoSimples;
    }

    public Boolean getOpcaoPeloMei() {
        return opcaoPeloMei;
    }

    public void setOpcaoPeloMei(Boolean opcaoPeloMei) {
        this.opcaoPeloMei = opcaoPeloMei;
    }

    public String getSituacaoEspecial() {
        return situacaoEspecial;
    }

    public void setSituacaoEspecial(String situacaoEspecial) {
        this.situacaoEspecial = situacaoEspecial;
    }

    public String getDataSituacaoEspecial() {
        return dataSituacaoEspecial;
    }

    public void setDataSituacaoEspecial(String dataSituacaoEspecial) {
        this.dataSituacaoEspecial = dataSituacaoEspecial;
    }

    public List<CnaeSecundario> getCnaesSecundarios() {
        return cnaesSecundarios;
    }

    public void setCnaesSecundarios(List<CnaeSecundario> cnaesSecundarios) {
        this.cnaesSecundarios = cnaesSecundarios;
    }

    public List<QuadroSocietario> getQsa() {
        return qsa;
    }

    public void setQsa(List<QuadroSocietario> qsa) {
        this.qsa = qsa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public List<AtividadePrincipal> getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(List<AtividadePrincipal> atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public List<AtividadeSecundaria> getAtividadesSecundarias() {
        return atividadesSecundarias;
    }

    public void setAtividadesSecundarias(List<AtividadeSecundaria> atividadesSecundarias) {
        this.atividadesSecundarias = atividadesSecundarias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEfr() {
        return efr;
    }

    public void setEfr(String efr) {
        this.efr = efr;
    }

    public Simples getSimples() {
        return simples;
    }

    public void setSimples(Simples simples) {
        this.simples = simples;
    }

    public Simei getSimei() {
        return simei;
    }

    public void setSimei(Simei simei) {
        this.simei = simei;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    @Override
    public String toString() {
        return "ReceitaWSResponse{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", uf='" + uf + '\'' +
                ", municipio='" + municipio + '\'' +
                '}';
    }

    /**
     * Classe interna para CNAEs secundários
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CnaeSecundario {
        @JsonProperty("codigo")
        private String codigo;

        @JsonProperty("descricao")
        private String descricao;

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    /**
     * Classe interna para Quadro Societário
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuadroSocietario {
        @JsonProperty("nome")
        private String nome;

        @JsonProperty("qual")
        private String qual;

        // Getters e Setters
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getQual() {
            return qual;
        }

        public void setQual(String qual) {
            this.qual = qual;
        }
    }

    /**
     * Classe interna para Atividade Principal
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AtividadePrincipal {
        @JsonProperty("code")
        private String code;

        @JsonProperty("text")
        private String text;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    /**
     * Classe interna para Atividades Secundárias
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AtividadeSecundaria {
        @JsonProperty("code")
        private String code;

        @JsonProperty("text")
        private String text;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    /**
     * Classe interna para Simples Nacional
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Simples {
        @JsonProperty("optante")
        private Boolean optante;

        @JsonProperty("data_opcao")
        private String dataOpcao;

        @JsonProperty("data_exclusao")
        private String dataExclusao;

        @JsonProperty("ultima_atualizacao")
        private String ultimaAtualizacao;

        public Boolean getOptante() {
            return optante;
        }

        public void setOptante(Boolean optante) {
            this.optante = optante;
        }

        public String getDataOpcao() {
            return dataOpcao;
        }

        public void setDataOpcao(String dataOpcao) {
            this.dataOpcao = dataOpcao;
        }

        public String getDataExclusao() {
            return dataExclusao;
        }

        public void setDataExclusao(String dataExclusao) {
            this.dataExclusao = dataExclusao;
        }

        public String getUltimaAtualizacao() {
            return ultimaAtualizacao;
        }

        public void setUltimaAtualizacao(String ultimaAtualizacao) {
            this.ultimaAtualizacao = ultimaAtualizacao;
        }
    }

    /**
     * Classe interna para SIMI
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Simei {
        @JsonProperty("optante")
        private Boolean optante;

        @JsonProperty("data_opcao")
        private String dataOpcao;

        @JsonProperty("data_exclusao")
        private String dataExclusao;

        @JsonProperty("ultima_atualizacao")
        private String ultimaAtualizacao;

        public Boolean getOptante() {
            return optante;
        }

        public void setOptante(Boolean optante) {
            this.optante = optante;
        }

        public String getDataOpcao() {
            return dataOpcao;
        }

        public void setDataOpcao(String dataOpcao) {
            this.dataOpcao = dataOpcao;
        }

        public String getDataExclusao() {
            return dataExclusao;
        }

        public void setDataExclusao(String dataExclusao) {
            this.dataExclusao = dataExclusao;
        }

        public String getUltimaAtualizacao() {
            return ultimaAtualizacao;
        }

        public void setUltimaAtualizacao(String ultimaAtualizacao) {
            this.ultimaAtualizacao = ultimaAtualizacao;
        }
    }

    /**
     * Classe interna para Billing
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Billing {
        @JsonProperty("free")
        private Boolean free;

        @JsonProperty("database")
        private Boolean database;

        public Boolean getFree() {
            return free;
        }

        public void setFree(Boolean free) {
            this.free = free;
        }

        public Boolean getDatabase() {
            return database;
        }

        public void setDatabase(Boolean database) {
            this.database = database;
        }
    }
}
