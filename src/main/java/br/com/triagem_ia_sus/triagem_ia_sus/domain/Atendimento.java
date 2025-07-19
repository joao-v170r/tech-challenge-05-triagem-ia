package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "mensagens")
@Document(collection = "tb_atendimento_paciente")
public class Atendimento {
    @Id
    private String id;
    @DBRef
    private Paciente paciente;
    private LocalDateTime dataHoraInicioAtendimento;
    private String sintomasRelatados;
    private AgenteIA agenteIA;
    private StatusAtendimento status;
    private ClassificacaoRisco classificacaoRiscoIa;
    private ClassificacaoRisco classificacaoFinal;
    private LocalDateTime dataHoraInicioTriagem;
    private String observacaoTriagemEspecializada;
    private String observacaoAtendimentoEspecializado;
    @DBRef
    private Colaborador responsavelTriagem;
    @DBRef
    private Colaborador responsavelAtendimento;
//    private String observacaoAtendimentoIA;
//    private String resumoHistoricoIA;
    private CanalAtendimento canalAtendimento;
    @DBRef
    private Set<Mensagem> mensagens = new HashSet<>();
}