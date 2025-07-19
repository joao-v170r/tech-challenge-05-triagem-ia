package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor; // Adicione se o Spring Data precisar
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode; // Importe esta anotação
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tb_paciente")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paciente {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nome;
    @Indexed(unique = true)
    @EqualsAndHashCode.Include
    private String cpf;
    private LocalDate dataNascimento;
    private String endereco;
    private String telefone;
}