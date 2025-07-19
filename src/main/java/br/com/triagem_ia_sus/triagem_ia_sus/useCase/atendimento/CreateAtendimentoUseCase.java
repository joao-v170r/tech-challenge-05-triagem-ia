package br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.*;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.InputCreateAtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.AtendimentoRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateAtendimentoUseCase {
    private final AtendimentoRepository atendimentoRepository;
    private final PacienteRepository pacienteRepository;
    private final ColaboradorRepository colaboradorRepository;

    public AtendimentoDTO create(InputCreateAtendimentoDTO dto) {

        Atendimento atendimento = new Atendimento();

        Paciente paciente = pacienteRepository.findById(dto.pacienteId()).orElseThrow(
                () -> new RuntimeException("Paciente não existe", new IllegalArgumentException("Id não encontrado"))
        );
        atendimento.setPaciente(paciente);

        Colaborador responavelTriagem = colaboradorRepository.findById(dto.responsavelTriagemId()).orElseThrow(
                () -> new RuntimeException("Colaborador não existe", new IllegalArgumentException("Id não encontrado"))
        );
        atendimento.setResponsavelTriagem(responavelTriagem);

        atendimento.setSintomasRelatados(dto.sintomasRelatados());
        atendimento.setClassificacaoFinal(ClassificacaoRisco.valueOf(dto.classificacaoFinal()));
        atendimento.setObservacaoTriagemEspecializada(dto.observacaoTriagemEspecializada());
        atendimento.setCanalAtendimento(CanalAtendimento.valueOf(dto.canalAtendimento()));
        atendimento.setDataHoraInicioTriagem(LocalDateTime.now());
        atendimento.setStatus(StatusAtendimento.AGUARDANDO_ATENDIMENTO_ESPECIALIZADO);

        atendimentoRepository.save(atendimento);

        return new AtendimentoDTO(atendimento);
    }
}
