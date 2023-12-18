package com.example.bookingappteam17.clients;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtUtils {
    public static String getRoleFromToken(String token) {
        try {
            // Decode the JWT token to get claims
            Claims claims = Jwts.parser()
                    .setSigningKey("nekasifranekasifranekasifranekasifranekasifranekasifranekasifranekasifranekasifranekasifranekasifra".getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();

            // Extract the 'role' claim as a List
            List<?> roleClaim = claims.get("role", List.class);

            if (roleClaim != null && roleClaim.size() > 0) {
                // Get the first element from the 'role' claim
                Object firstElement = roleClaim.get(0);

                if (firstElement instanceof Map) {
                    // Check if the first element is a Map
                    Map<?, ?> roleMap = (Map<?, ?>) firstElement;

                    // Get the 'authority' value from the nested Map
                    Object authority = roleMap.get("authority");

                    if (authority instanceof String) {
                        // Check if the 'authority' value is a String
                        return (String) authority;
                    }
                }
            }

            return null; // Handle the case where the 'role' claim structure is unexpected
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle the exception according to your requirements
        }
    }

}