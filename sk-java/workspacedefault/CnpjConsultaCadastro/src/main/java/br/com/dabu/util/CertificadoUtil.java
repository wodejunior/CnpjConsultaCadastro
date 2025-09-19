package br.com.dabu.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Utilitário para manipulação de certificados A1 (PKCS#12)
 */
public class CertificadoUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(CertificadoUtil.class);
    
    /**
     * Carrega certificado A1 do arquivo .p12/.pfx
     * 
     * @param caminhoArquivo Caminho para o arquivo do certificado
     * @param senha Senha do certificado
     * @return KeyStore carregado ou null em caso de erro
     */
    public static KeyStore carregarCertificado(String caminhoArquivo, String senha) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            logger.error("Caminho do certificado não pode ser nulo ou vazio");
            return null;
        }
        
        if (senha == null) {
            logger.error("Senha do certificado não pode ser nula");
            return null;
        }
        
        try {
            logger.info("Carregando certificado A1: {}", caminhoArquivo);
            
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            
            try (FileInputStream fis = new FileInputStream(caminhoArquivo)) {
                keyStore.load(fis, senha.toCharArray());
            }
            
            logger.info("Certificado A1 carregado com sucesso");
            return keyStore;
            
        } catch (KeyStoreException e) {
            logger.error("Erro ao criar KeyStore: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Erro ao ler arquivo do certificado {}: {}", caminhoArquivo, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Algoritmo não suportado: {}", e.getMessage(), e);
        } catch (CertificateException e) {
            logger.error("Erro no certificado: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado ao carregar certificado: {}", e.getMessage(), e);
        }
        
        return null;
    }
    
    /**
     * Cria SSLContext configurado com o certificado A1
     * 
     * @param keyStore KeyStore com o certificado
     * @param senha Senha do certificado
     * @return SSLContext configurado ou null em caso de erro
     */
    public static SSLContext criarSSLContext(KeyStore keyStore, String senha) {
        if (keyStore == null) {
            logger.error("KeyStore não pode ser nulo");
            return null;
        }
        
        try {
            // Configura KeyManager com o certificado
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, senha.toCharArray());
            
            // Configura TrustManager (aceita todos os certificados - apenas para desenvolvimento)
            TrustManager[] trustManagers = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        // Aceita todos os certificados de cliente
                    }
                    
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        // Aceita todos os certificados de servidor
                    }
                    
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
            };
            
            // Cria SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), trustManagers, null);
            
            logger.info("SSLContext criado com sucesso");
            return sslContext;
            
        } catch (Exception e) {
            logger.error("Erro ao criar SSLContext: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Valida se o certificado está dentro do prazo de validade
     * 
     * @param keyStore KeyStore com o certificado
     * @return true se válido, false caso contrário
     */
    public static boolean validarCertificado(KeyStore keyStore) {
        if (keyStore == null) {
            return false;
        }
        
        try {
            String alias = keyStore.aliases().nextElement();
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            
            if (certificate == null) {
                logger.error("Certificado não encontrado no KeyStore");
                return false;
            }
            
            // Verifica validade
            certificate.checkValidity();
            
            logger.info("Certificado válido até: {}", certificate.getNotAfter());
            return true;
            
        } catch (Exception e) {
            logger.error("Erro ao validar certificado: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtém informações do certificado
     * 
     * @param keyStore KeyStore com o certificado
     * @return String com informações do certificado ou null em caso de erro
     */
    public static String obterInformacoesCertificado(KeyStore keyStore) {
        if (keyStore == null) {
            return null;
        }
        
        try {
            String alias = keyStore.aliases().nextElement();
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            
            if (certificate == null) {
                return "Certificado não encontrado";
            }
            
            StringBuilder info = new StringBuilder();
            info.append("Subject: ").append(certificate.getSubjectDN().getName()).append("\n");
            info.append("Issuer: ").append(certificate.getIssuerDN().getName()).append("\n");
            info.append("Serial Number: ").append(certificate.getSerialNumber()).append("\n");
            info.append("Valid From: ").append(certificate.getNotBefore()).append("\n");
            info.append("Valid Until: ").append(certificate.getNotAfter()).append("\n");
            
            return info.toString();
            
        } catch (Exception e) {
            logger.error("Erro ao obter informações do certificado: {}", e.getMessage(), e);
            return "Erro ao obter informações: " + e.getMessage();
        }
    }
}

