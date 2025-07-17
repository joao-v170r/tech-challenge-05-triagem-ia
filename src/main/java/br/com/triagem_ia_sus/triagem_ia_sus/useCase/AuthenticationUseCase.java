//package br.com.triagem_ia_sus.triagem_ia_sus.useCase;
//
//import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
//import br.com.triagem_ia_sus.triagem_ia_sus.dto.UserResponseDTO;
//import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
//import br.com.triagem_ia_sus.triagem_ia_sus.security.TokenService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class AuthenticationUseCase {
//
//
//    private final AuthenticationManager authenticationManager;
//
//    private final TokenService tokenService;
//
//    public String generateToken(UsernamePasswordAuthenticationToken authenticationToken) {
//        var auth = authenticationManager.authenticate(authenticationToken);
//        return tokenService.generateToken((Colaborador) auth.getPrincipal());
//    }
//}
