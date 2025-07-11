package br.com.triagem_ia_sus.triagem_ia_sus.useCase;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.InputUpdatePacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.PacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UpdatePacienteUseCase {
    private final PacienteRepository repository;

    public PacienteDTO update(String id, InputUpdatePacienteDTO dto) {
        Paciente paciente = repository.findById(id).orElseThrow(
                () -> new RuntimeException("paciente não existe", new IllegalArgumentException("id não encontrado"))
        );

        if(dto.nome() != null) {
            paciente.setNome(dto.nome());
        }

        if(dto.dtNascimento() != null) {
            if(LocalDate.parse(dto.dtNascimento()).isAfter(LocalDate.now())) {
                throw new RuntimeException("data de nascimento invalida", new IllegalAccessException("a data de nascimento nao pode ser uma data futura"));
            }
            paciente.setDtNascimento(LocalDate.parse(dto.dtNascimento()));
        }

        if(dto.endereco() != null) {
            paciente.setEndereco(dto.endereco());
        }

        if(dto.telefone() != null) {
            paciente.setTelefone(dto.telefone());
        }

        return new PacienteDTO(repository.save(paciente));
    }
}
