package com.fntech.auth_service.security;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration 
public class RsaKeyConfig {
    
    @Value ("${application.security.jwt.keystore-location}")
    private Resource keystoreLocation;

    @Value("${application.security.jwt.keystore-password}")
    private String keystorePassword;

    @Value("${application.security.jwt.key-alias}")
    private String keyAlias;

    @Bean
    public KeyPair jwtKeyPair() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(keystoreLocation.getInputStream(), keystorePassword.toCharArray());

        RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(keyAlias, keystorePassword.toCharArray());
        RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(keyAlias).getPublicKey();

        return new KeyPair(publicKey, privateKey);
    }
}
