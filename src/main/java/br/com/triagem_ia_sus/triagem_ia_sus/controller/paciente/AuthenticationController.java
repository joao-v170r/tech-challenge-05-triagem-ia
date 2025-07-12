package br.com.triagem_ia_sus.triagem_ia_sus.controller.paciente;


import br.com.triagem_ia_sus.triagem_ia_sus.dto.UserAuthDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.AuthenticationUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationUseCase useCase;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserAuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        return ResponseEntity.ok(useCase.generateToken(usernamePassword));
    }
}