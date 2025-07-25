package br.com.triagem_ia_sus.triagem_ia_sus.controller.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.RealizarAtendimentoIaDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento.AtendimentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("realizar-atendimento")
@Tag(name = "atendimento", description = "Endpoints de gerenciamento de atendimento")
public class AtendimentoController {

    private final AtendimentoUseCase useCase;

    @PostMapping
    @Operation(summary = "Atendimento")
    public ResponseEntity<String> create(@RequestBody RealizarAtendimentoIaDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.atendimento(request));
    }
}