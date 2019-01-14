package com.core.api.services;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.core.api.configuration.VcapServicesUaaPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {

    @Autowired
    public VcapServicesUaaPublicKey vsupk;
    
    private PublicKey pkey = null;

    public boolean validateToken(String token) {
        PublicKey pkey = getKey();
        try {
            Jwts.parser().setSigningKey(pkey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Error validating token: " + e.toString());
            return false;
        }
    }

    public Jws<Claims> getClaims(String token) {
        PublicKey pkey = getKey();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(pkey).parseClaimsJws(token);
            return claims;
        } catch (JwtException e) {
            System.out.println("Error getting claims: " + e.toString());
            return null;
        }
    }

    private PublicKey getKey() {
        if (pkey != null) {
            return pkey;
        } else {
            try {
                String publicKey = vsupk.getRawVerificationKey();
                byte[] byteKey = Base64.getDecoder().decode(publicKey);
                X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
                KeyFactory kf = KeyFactory.getInstance("RSA");
        
                return kf.generatePublic(X509publicKey);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        
            return null;    
        }
    }
}