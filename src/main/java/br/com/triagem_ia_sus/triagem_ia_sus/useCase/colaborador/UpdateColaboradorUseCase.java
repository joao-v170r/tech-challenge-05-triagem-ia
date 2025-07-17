package br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.domain.TipoColaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.InputUpdateColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UpdateColaboradorUseCase {
    private final ColaboradorRepository repository;

    public ColaboradorDTO update(String id, InputUpdateColaboradorDTO dto) {
        Colaborador colaborador = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Colaborador não existe", new IllegalArgumentException("Id não encontrado"))
        );

        if (dto.nome() != null) {
            colaborador.setNome(dto.nome());
        }

        if (dto.dataNascimento() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(java.util.Locale.forLanguageTag("pt-BR"));
            LocalDate dataNascimento = LocalDate.parse(dto.dataNascimento(), formatter);
            if (dataNascimento.isAfter(LocalDate.now())) {
                throw new RuntimeException("Data de nascimento invalida", new IllegalAccessException("A data de nascimento não pode ser uma data futura"));
            }
            colaborador.setDataNascimento(dataNascimento);
        }

        if (dto.email() != null) {
            colaborador.setEmail(dto.email());
        }

        if (dto.tipoColaborador() != null) {
            try {
                TipoColaborador tipoColaborador = TipoColaborador.valueOf(dto.tipoColaborador());
                colaborador.setTipoColaborador(tipoColaborador);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo de colaborador inválido: " + dto.tipoColaborador());
            }
        }

        return new ColaboradorDTO(repository.save(colaborador));
    }
}
