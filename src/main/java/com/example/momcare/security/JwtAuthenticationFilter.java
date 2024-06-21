package com.example.momcare.security;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter{}// extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        if(authHeader == null ||!authHeader.startsWith("Bearer ")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        //userEmail = jwtService.extractUsername(jwt);
//        //if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
//
//        }
//    }

