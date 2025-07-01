package br.com.triagem_ia_sus.triagem_ia_sus.controller;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.InputUpdatePacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.PacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.UpdatePacienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("update-paciente")
@AllArgsConstructor
@Tag(name = "paciente", description = "Endpoints de gerenciamento de pacientes")
public class UpdatePacienteController {

    private final UpdatePacienteUseCase useCase;

    @PutMapping("/{id}")
    @Operation(summary = "atualiza paciente")
    public ResponseEntity<PacienteDTO> update(@PathVariable String id, @RequestBody InputUpdatePacienteDTO request) {
        return ResponseEntity.ok(useCase.update(id, request));
    }
}
