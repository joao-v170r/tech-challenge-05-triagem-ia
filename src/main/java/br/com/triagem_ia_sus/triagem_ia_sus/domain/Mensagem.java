package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(exclude = "atendimento")
@EqualsAndHashCode(exclude = "atendimento")
@Document(collection = "tb_atendimento_mensagem")
public class Mensagem {
    @Id
    private String id;
    private int numeroSequencial;
    private RoleMensagem roleMensagem;
    @DBRef
    private Atendimento atendimento;
    private String texto;

    public Mensagem(Atendimento atendimento, String texto, int numeroSequencial, RoleMensagem roleMensagem) {
        this.atendimento = atendimento;
        this.texto = texto;
        this.numeroSequencial = numeroSequencial;
        this.roleMensagem = roleMensagem;
    }
}
