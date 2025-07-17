package br.com.triagem_ia_sus.triagem_ia_sus.controller.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador.ReadColaboradorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("colaborador")
@Tag(name = "colaborador", description = "Endpoints de gerenciamento de colaboradores")
public class ReadColaboradorController {
    private final ReadColaboradorUseCase useCase;

    @GetMapping("/{id}")
    @Operation(summary = "Busca colaborador por id")
    public ResponseEntity<ColaboradorDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(useCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os colaboradores")
    public ResponseEntity<Page<ColaboradorDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(useCase.findAll(pageable));
    }
}
