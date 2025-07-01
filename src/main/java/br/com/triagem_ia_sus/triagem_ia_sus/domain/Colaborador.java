package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "tb_colaborador")
public class Colaborador {
    private String id;
    private String nome;
    private LocalDateTime dateTimeNascimento;
    @Indexed(unique = true)
    private String email;
    private String senhaHash;
    private TipoColaborador tipo;
}
