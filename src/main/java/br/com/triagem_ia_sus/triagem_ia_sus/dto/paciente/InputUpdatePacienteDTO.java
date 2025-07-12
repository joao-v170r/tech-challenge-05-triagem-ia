package br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente;

public record InputUpdatePacienteDTO(
    String nome,
    String dtNascimento,
    String endereco,
    String telefone
) {
}
