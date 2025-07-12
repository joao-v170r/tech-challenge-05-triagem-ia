package br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Colaborador;
import br.com.triagem_ia_sus.triagem_ia_sus.domain.TipoColaborador;

import java.time.LocalDateTime;

public record ColaboradorDTO(
        String id,
        String nome,
        LocalDateTime dateTimeNascimento,
        String email,
        String senhaHash,
        TipoColaborador tipoColaborador
) {

    public ColaboradorDTO(Colaborador colaborador) {
        this (
                colaborador.getId(),
                colaborador.getNome(),
                colaborador.getDateTimeNascimento(),
                colaborador.getEmail(),
                colaborador.getSenhaHash(),
                colaborador.getTipo()
        );
    }
}
