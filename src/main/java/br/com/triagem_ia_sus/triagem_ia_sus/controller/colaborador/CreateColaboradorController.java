package br.com.triagem_ia_sus.triagem_ia_sus.controller.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.InputCreateColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador.CreateColaboradorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("create-colaborador")
@Tag(name = "colaborador", description = "Endpoints de gerenciamento de colaboradores")
public class CreateColaboradorController {

    private final CreateColaboradorUseCase useCase;

    @PostMapping
    @Operation(summary = "Cria colaborador")
    public ResponseEntity<ColaboradorDTO> create(@RequestBody InputCreateColaboradorDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(useCase.create(request));
    }
}
