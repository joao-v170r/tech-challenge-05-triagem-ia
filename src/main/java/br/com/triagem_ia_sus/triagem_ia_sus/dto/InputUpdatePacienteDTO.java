package br.com.triagem_ia_sus.triagem_ia_sus.dto;

public record InputUpdatePacienteDTO(
    String nome,
    String dtNascimento,
    String endereco,
    String telefone
) {
}
