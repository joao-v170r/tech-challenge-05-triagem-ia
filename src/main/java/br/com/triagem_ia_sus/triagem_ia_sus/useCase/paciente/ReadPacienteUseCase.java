package br.com.triagem_ia_sus.triagem_ia_sus.useCase.paciente;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente.PacienteDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReadPacienteUseCase {

    private final PacienteRepository repository;

    public PacienteDTO findById(String id) {
        return new PacienteDTO(repository.findById(id).orElseThrow(
                () -> new RuntimeException("paciente de id: " + id + " não encontrado")));
    }

    public Page<PacienteDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(PacienteDTO::new);
    }
}
