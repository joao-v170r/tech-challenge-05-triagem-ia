package br.com.triagem_ia_sus.triagem_ia_sus.controller.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.InputUpdateColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador.UpdateColaboradorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("update-colaborador")
@AllArgsConstructor
@Tag(name = "colaborador", description = "Endpoints de gerenciamento de colaboradores")
public class UpdateColaboradorController {

    private final UpdateColaboradorUseCase useCase;

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza colaborador")
    public ResponseEntity<ColaboradorDTO> update(@PathVariable String id, @RequestBody InputUpdateColaboradorDTO request) {
        return ResponseEntity.ok(useCase.update(id, request));
    }
}
