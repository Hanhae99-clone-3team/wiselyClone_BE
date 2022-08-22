package com.hanghae.wisely.jwt;

import com.hanghae.wisely.domain.AuthMember;
import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.exception.BadRequestException;
import com.hanghae.wisely.service.CustomAccountDetailsService;
import com.hanghae.wisely.service.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtProvider {
    private  final Key key;
    private final RedisService redisService;
//    private final CustomAccountDetailsService customAccountDetailsService;
private final CustomAccountDetailsService customAccountDetailsService;

    public JwtProvider(@Value("${jwt.secret-key}")String SECRET_KEY, RedisService redisService, CustomAccountDetailsService customAccountDetailsService){
        this.redisService = redisService;
        this.customAccountDetailsService = customAccountDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userId, String roles) {
        Long tokenInvalidTime = 1000L * 60 * 3; // 3m
        return this.createToken(userId, roles, tokenInvalidTime);
    }

    public String createRefreshToken(String userId, String roles) {
        Long tokenInvalidTime = 1000L * 60 * 60 * 24; // 1d
        String refreshToken = this.createToken(userId, roles, tokenInvalidTime);
        redisService.setValues(userId, refreshToken, Duration.ofMillis(tokenInvalidTime));
        return refreshToken;
    }

    public String createToken(String userId, String roles,Long tokenInvalidTime){
        Claims claims = Jwts.claims().setSubject(userId); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenInvalidTime)) // 토큰 유효 시간 저장
                .signWith(key,SignatureAlgorithm.HS256) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }

    public void checkRefreshToken(String userId, String refreshToken) {
        String redisRT = redisService.getValues(userId);
        if (!refreshToken.equals(redisRT)) {
            throw new BadRequestException("토큰이 만료되었습니다.");
        }
    }

    public Authentication validateToken(HttpServletRequest request,String token) {
        String exception = "exception";
        try {
//            String expiredAT = redisService.getValues(blackListATPrefix + token);
//            if (expiredAT != null) {
//                throw new ExpiredJwtException(null, null, null);
//            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return getAuthentication(token);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            request.setAttribute(exception, "토큰의 형식을 확인하세요");
        } catch (ExpiredJwtException e) {
            request.setAttribute(exception, "토큰이 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute(exception, "JWT compact of handler are invalid");
        }
        return null;
    }




    private Authentication getAuthentication(String token) {
        UserDetails userDetails = customAccountDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Member getMemberFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return null;
        }
        return ((AuthMember) authentication.getPrincipal()).getMember();
    }


    private String getUserEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }



}
