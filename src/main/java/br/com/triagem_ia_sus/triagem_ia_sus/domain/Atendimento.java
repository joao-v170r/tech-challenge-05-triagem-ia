package br.com.triagem_ia_sus.triagem_ia_sus.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "tb_atendimento_paciente")
public class Atendimento {
    @Id
    private String id;
    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Paciente paciente;
    private LocalDateTime datahoraInicioAtendimento;
    private String sintomasRelatados;
    private AgenteIA agenteIA;
    private StatusAtendimento status;
    private ClassificacaoRisco classificacaoPaciente;
    private ClassificacaoRisco classificacaoFinal;
    private LocalDateTime datahoraTriagem;
    private String observacaoTriagemEspecializada;
    private String observacaoAtendimentoEspecializado;
    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Colaborador responsavelTriagem;
    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Colaborador responsavelAtendimento;
    private String observacaoAtendimentoIA;
    private String resumoHistoricoIA;
    private CanalAtendimento canalAtendimento;
}
