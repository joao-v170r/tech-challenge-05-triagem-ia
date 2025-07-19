package br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.AtendimentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReadAtendimentoUseCase {

    private final AtendimentoRepository repository;

    public AtendimentoDTO findById(String id) {
        return new AtendimentoDTO(repository.findById(id).orElseThrow(
                () -> new RuntimeException("Atendimento de id: " + id + " não encontrado")));
    }

    public Page<AtendimentoDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(AtendimentoDTO::new);
    }
}