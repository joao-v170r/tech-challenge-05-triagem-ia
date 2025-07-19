//package br.com.triagem_ia_sus.triagem_ia_sus.security;
//
//import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
//import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
//import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class SecurityFilter extends OncePerRequestFilter {
//
//    @Autowired
//    TokenService tokenService;
//
//    @Autowired
//    ColaboradorRepository colaboradorRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        var token = this.recoverToken(request);
//
//        if(token != null) {
//            var login = tokenService.validateToken(token);
//            UserDetails userDetails = colaboradorRepository.findByEmail(login);
//
//            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request,response);
//    }
//
//
//    private String recoverToken(HttpServletRequest request) {
//        var authHeader = request.getHeader("Authorization");
//        if(authHeader == null) return null;
//        return authHeader.replace("Bearer ", "");
//    }
//}
