package br.com.triagem_ia_sus.triagem_ia_sus.controller;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.InputCreatePacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.PacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.CreatePacienteUseCase;
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
@RequestMapping("create-paciente")
@Tag(name = "paciente", description = "Endpoints de gerenciamento de pacientes")
public class CreatePacienteController {

    private final CreatePacienteUseCase useCase;

    @PostMapping
    @Operation(summary = "cria paciente")
    public ResponseEntity<PacienteDTO> create(InputCreatePacienteDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.create(request));
    }
}
