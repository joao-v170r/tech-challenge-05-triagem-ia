package br.com.triagem_ia_sus.triagem_ia_sus.useCase.paciente;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeletePacienteUseCase {

    private final PacienteRepository repository;

    public void delete(String id) {
        Paciente paciente = repository.findById(id).orElseThrow(
                () -> new RuntimeException("paciente não encontrado"));

        repository.delete(paciente);
    }
}
