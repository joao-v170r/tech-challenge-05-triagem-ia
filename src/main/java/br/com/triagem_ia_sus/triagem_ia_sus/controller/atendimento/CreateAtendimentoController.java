package br.com.triagem_ia_sus.triagem_ia_sus.controller.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.InputCreateAtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento.CreateAtendimentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("create-atendimento")
@Tag(name = "atendimento", description = "Endpoints de gerenciamento de atendimento")
public class CreateAtendimentoController {

    private final CreateAtendimentoUseCase useCase;

    @PostMapping
    @Operation(summary = "Cria atendimento")
    public ResponseEntity<AtendimentoDTO> create(InputCreateAtendimentoDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.create(request));
    }
}