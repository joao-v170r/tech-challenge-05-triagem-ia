package br.com.triagem_ia_sus.triagem_ia_sus.controller.paciente;

import br.com.triagem_ia_sus.triagem_ia_sus.useCase.paciente.DeletePacienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("delete-paciente")
@Tag(name = "paciente", description = "Endpoints de gerenciamento de pacientes")
public class DeletePacienteController {

    private final DeletePacienteUseCase useCase;

    @DeleteMapping
    @Operation(summary = "deletar paciente")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
