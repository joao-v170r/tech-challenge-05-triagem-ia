package br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.*;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.AtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.InputUpdateAtendimentoDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.AtendimentoRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.ColaboradorRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class UpdateAtendimentoUseCase {
    @Autowired
    private TokenService tokenService;
    private final AtendimentoRepository atendimentoRepository;
    private final PacienteRepository pacienteRepository;
    private final ColaboradorRepository colaboradorRepository;

    public AtendimentoDTO update(String id,
                                 InputUpdateAtendimentoDTO dto,
                                 HttpServletRequest httpRequest) {

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

        if (dto.responsavelTriagem() == Boolean.TRUE) {
            atendimento.setResponsavelTriagem(obterColaborador(httpRequest));
        }

        if (dto.responsavelAtendimento() == Boolean.TRUE) {
            atendimento.setResponsavelAtendimento(obterColaborador(httpRequest));
        }

        return new AtendimentoDTO(atendimentoRepository.save(atendimento));
    }

    public Colaborador obterColaborador(HttpServletRequest httpRequest) {
        try {
            String authorizationHeader = httpRequest.getHeader("Authorization");
            String token = authorizationHeader.substring(7);
            String login = tokenService.validateToken(token);
            return colaboradorRepository.findByEmail(login);
        } catch (Exception e) {
            throw new RuntimeException("Colaborador não existe", new IllegalArgumentException("Erro ao recuperar colaborador"));
        }
    }

}
