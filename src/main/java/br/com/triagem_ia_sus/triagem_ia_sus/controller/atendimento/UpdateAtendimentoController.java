package br.com.triagem_ia_sus.triagem_ia_sus.controller.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.InputUpdateAtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento.UpdateAtendimentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("update-atendimento")
@Tag(name = "atendimento", description = "Endpoints de gerenciamento de atendimento")
public class UpdateAtendimentoController {
    private final UpdateAtendimentoUseCase useCase;

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza atendimento")
    public ResponseEntity<AtendimentoDTO> update(
            @PathVariable String id,
            @RequestBody InputUpdateAtendimentoDTO request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(useCase.update(id, request, httpRequest));
    }
}
