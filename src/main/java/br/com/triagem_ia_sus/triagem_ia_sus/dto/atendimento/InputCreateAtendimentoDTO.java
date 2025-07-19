package br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento;

public record InputCreateAtendimentoDTO (
    String pacienteId,
    String sintomasRelatados,
    String classificacaoFinal,
    String observacaoTriagemEspecializada,
    String responsavelTriagemId,
    String canalAtendimento
) {}