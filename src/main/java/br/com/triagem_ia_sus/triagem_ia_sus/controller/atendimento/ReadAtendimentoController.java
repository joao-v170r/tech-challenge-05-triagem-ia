package br.com.triagem_ia_sus.triagem_ia_sus.controller.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento.ReadAtendimentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("atendimento")
@Tag(name = "atendimento", description = "Endpoints de gerenciamento de atendimento")
public class ReadAtendimentoController {
    private final ReadAtendimentoUseCase useCase;

    @GetMapping("/{id}")
    @Operation(summary = "Busca atendimento por id")
    public ResponseEntity<AtendimentoDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(useCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os atendimentos")
    public ResponseEntity<Page<AtendimentoDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(useCase.findAll(pageable));
    }
}