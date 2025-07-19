package br.com.triagem_ia_sus.triagem_ia_sus.useCase.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.domain.TipoColaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.ColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador.InputCreateColaboradorDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class CreateColaboradorUseCase {

    private final ColaboradorRepository repository;

    public ColaboradorDTO create(InputCreateColaboradorDTO dto) {

        if (repository.findByEmail(dto.email()) != null) {
            throw new RuntimeException("Já existe um colaborador com esse email");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(java.util.Locale.forLanguageTag("pt-BR"));
        LocalDate dataNascimento = LocalDate.parse(dto.dataNascimento(), formatter);

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new RuntimeException("Data de nascimento invalida", new IllegalAccessException("A data de nascimento não pode ser uma data futura"));
        }

        try {
            TipoColaborador tipoColaborador = TipoColaborador.valueOf(dto.tipoColaborador());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de colaborador inválido: " + dto.tipoColaborador());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaHash = encoder.encode(dto.senha());

        return new ColaboradorDTO(repository.save(new Colaborador(
                null,
                dto.nome(),
                LocalDate.parse(dto.dataNascimento(), formatter),
                dto.email(),
                senhaHash,
                TipoColaborador.valueOf(dto.tipoColaborador())
        )));
    }
}
