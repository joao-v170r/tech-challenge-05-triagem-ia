package br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento;

public record InputUpdateAtendimentoDTO(
        String id,
        String pacienteId,
        String dataHoraInicioAtendimento,
        String sintomasRelatados,
        String status,
        String classificacaoFinal,
        String observacaoTriagemEspecializada,
        String observacaoAtendimentoEspecializado,
        String responsavelTriagemId,
        String responsavelAtendimentoId
) {
}