package br.com.triagem_ia_sus.triagem_ia_sus.controller.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador.DeleteColaboradorUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("delete-colaborador")
@Tag(name = "colaborador", description = "Endpoints de gerenciamento de colaboradores")
public class DeleteColaboradorController {

    private final DeleteColaboradorUseCase useCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
