package br.com.triagem_ia_sus.triagem_ia_sus.useCase.paciente;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente.InputUpdatePacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente.PacienteDTO;
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
                () -> new RuntimeException("Paciente não existe", new IllegalArgumentException("id não encontrado"))
        );

        if(dto.nome() != null) {
            paciente.setNome(dto.nome());
        }

        if(dto.dataNascimento() != null) {
            if(LocalDate.parse(dto.dataNascimento()).isAfter(LocalDate.now())) {
                throw new RuntimeException("Data de nascimento invalida", new IllegalAccessException("A data de nascimento não pode ser uma data futura"));
            }
            paciente.setDataNascimento(LocalDate.parse(dto.dataNascimento()));
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
