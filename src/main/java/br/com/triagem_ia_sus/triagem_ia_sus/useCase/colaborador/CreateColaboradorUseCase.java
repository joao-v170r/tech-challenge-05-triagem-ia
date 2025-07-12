package br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.domain.TipoColaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.InputCreateColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateColaboradorUseCase {

    private final ColaboradorRepository repository;

    public ColaboradorDTO create(InputCreateColaboradorDTO dto) {
        if (repository.findByEmail(dto.email()) != null) {
            throw new RuntimeException("Já existe um colaborador com esse email");
        }

        if (LocalDate.parse(dto.dateTimeNascimento()).isAfter(LocalDate.now())) {
            throw new RuntimeException("Data de nascimento invalida", new IllegalAccessException("A data de nascimento não pode ser uma data futura"));
        }

        return new ColaboradorDTO(repository.save(new Colaborador(
                null,
                dto.nome(),
                LocalDateTime.parse(dto.dateTimeNascimento()),
                dto.email(),
                dto.senha(),
                TipoColaborador.valueOf(dto.tipoColaborador())
        )));
    }
}
