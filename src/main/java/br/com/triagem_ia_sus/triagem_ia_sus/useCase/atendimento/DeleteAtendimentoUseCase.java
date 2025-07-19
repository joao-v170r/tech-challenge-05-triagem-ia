package br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Atendimento;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.AtendimentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAtendimentoUseCase {

    private final AtendimentoRepository repository;

    public void delete(String id) {
        Atendimento atendimento = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Atendimento não encontrado."));
        repository.delete(atendimento);
    }
}
