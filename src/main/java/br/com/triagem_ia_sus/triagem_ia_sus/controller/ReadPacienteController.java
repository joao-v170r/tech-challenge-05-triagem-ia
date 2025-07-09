package br.com.triagem_ia_sus.triagem_ia_sus.controller;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.PacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.ReadPacienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("paciente")
@Tag(name = "paciente", description = "Endpoints de gerenciamento de pacientes")
public class ReadPacienteController {
    private final ReadPacienteUseCase useCase;

    @GetMapping("/{id}")
    @Operation(summary = "busca paciente por id")
    public ResponseEntity<PacienteDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(useCase.findById(id));
    }

    @GetMapping
    @Operation(summary = "lista todos os pacientes")
    public ResponseEntity<Page<PacienteDTO>> findAll(@RequestBody Pageable pageable) {
        return ResponseEntity.ok(useCase.findAll(pageable));
    }
}
