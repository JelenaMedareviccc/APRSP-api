package com.example.project.security;

import com.example.project.enums.RoleEnum;
import com.example.project.exception.CustomException;
import com.example.project.services.implementation.*;
import com.example.project.utils.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;


/**
 * This class is used to:
 * -Verify the access token’s signature
 * -Extract identity and authorization claims from Access token and use them to create UserContext
 * -If Access token is malformed, expired or simply if token is not signed with the appropriate signing key
 * Authentication exception will be thrown
 */
@Component
public class JwtTokenProvider {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    public JwtTokenProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * This method generates token for users authentication
     */
    public String createToken(String username, RoleEnum role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role.getAuthority());

        Date now = new Date();
        Date validity = new Date(now.getTime() + ApplicationConstants.expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
    	System.out.println("AUTHENTICATION");
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * This method returns username from token
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * This method removes Bearer from the token
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(ApplicationConstants.header);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * This method validates token (checks if it is expired or invalid)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
