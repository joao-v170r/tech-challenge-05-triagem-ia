package br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.*;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.InputUpdateAtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.AtendimentoRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UpdateAtendimentoUseCase {
    private final AtendimentoRepository atendimentoRepository;
    private final PacienteRepository pacienteRepository;
    private final ColaboradorRepository colaboradorRepository;

    public AtendimentoDTO update(String id, InputUpdateAtendimentoDTO dto) {

        Atendimento atendimento = atendimentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Atendimento não existe", new IllegalArgumentException("Id não encontrado"))
        );

        if (dto.pacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(dto.pacienteId()).orElseThrow(
                    () -> new RuntimeException("Paciente não existe", new IllegalArgumentException("Id não encontrado"))
            );
            atendimento.setPaciente(paciente);
        }

        if (dto.dataHoraInicioAtendimento() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dataHora = LocalDateTime.parse(dto.dataHoraInicioAtendimento(), formatter);
            atendimento.setDataHoraInicioAtendimento(dataHora);
        }

        if (dto.sintomasRelatados() != null) {
            atendimento.setSintomasRelatados(dto.sintomasRelatados());
        }

        if (dto.status() != null) {
            atendimento.setStatus(StatusAtendimento.valueOf(dto.status()));
        }

        if (dto.classificacaoFinal() != null) {
            atendimento.setClassificacaoFinal(ClassificacaoRisco.valueOf(dto.classificacaoFinal()));
        }

        if (dto.observacaoTriagemEspecializada() != null) {
            atendimento.setObservacaoTriagemEspecializada(dto.observacaoTriagemEspecializada());
        }

        if (dto.observacaoAtendimentoEspecializado() != null) {
            atendimento.setObservacaoAtendimentoEspecializado(dto.observacaoAtendimentoEspecializado());
        }

        if (dto.responsavelTriagemId() != null) {
            Colaborador responsavelTriagem = colaboradorRepository.findById(dto.responsavelTriagemId()).orElseThrow(
                    () -> new RuntimeException("Responsável triagem não existe", new IllegalArgumentException("Id não encontrado"))
            );
            atendimento.setResponsavelTriagem(responsavelTriagem);
        }

        if (dto.responsavelAtendimentoId() != null) {
            Colaborador responsavelAtendimento = colaboradorRepository.findById(dto.responsavelAtendimentoId()).orElseThrow(
                    () -> new RuntimeException("Responsável atendimento não existe", new IllegalArgumentException("Id não encontrado"))
            );
            atendimento.setResponsavelAtendimento(responsavelAtendimento);
        }

        return new AtendimentoDTO(atendimentoRepository.save(atendimento));
    }
}
