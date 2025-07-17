package br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador;

public record InputCreateColaboradorDTO(
        String nome,
        String dataNascimento,
        String email,
        String senha,
        String tipoColaborador
) {
}