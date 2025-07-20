package br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento;

public record InputUpdateAtendimentoDTO(
        String pacienteId,
        String dataHoraInicioAtendimento,
        String sintomasRelatados,
        String status,
        String classificacaoFinal,
        String observacaoTriagemEspecializada,
        String observacaoAtendimentoEspecializado,
        Boolean responsavelTriagem,
        Boolean responsavelAtendimento
) {
}