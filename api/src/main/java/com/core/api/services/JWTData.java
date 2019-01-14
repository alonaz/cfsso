package com.core.api.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JWTData {

    private Jws<Claims> claims;

    public void setClaims(Jws<Claims> claims) {
        this.claims = claims;
    }

    public String get(String key) {
        if (claims != null) {
            return claims.getBody().get(key).toString();
        } else {
            return null;
        }
    }
}