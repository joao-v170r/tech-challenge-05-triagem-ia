package br.com.triagem_ia_sus.triagem_ia_sus.dto.paciente;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.Paciente;

import java.time.LocalDate;

public record PacienteDTO(
        String id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String endereco,
        String telefone
) {
    /**
     * Construtor que converte uma entidade Paciente em um PacienteDTO.
     * @param paciente A entidade a ser convertida.
     */
    public PacienteDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getEndereco(),
                paciente.getTelefone()
        );
    }
}
