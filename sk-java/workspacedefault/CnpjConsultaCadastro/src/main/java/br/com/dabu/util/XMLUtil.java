package br.com.dabu.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitário para manipulação de XML usando JDOM
 */
public class XMLUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);
    
    // Namespace da SEFAZ
    public static final Namespace NFE_NAMESPACE = Namespace.getNamespace("http://www.portalfiscal.inf.br/nfe");
    
    /**
     * Cria XML de consulta cadastro para SEFAZ
     * 
     * @param uf Unidade Federativa
     * @param cnpj CNPJ a ser consultado
     * @return XML da requisição ou null em caso de erro
     */
    public static String criarXMLConsultaCadastro(String uf, String cnpj) {
        try {
            // Elemento raiz
            Element consCad = new Element("ConsCad", NFE_NAMESPACE);
            consCad.setAttribute("versao", "2.00"); // Versão correta para SEFAZ
            
            // Informações da consulta
            Element infCons = new Element("infCons", NFE_NAMESPACE);
            
            // Serviço
            Element xServ = new Element("xServ", NFE_NAMESPACE);
            xServ.setText("CONS-CAD");
            infCons.addContent(xServ);
            
            // UF
            Element ufElement = new Element("UF", NFE_NAMESPACE);
            ufElement.setText(uf);
            infCons.addContent(ufElement);
            
            // CNPJ ou IE
            if (cnpj != null && !cnpj.trim().isEmpty()) {
                Element cnpjElement = new Element("CNPJ", NFE_NAMESPACE);
                cnpjElement.setText(cnpj);
                infCons.addContent(cnpjElement);
            }
            
            consCad.addContent(infCons);
            
            // Cria documento
            Document document = new Document(consCad);
            
            // Converte para string
            return documentToString(document);
            
        } catch (Exception e) {
            logger.error("Erro ao criar XML de consulta cadastro: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Cria XML para consulta de cadastro na SEFAZ por Inscrição Estadual
     * 
     * @param uf Unidade Federativa
     * @param ie Inscrição Estadual
     * @return XML da requisição ou null em caso de erro
     */
    public static String criarXMLConsultaCadastroPorIE(String uf, String ie) {
        try {
            // Elemento raiz
            Element consCad = new Element("ConsCad", NFE_NAMESPACE);
            consCad.setAttribute("versao", "2.00"); // Versão correta para SEFAZ
            
            // Informações da consulta
            Element infCons = new Element("infCons", NFE_NAMESPACE);
            
            // Serviço
            Element xServ = new Element("xServ", NFE_NAMESPACE);
            xServ.setText("CONS-CAD");
            infCons.addContent(xServ);
            
            // UF
            Element ufElement = new Element("UF", NFE_NAMESPACE);
            ufElement.setText(uf);
            infCons.addContent(ufElement);
            
            // IE
            Element ieElement = new Element("IE", NFE_NAMESPACE);
            ieElement.setText(ie);
            infCons.addContent(ieElement);
            
            consCad.addContent(infCons);
            
            // Cria documento
            Document document = new Document(consCad);
            
            // Converte para String
            return documentToString(document);
            
        } catch (Exception e) {
            logger.error("Erro ao criar XML de consulta cadastro por IE: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Converte Document JDOM para String
     * 
     * @param document Document JDOM
     * @return XML como String
     */
    public static String documentToString(Document document) {
        try {
            // Usa formato compacto para evitar caracteres de edição
            Format format = Format.getCompactFormat();
            format.setEncoding("UTF-8");
            format.setOmitDeclaration(false); // Mantém declaração XML
            format.setIndent(""); // Remove indentação
            format.setLineSeparator(""); // Remove quebras de linha
            XMLOutputter outputter = new XMLOutputter(format);
            StringWriter writer = new StringWriter();
            outputter.output(document, writer);
            String result = writer.toString();
            // Remove espaços extras e caracteres de controle
            return result.replaceAll("\\s+", " ").trim();
        } catch (IOException e) {
            logger.error("Erro ao converter Document para String: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Converte String XML para Document JDOM
     * 
     * @param xmlString XML como String
     * @return Document JDOM ou null em caso de erro
     */
    public static Document stringToDocument(String xmlString) {
        try {
            SAXBuilder builder = new SAXBuilder();
            return builder.build(new StringReader(xmlString));
        } catch (JDOMException | IOException e) {
            logger.error("Erro ao converter String para Document: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Extrai valor de um elemento XML
     * 
     * @param document Document JDOM
     * @param elementName Nome do elemento
     * @param namespace Namespace (pode ser null)
     * @return Valor do elemento ou null se não encontrado
     */
    public static String extrairValorElemento(Document document, String elementName, Namespace namespace) {
        try {
            Element root = document.getRootElement();
            Element elemento = encontrarElemento(root, elementName, namespace);
            return elemento != null ? elemento.getTextTrim() : null;
        } catch (Exception e) {
            logger.error("Erro ao extrair valor do elemento {}: {}", elementName, e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Encontra elemento recursivamente na árvore XML
     * 
     * @param parent Elemento pai
     * @param elementName Nome do elemento procurado
     * @param namespace Namespace (pode ser null)
     * @return Elemento encontrado ou null
     */
    private static Element encontrarElemento(Element parent, String elementName, Namespace namespace) {
        // Verifica o elemento atual
        if (parent.getName().equals(elementName)) {
            if (namespace == null || parent.getNamespace().equals(namespace)) {
                return parent;
            }
        }
        
        // Procura nos filhos
        for (Element child : parent.getChildren()) {
            Element found = encontrarElemento(child, elementName, namespace);
            if (found != null) {
                return found;
            }
        }
        
        return null;
    }
    
    /**
     * Cria envelope SOAP para requisição SEFAZ
     * 
     * @param xmlRequest XML da requisição
     * @param uf UF para determinar o endpoint
     * @return Envelope SOAP completo
     */
    public static String criarEnvelopeSOAP(String xmlRequest, String uf) {
        try {
            // Namespace SOAP
            Namespace soapNamespace = Namespace.getNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/");
            Namespace cadNamespace = Namespace.getNamespace("cad", "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro4");
            
            // Envelope SOAP
            Element envelope = new Element("Envelope", soapNamespace);
            envelope.addNamespaceDeclaration(cadNamespace);
            
            // Header
            Element header = new Element("Header", soapNamespace);
            envelope.addContent(header);
            
            // Body
            Element body = new Element("Body", soapNamespace);
            
            // consultaCadastro
            Element consultaCadastro = new Element("consultaCadastro", cadNamespace);
            
            // nfeCabecMsg
            Element nfeCabecMsg = new Element("nfeCabecMsg", cadNamespace);
            Element versaoDados = new Element("versaoDados", cadNamespace);
            versaoDados.setText("2.00");
            nfeCabecMsg.addContent(versaoDados);
            consultaCadastro.addContent(nfeCabecMsg);
            
            // nfeDadosMsg - adiciona o XML como elemento filho
            Element nfeDadosMsg = new Element("nfeDadosMsg", cadNamespace);
            try {
                // Parse o XML da requisição e adiciona como elemento
                org.jdom2.input.SAXBuilder builder = new org.jdom2.input.SAXBuilder();
                builder.setFeature("http://xml.org/sax/features/validation", false);
                builder.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
                builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
                org.jdom2.Document xmlDoc = builder.build(new java.io.StringReader(xmlRequest));
                Element consCadElement = xmlDoc.getRootElement();
                nfeDadosMsg.addContent(consCadElement.clone());
            } catch (Exception e) {
                logger.warn("Erro ao parsear XML da requisição, usando como texto: {}", e.getMessage());
                nfeDadosMsg.setText(xmlRequest);
            }
            consultaCadastro.addContent(nfeDadosMsg);
            
            body.addContent(consultaCadastro);
            envelope.addContent(body);
            
            // Cria documento
            Document document = new Document(envelope);
            
            return documentToString(document);
            
        } catch (Exception e) {
            logger.error("Erro ao criar envelope SOAP: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Extrai corpo da resposta SOAP removendo envelope
     * 
     * @param soapResponse Resposta SOAP completa
     * @return XML do corpo da resposta ou null em caso de erro
     */
    public static String extrairCorpoResposta(String soapResponse) {
        try {
            Document document = stringToDocument(soapResponse);
            if (document == null) {
                return null;
            }
            
            // Procura pelo elemento retConsCad
            Element retConsCad = encontrarElemento(document.getRootElement(), "retConsCad", null);
            
            if (retConsCad != null) {
                Document responseDoc = new Document((Element) retConsCad.clone());
                return documentToString(responseDoc);
            }
            
            // Se não encontrar, retorna o XML original
            logger.warn("Elemento retConsCad não encontrado na resposta SOAP");
            return soapResponse;
            
        } catch (Exception e) {
            logger.error("Erro ao extrair corpo da resposta SOAP: {}", e.getMessage(), e);
            return soapResponse; // Retorna original em caso de erro
        }
    }
    
    /**
     * Valida se XML está bem formado
     * 
     * @param xmlString XML como String
     * @return true se válido, false caso contrário
     */
    public static boolean validarXML(String xmlString) {
        try {
            Document document = stringToDocument(xmlString);
            return document != null;
        } catch (Exception e) {
            logger.debug("XML inválido: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Formata XML para exibição (pretty print)
     * 
     * @param xmlString XML como String
     * @return XML formatado ou original em caso de erro
     */
    public static String formatarXML(String xmlString) {
        try {
            Document document = stringToDocument(xmlString);
            if (document != null) {
                XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
                return outputter.outputString(document);
            }
        } catch (Exception e) {
            logger.debug("Erro ao formatar XML: {}", e.getMessage());
        }
        return xmlString; // Retorna original em caso de erro
    }
    
    /**
     * Converte data ISO para formato brasileiro dd/MM/yyyy HH:mm:ss
     * 
     * @param dataISO Data no formato ISO (ex: 2025-09-19T15:22:47.595-03:00)
     * @return Data formatada em português brasileiro ou original em caso de erro
     */
    public static String formatarDataBrasileira(String dataISO) {
        if (dataISO == null || dataISO.trim().isEmpty()) {
            return "N/A";
        }

        try {
            // Corrige casos onde vem só a data e o offset (sem hora)
            if (dataISO.matches("\\d{4}-\\d{2}-\\d{2}-\\d{2}:\\d{2}")) {
                // Exemplo: "2025-07-02-03:00" → "2025-07-02T00:00:00-03:00"
                dataISO = dataISO.replaceFirst("^(\\d{4}-\\d{2}-\\d{2})(-\\d{2}:\\d{2})$", "$1T00:00:00$2");
            }

            // Usa OffsetDateTime para considerar fuso-horário
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dataISO);

            // Converte para horário local (sem offset)
            LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

            // Formata
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return localDateTime.format(formatter);

        } catch (Exception e) {
            logger.debug("Erro ao formatar data '{}': {}", dataISO, e.getMessage());
            return dataISO;
        }
    }
}




