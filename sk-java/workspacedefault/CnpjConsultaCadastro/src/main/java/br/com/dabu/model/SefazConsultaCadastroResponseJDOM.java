package br.com.dabu.model;

import br.com.dabu.util.XMLUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo para resposta de consulta cadastro da SEFAZ usando JDOM
 * Substitui a versão com Simple XML
 */
public class SefazConsultaCadastroResponseJDOM {
    
    private static final Logger logger = LoggerFactory.getLogger(SefazConsultaCadastroResponseJDOM.class);
    
    private InfCons infCons;
    
    /**
     * Cria instância a partir de XML usando JDOM
     * 
     * @param xmlResponse XML da resposta SEFAZ
     * @return Instância preenchida ou null em caso de erro
     */
    public static SefazConsultaCadastroResponseJDOM fromXML(String xmlResponse) {
        try {
            Document document = XMLUtil.stringToDocument(xmlResponse);
            if (document == null) {
                logger.error("Erro ao parsear XML da resposta SEFAZ");
                return null;
            }
            
            SefazConsultaCadastroResponseJDOM response = new SefazConsultaCadastroResponseJDOM();
            response.parseDocument(document);
            
            return response;
            
        } catch (Exception e) {
            logger.error("Erro ao criar resposta SEFAZ a partir do XML: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Faz parse do documento JDOM
     */
    private void parseDocument(Document document) {
        Element root = document.getRootElement();
        
        logger.info("=== DEBUG PARSING JDOM ===");
        logger.info("Root element: {}", root.getName());
        logger.info("Root namespace: {}", root.getNamespaceURI());
        logger.info("Root children count: {}", root.getChildren().size());
        
        // Log de todos os elementos filhos
        for (Element child : root.getChildren()) {
            logger.info("Child element: {} (namespace: {})", child.getName(), child.getNamespaceURI());
        }
        
        // Procura elemento infCons
        Element infConsElement = encontrarElemento(root, "infCons");
        if (infConsElement != null) {
            logger.info("✓ InfCons element found");
            this.infCons = parseInfCons(infConsElement);
        } else {
            logger.warn("⚠ InfCons element not found");
        }
    }
    
    /**
     * Faz parse do elemento infCons
     */
    private InfCons parseInfCons(Element infConsElement) {
        InfCons infCons = new InfCons();
        
        logger.info("=== DEBUG PARSE INFCONS ===");
        logger.info("InfCons element name: {}", infConsElement.getName());
        logger.info("InfCons namespace: {}", infConsElement.getNamespaceURI());
        logger.info("InfCons children count: {}", infConsElement.getChildren().size());
        
        // Log de todos os elementos filhos do infCons
        for (Element child : infConsElement.getChildren()) {
            logger.info("InfCons child: {} = '{}'", child.getName(), child.getTextTrim());
        }
        
        String verAplic = getElementText(infConsElement, "verAplic");
        String cStat = getElementText(infConsElement, "cStat");
        String xMotivo = getElementText(infConsElement, "xMotivo");
        String uf = getElementText(infConsElement, "UF");
        String cnpj = getElementText(infConsElement, "CNPJ");
        String cpf = getElementText(infConsElement, "CPF");
        String dhCons = getElementText(infConsElement, "dhCons");
        String cUF = getElementText(infConsElement, "cUF");
        
        logger.info("Parsed values:");
        logger.info("  verAplic: '{}'", verAplic);
        logger.info("  cStat: '{}'", cStat);
        logger.info("  xMotivo: '{}'", xMotivo);
        logger.info("  UF: '{}'", uf);
        logger.info("  CNPJ: '{}'", cnpj);
        logger.info("  CPF: '{}'", cpf);
        logger.info("  dhCons: '{}'", dhCons);
        logger.info("  cUF: '{}'", cUF);
        
        infCons.setVerAplic(verAplic);
        infCons.setcStat(cStat);
        infCons.setxMotivo(xMotivo);
        infCons.setUf(uf);
        infCons.setCnpj(cnpj);
        infCons.setCpf(cpf);
        infCons.setDhCons(dhCons);
        infCons.setcUF(cUF);
        
        // Parse dos elementos infCad
        List<InfCad> infCadList = new ArrayList<>();
        // Busca elementos infCad considerando namespace
        List<Element> infCadElements = new ArrayList<>();
        for (Element child : infConsElement.getChildren()) {
            if ("infCad".equals(child.getName())) {
                infCadElements.add(child);
            }
        }
        
        logger.info("=== DEBUG INFCAD PARSING ===");
        logger.info("Total infCad elements found: {}", infCadElements.size());
        
        for (int i = 0; i < infCadElements.size(); i++) {
            Element infCadElement = infCadElements.get(i);
            logger.info("Processing infCad[{}]: name={}, namespace={}", i, infCadElement.getName(), infCadElement.getNamespaceURI());
            logger.info("infCad[{}] children count: {}", i, infCadElement.getChildren().size());
            
            // Log todos os filhos do infCad
            for (Element child : infCadElement.getChildren()) {
                logger.info("  infCad[{}] child: {} = '{}'", i, child.getName(), child.getTextTrim());
            }
            
            InfCad infCad = parseInfCad(infCadElement);
            if (infCad != null) {
                logger.info("✓ infCad[{}] parsed successfully", i);
                infCadList.add(infCad);
            } else {
                logger.warn("✗ infCad[{}] parsing failed", i);
            }
        }
        
        infCons.setInfCad(infCadList);
        
        return infCons;
    }
    
    /**
     * Faz parse do elemento infCad
     */
    private InfCad parseInfCad(Element infCadElement) {
        InfCad infCad = new InfCad();
        
        infCad.setIe(getElementText(infCadElement, "IE"));
        infCad.setCnpj(getElementText(infCadElement, "CNPJ"));
        infCad.setCpf(getElementText(infCadElement, "CPF"));
        infCad.setUf(getElementText(infCadElement, "UF"));
        infCad.setcSit(getElementText(infCadElement, "cSit"));
        infCad.setIndCredNFe(getElementText(infCadElement, "indCredNFe"));
        infCad.setIndCredCTe(getElementText(infCadElement, "indCredCTe"));
        infCad.setxNome(getElementText(infCadElement, "xNome"));
        infCad.setxFant(getElementText(infCadElement, "xFant"));
        infCad.setxRegApur(getElementText(infCadElement, "xRegApur"));
        infCad.setCnae(getElementText(infCadElement, "CNAE"));
        infCad.setdIniAtiv(getElementText(infCadElement, "dIniAtiv"));
        infCad.setdUltSit(getElementText(infCadElement, "dUltSit"));
        infCad.setdBaixa(getElementText(infCadElement, "dBaixa"));
        infCad.setIeUnica(getElementText(infCadElement, "IEUnica"));
        infCad.setIeAtual(getElementText(infCadElement, "IEAtual"));
        
        // Parse do endereço - busca considerando namespace
        Element enderElement = null;
        for (Element child : infCadElement.getChildren()) {
            if ("ender".equals(child.getName())) {
                enderElement = child;
                break;
            }
        }
        if (enderElement != null) {
            Endereco endereco = parseEndereco(enderElement);
            infCad.setEnder(endereco);
        }
        
        return infCad;
    }
    
    /**
     * Faz parse do elemento endereco
     */
    private Endereco parseEndereco(Element enderElement) {
        Endereco endereco = new Endereco();
        
        endereco.setxLgr(getElementText(enderElement, "xLgr"));
        endereco.setNro(getElementText(enderElement, "nro"));
        endereco.setxCpl(getElementText(enderElement, "xCpl"));
        endereco.setxBairro(getElementText(enderElement, "xBairro"));
        endereco.setcMun(getElementText(enderElement, "cMun"));
        endereco.setxMun(getElementText(enderElement, "xMun"));
        endereco.setCep(getElementText(enderElement, "CEP"));
        
        return endereco;
    }
    
    /**
     * Obtém texto de um elemento filho (considerando namespace)
     */
    private String getElementText(Element parent, String childName) {
        // Primeiro tenta sem namespace
        Element child = parent.getChild(childName);
        if (child != null) {
            return child.getTextTrim();
        }
        
        // Se não encontrou, tenta com namespace do pai
        if (parent.getNamespaceURI() != null) {
            child = parent.getChild(childName, parent.getNamespace());
            if (child != null) {
                return child.getTextTrim();
            }
        }
        
        return null;
    }
    
    /**
     * Encontra elemento recursivamente
     */
    private Element encontrarElemento(Element parent, String elementName) {
        if (parent.getName().equals(elementName)) {
            return parent;
        }
        
        for (Element child : parent.getChildren()) {
            Element found = encontrarElemento(child, elementName);
            if (found != null) {
                return found;
            }
        }
        
        return null;
    }
    
    // Getters e Setters
    public InfCons getInfCons() {
        return infCons;
    }
    
    public void setInfCons(InfCons infCons) {
        this.infCons = infCons;
    }
    
    /**
     * Classe interna para informações da consulta
     */
    public static class InfCons {
        private String verAplic;
        private String cStat;
        private String xMotivo;
        private String uf;
        private String cnpj;
        private String cpf;
        private String dhCons;
        private String cUF;
        private List<InfCad> infCad;
        
        // Getters e Setters
        public String getVerAplic() {
            return verAplic;
        }
        
        public void setVerAplic(String verAplic) {
            this.verAplic = verAplic;
        }
        
        public String getcStat() {
            return cStat;
        }
        
        public void setcStat(String cStat) {
            this.cStat = cStat;
        }
        
        public String getxMotivo() {
            return xMotivo;
        }
        
        public void setxMotivo(String xMotivo) {
            this.xMotivo = xMotivo;
        }
        
        public String getUf() {
            return uf;
        }
        
        public void setUf(String uf) {
            this.uf = uf;
        }
        
        public String getCnpj() {
            return cnpj;
        }
        
        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }
        
        public String getCpf() {
            return cpf;
        }
        
        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
        
        public String getDhCons() {
            return dhCons;
        }
        
        public void setDhCons(String dhCons) {
            this.dhCons = dhCons;
        }
        
        public String getcUF() {
            return cUF;
        }
        
        public void setcUF(String cUF) {
            this.cUF = cUF;
        }
        
        public List<InfCad> getInfCad() {
            return infCad;
        }
        
        public void setInfCad(List<InfCad> infCad) {
            this.infCad = infCad;
        }
    }
    
    /**
     * Classe interna para informações cadastrais
     */
    public static class InfCad {
        private String ie;
        private String cnpj;
        private String cpf;
        private String uf;
        private String cSit;
        private String indCredNFe;
        private String indCredCTe;
        private String xNome;
        private String xFant;
        private String xRegApur;
        private String cnae;
        private String dIniAtiv;
        private String dUltSit;
        private String dBaixa;
        private String ieUnica;
        private String ieAtual;
        private Endereco ender;
        
        // Getters e Setters
        public String getIe() {
            return ie;
        }
        
        public void setIe(String ie) {
            this.ie = ie;
        }
        
        public String getCnpj() {
            return cnpj;
        }
        
        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }
        
        public String getCpf() {
            return cpf;
        }
        
        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
        
        public String getUf() {
            return uf;
        }
        
        public void setUf(String uf) {
            this.uf = uf;
        }
        
        public String getcSit() {
            return cSit;
        }
        
        public void setcSit(String cSit) {
            this.cSit = cSit;
        }
        
        public String getIndCredNFe() {
            return indCredNFe;
        }
        
        public void setIndCredNFe(String indCredNFe) {
            this.indCredNFe = indCredNFe;
        }
        
        public String getIndCredCTe() {
            return indCredCTe;
        }
        
        public void setIndCredCTe(String indCredCTe) {
            this.indCredCTe = indCredCTe;
        }
        
        public String getxNome() {
            return xNome;
        }
        
        public void setxNome(String xNome) {
            this.xNome = xNome;
        }
        
        public String getxFant() {
            return xFant;
        }
        
        public void setxFant(String xFant) {
            this.xFant = xFant;
        }
        
        public String getxRegApur() {
            return xRegApur;
        }
        
        public void setxRegApur(String xRegApur) {
            this.xRegApur = xRegApur;
        }
        
        public String getCnae() {
            return cnae;
        }
        
        public void setCnae(String cnae) {
            this.cnae = cnae;
        }
        
        public String getdIniAtiv() {
            return dIniAtiv;
        }
        
        public void setdIniAtiv(String dIniAtiv) {
            this.dIniAtiv = dIniAtiv;
        }
        
        public String getdUltSit() {
            return dUltSit;
        }
        
        public void setdUltSit(String dUltSit) {
            this.dUltSit = dUltSit;
        }
        
        public String getdBaixa() {
            return dBaixa;
        }
        
        public void setdBaixa(String dBaixa) {
            this.dBaixa = dBaixa;
        }
        
        public String getIeUnica() {
            return ieUnica;
        }
        
        public void setIeUnica(String ieUnica) {
            this.ieUnica = ieUnica;
        }
        
        public String getIeAtual() {
            return ieAtual;
        }
        
        public void setIeAtual(String ieAtual) {
            this.ieAtual = ieAtual;
        }
        
        public Endereco getEnder() {
            return ender;
        }
        
        public void setEnder(Endereco ender) {
            this.ender = ender;
        }
    }
    
    /**
     * Classe interna para endereço
     */
    public static class Endereco {
        private String xLgr;
        private String nro;
        private String xCpl;
        private String xBairro;
        private String cMun;
        private String xMun;
        private String cep;
        
        // Getters e Setters
        public String getxLgr() {
            return xLgr;
        }
        
        public void setxLgr(String xLgr) {
            this.xLgr = xLgr;
        }
        
        public String getNro() {
            return nro;
        }
        
        public void setNro(String nro) {
            this.nro = nro;
        }
        
        public String getxCpl() {
            return xCpl;
        }
        
        public void setxCpl(String xCpl) {
            this.xCpl = xCpl;
        }
        
        public String getxBairro() {
            return xBairro;
        }
        
        public void setxBairro(String xBairro) {
            this.xBairro = xBairro;
        }
        
        public String getcMun() {
            return cMun;
        }
        
        public void setcMun(String cMun) {
            this.cMun = cMun;
        }
        
        public String getxMun() {
            return xMun;
        }
        
        public void setxMun(String xMun) {
            this.xMun = xMun;
        }
        
        public String getCep() {
            return cep;
        }
        
        public void setCep(String cep) {
            this.cep = cep;
        }
    }
}


