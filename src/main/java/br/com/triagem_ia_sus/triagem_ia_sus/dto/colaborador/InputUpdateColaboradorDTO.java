package br.com.triagem_ia_sus.triagem_ia_sus.dto.colaborador;

public record InputUpdateColaboradorDTO(
    String nome,
    String dataNascimento,
    String email,
    String tipoColaborador
) {
}
