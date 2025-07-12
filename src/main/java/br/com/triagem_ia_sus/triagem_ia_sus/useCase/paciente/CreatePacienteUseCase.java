package br.com.triagem_ia_sus.triagem_ia_sus.useCase.paciente;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente.InputCreatePacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente.PacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CreatePacienteUseCase {

    private final PacienteRepository repository;

    public PacienteDTO create(InputCreatePacienteDTO dto) {
        if(repository.findByCpf(dto.cpf()).isPresent()) {
            throw new RuntimeException("ja existe um paciente com esse cpf");
        }

        if(LocalDate.parse(dto.dtNascimento()).isAfter(LocalDate.now())) {
            throw new RuntimeException("data de nascimento invalida", new IllegalAccessException("a data de nascimento nao pode ser uma data futura"));
        }

        return new PacienteDTO(repository.save(new Paciente(
                null,
                dto.nome(),
                dto.cpf(),
                LocalDate.parse(dto.dtNascimento()),
                dto.endereco(),
                dto.telefone()
        )));
    }
}
