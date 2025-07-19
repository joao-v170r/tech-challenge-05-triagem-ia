package br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.*;
import java.time.LocalDateTime;

public record AtendimentoDTO(
        String id,
        Paciente paciente,
        LocalDateTime dataHoraInicioAtendimento,
        String sintomasRelatados,
        AgenteIA agenteIA,
        StatusAtendimento status,
        ClassificacaoRisco classificacaoRiscoIa,
        ClassificacaoRisco classificacaoFinal,
        LocalDateTime dataHoraInicioTriagem,
        String observacaoTriagemEspecializada,
        String observacaoAtendimentoEspecializado,
        Colaborador responsavelTriagem,
        Colaborador responsavelAtendimento,
        CanalAtendimento canalAtendimento
) {
    public AtendimentoDTO(Atendimento atendimento) {
        this(
                atendimento.getId(),
                atendimento.getPaciente(),
                atendimento.getDataHoraInicioAtendimento(),
                atendimento.getSintomasRelatados(),
                atendimento.getAgenteIA(),
                atendimento.getStatus(),
                atendimento.getClassificacaoRiscoIa(),
                atendimento.getClassificacaoFinal(),
                atendimento.getDataHoraInicioTriagem(),
                atendimento.getObservacaoTriagemEspecializada(),
                atendimento.getObservacaoAtendimentoEspecializado(),
                atendimento.getResponsavelTriagem(),
                atendimento.getResponsavelAtendimento(),
                atendimento.getCanalAtendimento()
        );
    }
}