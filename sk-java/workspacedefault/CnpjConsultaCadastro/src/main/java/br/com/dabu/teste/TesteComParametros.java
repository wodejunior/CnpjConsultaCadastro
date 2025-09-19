package br.com.dabu.teste;

import br.com.dabu.model.CnpjConsultaDTO;
import br.com.dabu.model.CnpjConsultaResult;
import br.com.dabu.model.SefazConsultaCadastroResponseJDOM;
import br.com.dabu.service.CnpjConsultaService;
import br.com.dabu.util.XMLUtil;
/**
 * Classe de teste para consulta completa de CNPJ com parâmetros fixos.
 * Permite passar CNPJ, nome do arquivo do certificado e senha como argumentos.
 * Ideal para testes no Eclipse ou via linha de comando.
 */
public class TesteComParametros {

    public static void main(String[] args) {
        System.out.println("=== TESTE COM PARÂMETROS ===");

        /*
        if (args.length < 3) {
            System.out.println("Uso: java TesteComParametros <CNPJ> <nome_arquivo_certificado> <senha_certificado>");
            System.out.println("Exemplo: java TesteComParametros 36897740000110 certificado.p12 minhaSenha");
            return;
        }*/

        String cnpj = "36897740000110";
        String nomeCertificado = "WJC_SISTEMAS_LTDA_51926609000127.pfx";
        String senhaCertificado = "12345678";

        System.out.println("CNPJ a consultar: " + cnpj);
        System.out.println("Certificado: " + nomeCertificado);
        System.out.println("Senha do Certificado: [ocultada]");

        CnpjConsultaService service = new CnpjConsultaService();

        // 1. Configurar o certificado
        System.out.println("\nConfigurando certificado...");
        boolean certificadoConfigurado = service.configurarCertificado(nomeCertificado, senhaCertificado);

        if (certificadoConfigurado) {
            System.out.println("✓ Certificado configurado com sucesso!");
            System.out.println(service.getInformacoesCertificado());
        } else {
            System.out.println("✗ Falha ao configurar o certificado. Verifique o nome do arquivo e a senha.");
            System.out.println("A consulta SEFAZ será pulada.");
        }

        // 2. Realizar a consulta completa
        System.out.println("\nRealizando consulta completa para CNPJ: " + cnpj + "...");
        long inicio = System.currentTimeMillis();
        CnpjConsultaResult resultado = service.consultarCNPJ(cnpj);
        long tempo = System.currentTimeMillis() - inicio;

        System.out.println("\n=== RESULTADO DA CONSULTA ===");
        System.out.println("Tempo de consulta: " + tempo + "ms");

        if (resultado.isSucesso()) {
            // Converte resultado para DTO para facilitar reutilização
            CnpjConsultaDTO dto = new CnpjConsultaDTO(resultado);
            
            // Debug: Exibir dados brutos da SEFAZ
            System.out.println("\n=== DEBUG SEFAZ ===");
            if (resultado.getDadosSefazJDOM() != null) {
                System.out.println("✓ Dados SEFAZ brutos disponíveis");
                exibirDadosBrutosSefaz(resultado.getDadosSefazJDOM());
            } else {
                System.out.println("✗ Dados SEFAZ brutos: NÃO DISPONÍVEIS");
            }
            
            exibirResultadoSucesso(dto);
        } else {
            System.out.println("✗ Erro na consulta: " + resultado.getMensagemErro());
        }

        System.out.println("\n=== TESTE CONCLUÍDO ===");
    }

    /**
     * Exibe dados brutos da SEFAZ para debug
     */
    private static void exibirDadosBrutosSefaz(SefazConsultaCadastroResponseJDOM sefaz) {
        if (sefaz.getInfCons() != null) {
            SefazConsultaCadastroResponseJDOM.InfCons infCons = sefaz.getInfCons();
            System.out.println("  • UF: " + infCons.getUf());
            System.out.println("  • VerAplic: " + infCons.getVerAplic());
            System.out.println("  • CStat: " + infCons.getcStat());
            System.out.println("  • XMotivo: " + infCons.getxMotivo());
            System.out.println("  • CNPJ: " + infCons.getCnpj());
            System.out.println("  • CPF: " + infCons.getCpf());
            System.out.println("  • DhCons: " + XMLUtil.formatarDataBrasileira(infCons.getDhCons()));
            System.out.println("  • CUF: " + infCons.getcUF());
            
            if (infCons.getInfCad() != null && !infCons.getInfCad().isEmpty()) {
                System.out.println("  • Total InfCad: " + infCons.getInfCad().size());
                for (int i = 0; i < infCons.getInfCad().size(); i++) {
                    SefazConsultaCadastroResponseJDOM.InfCad infCad = infCons.getInfCad().get(i);
                    System.out.println("  • InfCad[" + i + "]:");
                    System.out.println("    - IE: " + infCad.getIe());
                    System.out.println("    - CNPJ: " + infCad.getCnpj());
                    System.out.println("    - CPF: " + infCad.getCpf());
                    System.out.println("    - UF: " + infCad.getUf());
                    System.out.println("    - CSit: " + infCad.getcSit());
                    System.out.println("    - IndCredNFe: " + infCad.getIndCredNFe());
                    System.out.println("    - IndCredCTe: " + infCad.getIndCredCTe());
                    System.out.println("    - XNome: " + infCad.getxNome());
                    System.out.println("    - XFant: " + infCad.getxFant());
                    System.out.println("    - XRegApur: " + infCad.getxRegApur());
                    System.out.println("    - CNAE: " + infCad.getCnae());
                    System.out.println("    - DIniAtiv: " + infCad.getdIniAtiv());
                    System.out.println("    - DUltSit: " + infCad.getdUltSit());
                    System.out.println("    - DBaixa: " + infCad.getdBaixa());
                    System.out.println("    - IEUnica: " + infCad.getIeUnica());
                    System.out.println("    - IEAtual: " + infCad.getIeAtual());
                    
                    if (infCad.getEnder() != null) {
                        System.out.println("    - Endereço disponível: Sim");
                        System.out.println("      * XLgr: " + infCad.getEnder().getxLgr());
                        System.out.println("      * Nro: " + infCad.getEnder().getNro());
                        System.out.println("      * XCpl: " + infCad.getEnder().getxCpl());
                        System.out.println("      * XBairro: " + infCad.getEnder().getxBairro());
                        System.out.println("      * CEP: " + infCad.getEnder().getCep());
                        System.out.println("      * XMun: " + infCad.getEnder().getxMun());
                        System.out.println("      * CMun: " + infCad.getEnder().getcMun());
                    } else {
                        System.out.println("    - Endereço disponível: Não");
                    }
                }
            } else {
                System.out.println("  • InfCad: Lista vazia ou nula");
            }
        } else {
            System.out.println("  • InfCons: Nulo");
        }
    }
    
    /**
     * Exibe resultado de sucesso formatado com dados detalhados de ambas as APIs usando DTO
     */
    private static void exibirResultadoSucesso(CnpjConsultaDTO dto) {
        System.out.println("✓ Consulta realizada com sucesso!");
        System.out.println();

        // Dados consolidados principais
        exibirDadosConsolidados(dto);
        
        // Dados detalhados da ReceitaWS
        exibirDadosReceitaWS(dto);
        
        // Dados detalhados da SEFAZ
        exibirDadosSefaz(dto);
        
        // Resumo das APIs consultadas
        exibirResumoAPIs(dto);
        
        // Exibe resumo do DTO
        System.out.println();
        System.out.println(dto.getResumoConsultas());
    }
    
    /**
     * Exibe dados consolidados principais
     */
    private static void exibirDadosConsolidados(CnpjConsultaDTO dto) {
        System.out.println("=== DADOS CONSOLIDADOS ===");
        System.out.println("CNPJ: " + dto.getCnpj());
        System.out.println("Razão Social: " + (dto.getRazaoSocial() != null ? dto.getRazaoSocial() : "N/A"));
        System.out.println("Nome Fantasia: " + (dto.getNomeFantasia() != null ? dto.getNomeFantasia() : "N/A"));
        System.out.println("Situação Cadastral: " + (dto.getSituacaoCadastral() != null ? dto.getSituacaoCadastral() : "N/A"));
        System.out.println("UF: " + (dto.getUf() != null ? dto.getUf() : "N/A"));
        System.out.println("Município: " + (dto.getMunicipio() != null ? dto.getMunicipio() : "N/A"));
        System.out.println();
    }
    
    /**
     * Exibe dados detalhados da ReceitaWS
     */
    private static void exibirDadosReceitaWS(CnpjConsultaDTO dto) {
        System.out.println("=== DADOS RECEITAWS ===");
        
        if (dto.isReceitaWSSucesso()) {
            System.out.println("✓ API ReceitaWS: CONSULTADA COM SUCESSO");
            System.out.println();
            
            CnpjConsultaDTO.ReceitaWSData receitaWS = dto.getReceitaWSData();
            
            // Dados básicos
            System.out.println("📋 DADOS BÁSICOS:");
            System.out.println("  • CNPJ: " + (receitaWS.getCnpj() != null ? receitaWS.getCnpj() : "N/A"));
            System.out.println("  • Razão Social: " + (receitaWS.getRazaoSocial() != null ? receitaWS.getRazaoSocial() : "N/A"));
            System.out.println("  • Nome Fantasia: " + (receitaWS.getNomeFantasia() != null ? receitaWS.getNomeFantasia() : "N/A"));
            System.out.println("  • Tipo: " + (receitaWS.getTipo() != null ? receitaWS.getTipo() : "N/A"));
            System.out.println("  • Situação: " + (receitaWS.getSituacao() != null ? receitaWS.getSituacao() : "N/A"));
            System.out.println("  • Data Situação: " + (receitaWS.getDataSituacao() != null ? receitaWS.getDataSituacao() : "N/A"));
            System.out.println("  • Motivo Situação: " + (receitaWS.getMotivoSituacao() != null ? receitaWS.getMotivoSituacao() : "N/A"));
            System.out.println("  • Status API: " + (receitaWS.getStatus() != null ? receitaWS.getStatus() : "N/A"));
            System.out.println("  • Última Atualização: " + XMLUtil.formatarDataBrasileira(receitaWS.getUltimaAtualizacao()));
            
            // Dados de localização
            System.out.println();
            System.out.println("📍 LOCALIZAÇÃO:");
            System.out.println("  • UF: " + (receitaWS.getUf() != null ? receitaWS.getUf() : "N/A"));
            System.out.println("  • Município: " + (receitaWS.getMunicipio() != null ? receitaWS.getMunicipio() : "N/A"));
            System.out.println("  • CEP: " + (receitaWS.getCep() != null ? receitaWS.getCep() : "N/A"));
            System.out.println("  • Logradouro: " + (receitaWS.getLogradouro() != null ? receitaWS.getLogradouro() : "N/A"));
            System.out.println("  • Número: " + (receitaWS.getNumero() != null ? receitaWS.getNumero() : "N/A"));
            System.out.println("  • Complemento: " + (receitaWS.getComplemento() != null ? receitaWS.getComplemento() : "N/A"));
            System.out.println("  • Bairro: " + (receitaWS.getBairro() != null ? receitaWS.getBairro() : "N/A"));
            
            // Dados empresariais
            System.out.println();
            System.out.println("🏢 DADOS EMPRESARIAIS:");
            System.out.println("  • Data Início Atividade: " + (receitaWS.getDataInicioAtividade() != null ? receitaWS.getDataInicioAtividade() : "N/A"));
            System.out.println("  • Porte: " + (receitaWS.getPorte() != null ? receitaWS.getPorte() : "N/A"));
            System.out.println("  • Capital Social: " + (receitaWS.getCapitalSocial() != null ? receitaWS.getCapitalSocial() : "N/A"));
            System.out.println("  • Natureza Jurídica: " + (receitaWS.getNaturezaJuridicaCompleta() != null ? receitaWS.getNaturezaJuridicaCompleta() : "N/A"));
            System.out.println("  • Código Natureza Jurídica: " + (receitaWS.getCodigoNaturezaJuridica() != null ? receitaWS.getCodigoNaturezaJuridica() : "N/A"));
            
            // CNAE Principal
            System.out.println();
            System.out.println("📊 CNAE PRINCIPAL:");
            if (receitaWS.getAtividadePrincipal() != null && !receitaWS.getAtividadePrincipal().isEmpty()) {
                for (int i = 0; i < receitaWS.getAtividadePrincipal().size(); i++) {
                    CnpjConsultaDTO.AtividadePrincipal atividade = receitaWS.getAtividadePrincipal().get(i);
                    System.out.println("  • Código: " + (atividade.getCode() != null ? atividade.getCode() : "N/A"));
                    System.out.println("  • Descrição: " + (atividade.getText() != null ? atividade.getText() : "N/A"));
                }
            } else {
                System.out.println("  • CNAE Principal: " + (receitaWS.getCnaeFiscal() != null ? receitaWS.getCnaeFiscal() : "N/A"));
                System.out.println("  • Descrição CNAE: " + (receitaWS.getCnaeFiscalDescricao() != null ? receitaWS.getCnaeFiscalDescricao() : "N/A"));
            }
            
            // CNAEs Secundários
            System.out.println();
            System.out.println("📊 CNAES SECUNDÁRIOS:");
            if (receitaWS.getAtividadesSecundarias() != null && !receitaWS.getAtividadesSecundarias().isEmpty()) {
                for (int i = 0; i < receitaWS.getAtividadesSecundarias().size(); i++) {
                    CnpjConsultaDTO.AtividadeSecundaria atividade = receitaWS.getAtividadesSecundarias().get(i);
                    System.out.println("  • Código: " + (atividade.getCode() != null ? atividade.getCode() : "N/A"));
                    System.out.println("  • Descrição: " + (atividade.getText() != null ? atividade.getText() : "N/A"));
                    if (i < receitaWS.getAtividadesSecundarias().size() - 1) {
                        System.out.println("  • ---");
                    }
                }
            } else {
                System.out.println("  • Nenhuma atividade secundária encontrada");
            }
            
            // Regimes tributários
            System.out.println();
            System.out.println("💰 REGIMES TRIBUTÁRIOS:");
            if (receitaWS.getSimples() != null) {
                System.out.println("  • Simples Nacional: " + (receitaWS.getSimples().getOptante() ? "✓ Sim" : "✗ Não"));
                System.out.println("  • Data Opção Simples: " + (receitaWS.getSimples().getDataOpcao() != null ? receitaWS.getSimples().getDataOpcao() : "N/A"));
                System.out.println("  • Data Exclusão Simples: " + (receitaWS.getSimples().getDataExclusao() != null ? receitaWS.getSimples().getDataExclusao() : "N/A"));
            } else {
                System.out.println("  • Simples Nacional: " + (receitaWS.getOpcaoPeloSimples() != null ? (receitaWS.getOpcaoPeloSimples() ? "✓ Sim" : "✗ Não") : "N/A"));
            }
            
            if (receitaWS.getSimei() != null) {
                System.out.println("  • SIMI: " + (receitaWS.getSimei().getOptante() ? "✓ Sim" : "✗ Não"));
                System.out.println("  • Data Opção SIMI: " + (receitaWS.getSimei().getDataOpcao() != null ? receitaWS.getSimei().getDataOpcao() : "N/A"));
                System.out.println("  • Data Exclusão SIMI: " + (receitaWS.getSimei().getDataExclusao() != null ? receitaWS.getSimei().getDataExclusao() : "N/A"));
            } else {
                System.out.println("  • MEI: " + (receitaWS.getOpcaoPeloMei() != null ? (receitaWS.getOpcaoPeloMei() ? "✓ Sim" : "✗ Não") : "N/A"));
            }
            
            // Contato
            System.out.println();
            System.out.println("📞 CONTATO:");
            System.out.println("  • Email: " + (receitaWS.getEmail() != null ? receitaWS.getEmail() : "N/A"));
            System.out.println("  • Telefone: " + (receitaWS.getTelefone() != null ? receitaWS.getTelefone() : "N/A"));
            System.out.println("  • DDD Telefone 1: " + (receitaWS.getDddTelefone1() != null ? receitaWS.getDddTelefone1() : "N/A"));
            System.out.println("  • DDD Telefone 2: " + (receitaWS.getDddTelefone2() != null ? receitaWS.getDddTelefone2() : "N/A"));
            System.out.println("  • DDD Fax: " + (receitaWS.getDddFax() != null ? receitaWS.getDddFax() : "N/A"));
            
            // Quadro Societário
            System.out.println();
            System.out.println("👥 QUADRO SOCIETÁRIO (QSA):");
            if (receitaWS.getQsa() != null && !receitaWS.getQsa().isEmpty()) {
                for (int i = 0; i < receitaWS.getQsa().size(); i++) {
                    CnpjConsultaDTO.QuadroSocietario socio = receitaWS.getQsa().get(i);
                    System.out.println("  • Sócio " + (i + 1) + ":");
                    System.out.println("    - Nome: " + (socio.getNome() != null ? socio.getNome() : "N/A"));
                    System.out.println("    - Qualificação: " + (socio.getQual() != null ? socio.getQual() : "N/A"));
                    if (i < receitaWS.getQsa().size() - 1) {
                        System.out.println("    - ---");
                    }
                }
            } else {
                System.out.println("  • Nenhum sócio encontrado");
            }
            
            // Dados adicionais
            System.out.println();
            System.out.println("📋 DADOS ADICIONAIS:");
            System.out.println("  • EFR: " + (receitaWS.getEfr() != null ? receitaWS.getEfr() : "N/A"));
            System.out.println("  • Situação Especial: " + (receitaWS.getSituacaoEspecial() != null ? receitaWS.getSituacaoEspecial() : "N/A"));
            System.out.println("  • Data Situação Especial: " + (receitaWS.getDataSituacaoEspecial() != null ? receitaWS.getDataSituacaoEspecial() : "N/A"));
            
            // Informações da API
            System.out.println();
            System.out.println("🔧 INFORMAÇÕES DA API:");
            if (receitaWS.getBilling() != null) {
                System.out.println("  • API Gratuita: " + (receitaWS.getBilling().getFree() ? "✓ Sim" : "✗ Não"));
                System.out.println("  • Base de Dados: " + (receitaWS.getBilling().getDatabase() ? "✓ Sim" : "✗ Não"));
            }
            
        } else {
            System.out.println("✗ API ReceitaWS: ERRO NA CONSULTA");
        }
        System.out.println();
    }
    
    /**
     * Exibe dados detalhados da SEFAZ
     */
    private static void exibirDadosSefaz(CnpjConsultaDTO dto) {
        System.out.println("=== DADOS SEFAZ ===");
        
        if (dto.isSefazSucesso()) {
            System.out.println("✓ API SEFAZ: CONSULTADA COM SUCESSO");
            System.out.println();
            
            CnpjConsultaDTO.SefazData sefaz = dto.getSefazData();
            
            // Dados básicos SEFAZ
            System.out.println("📋 DADOS BÁSICOS SEFAZ:");
            System.out.println("  • UF Consultada: " + (sefaz.getUfConsultada() != null ? sefaz.getUfConsultada() : "N/A"));
            System.out.println("  • Ambiente: " + (sefaz.getAmbiente() != null ? sefaz.getAmbiente() : "N/A"));
            System.out.println("  • Versão: " + (sefaz.getVersao() != null ? sefaz.getVersao() : "N/A"));
            
            if (sefaz.getCnpj() != null) {
                System.out.println();
                System.out.println("🏢 DADOS CADASTRAIS:");
                System.out.println("  • CNPJ: " + (sefaz.getCnpj() != null ? sefaz.getCnpj() : "N/A"));
                System.out.println("  • Inscrição Estadual: " + (sefaz.getInscricaoEstadual() != null ? sefaz.getInscricaoEstadual() : "N/A"));
                System.out.println("  • Situação IE: " + (sefaz.getSituacaoIE() != null ? sefaz.getSituacaoIE() : "N/A"));
                System.out.println("  • Data Situação IE: " + XMLUtil.formatarDataBrasileira( sefaz.getDataSituacaoIE() ));
                
                System.out.println();
                System.out.println("📄 CREDENCIAMENTOS:");
                System.out.println("  • Credenciado NF-e: " + (sefaz.isCredenciadoNFe() ? "✓ Sim" : "✗ Não"));
                System.out.println("  • Credenciado CT-e: " + (sefaz.isCredenciadoCTe() ? "✓ Sim" : "✗ Não"));
                System.out.println("  • Credenciado MDFe: " + (sefaz.isCredenciadoMDFe() ? "✓ Sim" : "✗ Não"));
                
                System.out.println();
                System.out.println("📋 DADOS ADICIONAIS SEFAZ:");
                System.out.println("  • Data Última Situação: " + XMLUtil.formatarDataBrasileira( sefaz.getDataSituacaoIE() ));
                
                // Endereço SEFAZ
                if (sefaz.getLogradouro() != null) {
                    System.out.println();
                    System.out.println("📍 ENDEREÇO SEFAZ:");
                    System.out.println("  • Logradouro: " + (sefaz.getLogradouro() != null ? sefaz.getLogradouro() : "N/A"));
                    System.out.println("  • Número: " + (sefaz.getNumero() != null ? sefaz.getNumero() : "N/A"));
                    System.out.println("  • Complemento: " + (sefaz.getComplemento() != null ? sefaz.getComplemento() : "N/A"));
                    System.out.println("  • Bairro: " + (sefaz.getBairro() != null ? sefaz.getBairro() : "N/A"));
                    System.out.println("  • CEP: " + (sefaz.getCep() != null ? sefaz.getCep() : "N/A"));
                    System.out.println("  • Município: " + (sefaz.getMunicipio() != null ? sefaz.getMunicipio() : "N/A"));
                    System.out.println("  • Código Município: " + (sefaz.getCodigoMunicipio() != null ? sefaz.getCodigoMunicipio() : "N/A"));
                    System.out.println("  • UF Consultada: " + (sefaz.getUfConsultada() != null ? sefaz.getUfConsultada() : "N/A"));
                }
            } else {
                System.out.println("  • Nenhum cadastro encontrado na SEFAZ para este CNPJ");
            }
            
        } else {
            System.out.println("✗ API SEFAZ: ERRO NA CONSULTA OU CERTIFICADO NÃO CONFIGURADO");
        }
        System.out.println();
    }
    
    /**
     * Exibe resumo das APIs consultadas
     */
    private static void exibirResumoAPIs(CnpjConsultaDTO dto) {
        System.out.println("=== RESUMO DAS CONSULTAS ===");
        
        // Status ReceitaWS
        if (dto.isReceitaWSSucesso()) {
            System.out.println("✓ ReceitaWS: Consulta realizada com sucesso");
        } else {
            System.out.println("✗ ReceitaWS: Erro na consulta");
        }
        
        // Status SEFAZ
        if (dto.isSefazSucesso()) {
            System.out.println("✓ SEFAZ: Consulta realizada com sucesso");
        } else {
            System.out.println("✗ SEFAZ: Erro na consulta ou certificado não configurado");
        }
        
        // Informações técnicas
        System.out.println();
        System.out.println("🔧 INFORMAÇÕES TÉCNICAS:");
        System.out.println("  • XML processado com JDOM: " + (dto.isSefazSucesso() ? "✓ Sim" : "✗ Não"));
        System.out.println("  • Certificado carregado: " + (dto.isSefazSucesso() ? "✓ Sim" : "✗ Não"));
        System.out.println("  • Timestamp da consulta: " + (dto.getConsultaTimestamp() != null ? dto.getConsultaTimestamp() : "N/A"));
        System.out.println("  • Consulta completa: " + (dto.isConsultaCompleta() ? "✓ Sim" : "✗ Não"));
        System.out.println("  • Consulta parcial: " + (dto.isConsultaParcial() ? "✓ Sim" : "✗ Não"));
    }
}
