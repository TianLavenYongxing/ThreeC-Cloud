package com.threec.auth.utils;

import com.threec.auth.enums.ResultEnums;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtils {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds
    private static final String ISSUER = "your_issuer";

    public static String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder().setSubject(subject).setIssuer(ISSUER).setIssuedAt(now).setExpiration(expiryDate).signWith(SECRET_KEY).compact();
    }



    public static String getUsernameFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    public static Date getExpirationDateFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        return claimsJws.getBody().getExpiration();
    }
    public static JWTValidationResult validateToken(String jwt) {
        String token = jwt.substring(7);
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return JWTValidationResult.success(claims.getBody());
        } catch (ExpiredJwtException e) {
            return JWTValidationResult.failure(ResultEnums.JWT_EXPIRED.getMsg());
        } catch (UnsupportedJwtException e) {
            return JWTValidationResult.failure(ResultEnums.JWT_UNSUPPORTED.getMsg());
        } catch (MalformedJwtException e) {
            return JWTValidationResult.failure(ResultEnums.JWT_MALFORMED.getMsg());
        }  catch (SignatureException e) {
            return JWTValidationResult.failure(ResultEnums.JWT_INVALID_SIGNATURE.getMsg());
        }catch (IllegalArgumentException e) {
            return JWTValidationResult.failure(ResultEnums.JWT_ILLEGAL_PARAM.getMsg());
        }
    }
}
