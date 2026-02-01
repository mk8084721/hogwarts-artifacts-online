package com.mfk.hogwarts_artifacts_online.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtEncoder jwtEncoder;
    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiresIn = 2; //2 hours.

        //Prepare a claim called authorities.
        String authorities = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .subject(authentication.getName())
                .expiresAt(now.plus(expiresIn, ChronoUnit.HOURS))
                .issuedAt(now)
                .claim("authorities",authorities)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
