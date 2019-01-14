package com.core.api.services;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.core.api.configuration.ApplicationConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {

    @Autowired
    public ApplicationConfiguration conf;
    
    @Autowired
    JWTData jwtData;

    private PublicKey pkey = null;

    public boolean validateAndInitToken(String token) {
        boolean res = false;
        PublicKey pkey = getKey();

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(pkey).parseClaimsJws(token);
            jwtData.setClaims(claims);
            res = true;
        } catch (JwtException e) {
            System.out.println("Error validating token: " + e.toString());
        }

        return res;
    }
    private PublicKey getKey() {
        if (pkey != null) {
            return pkey;
        } else {
            try {
                String publicKey = conf.getRawVerificationKey();
                byte[] byteKey = Base64.getDecoder().decode(publicKey);
                X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
                KeyFactory kf = KeyFactory.getInstance("RSA");        
                return kf.generatePublic(X509publicKey);
            }
            catch(Exception e){
                e.printStackTrace();                        
                return null;
            }
        }
    }
}