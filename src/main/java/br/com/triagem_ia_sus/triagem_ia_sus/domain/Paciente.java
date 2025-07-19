package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Document(collection = "tb_paciente")
public class Paciente {
    @Id
    private String id;
    private String nome;
    @Indexed(unique = true)
    private String cpf;
    private LocalDate dataNascimento;
    private String endereco;
    private String telefone;
}