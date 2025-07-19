package br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente;

public record InputUpdatePacienteDTO(
    String nome,
    String dataNascimento,
    String endereco,
    String telefone
) {
}
