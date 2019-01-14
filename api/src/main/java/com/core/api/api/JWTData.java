package com.core.api.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class JWTData {

    private Jws<Claims> claims;

    public void setClaims(Jws<Claims> claims) {
        this.claims = claims;
    }

    public String get(String key) {
        return claims.getBody().get(key).toString();
    }
}