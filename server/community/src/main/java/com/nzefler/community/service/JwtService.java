package com.nzefler.community.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private String secret = "superlongrandomsecretkeyforjwtthatshouldbeatleast32characters";

    public String generateToken(String userName){
        return Jwts.builder().setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractUserName(String token){
        return Jwts.parserBuilder().setSigningKey(secret)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secret).build()
                    .parseClaimsJws(token);
            return true;
        }catch(JwtException e){
            return false;
        }
    }
}
