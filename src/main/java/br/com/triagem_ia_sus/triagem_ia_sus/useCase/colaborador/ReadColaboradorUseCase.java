package br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReadColaboradorUseCase {

    private final ColaboradorRepository repository;

    public ColaboradorDTO findById(String id) {
        return new ColaboradorDTO(repository.findById(id).orElseThrow(
                () -> new RuntimeException("Colaborador de id: " + id + " não encontrado")));
    }

    public Page<ColaboradorDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ColaboradorDTO::new);
    }
}
