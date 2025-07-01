package br.com.triagem_ia_sus.triagem_ia_sus.dto;

public record InputCreatePacienteDTO(
        String nome,
        String cpf,
        String dtNascimento,
        String endereco,
        String telefone
) {
}
