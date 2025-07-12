package br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteColaboradorUseCase {

    private final ColaboradorRepository repository;

    public void delete(String id) {
        Colaborador colaborador = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Colaborador não encontrado."));

        repository.delete(colaborador);
    }
}
