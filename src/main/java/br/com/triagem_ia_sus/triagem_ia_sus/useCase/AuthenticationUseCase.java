package br.com.triagem_ia_sus.triagem_ia_sus.useCase;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationUseCase {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public String generateToken(UsernamePasswordAuthenticationToken authenticationToken) {
        var auth = authenticationManager.authenticate(authenticationToken);
        return tokenService.generateToken((Colaborador) auth.getPrincipal());
    }
}
