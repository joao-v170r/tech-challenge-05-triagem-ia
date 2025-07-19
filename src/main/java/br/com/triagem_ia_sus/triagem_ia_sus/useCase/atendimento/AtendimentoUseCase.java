package br.com.triagem_ia_sus.triagem_ia_sus.useCase.atendimento;

import br.com.triagem_ia_sus.triagem_ia_sus.domain.*;
import br.com.triagem_ia_sus.triagem_ia_sus.dto.atendimento.RealizarAtendimentoIaDTO;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.AtendimentoRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.MensagemRepository;
import br.com.triagem_ia_sus.triagem_ia_sus.repository.PacienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AtendimentoUseCase {

    private final MensagemRepository mensagemRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final PacienteRepository pacienteRepository;

    public String atendimento(RealizarAtendimentoIaDTO dto) {

        Atendimento atendimento = obterAtendimento(dto);

        // Se a triagem está concluída, retorna a última mensagem.
        if (
                atendimento.getStatus() != StatusAtendimento.AGUARDANDO_TRIAGEM_ESPECIALIZADA &&
                        atendimento.getStatus() != StatusAtendimento.TRIAGEM_ESPECIALIZADA_EM_ANDAMENTO
        ) {
            Mensagem ultimaMensagem = atendimento.getMensagens()
                    .stream()
                    .max(Comparator.comparingInt(Mensagem::getNumeroSequencial))
                    .orElse(null);
            return ultimaMensagem.getTexto();
        }

//        System.out.println("ID do Atendimento:");
//        System.out.println(atendimento.getId());

        salvarMensagem(atendimento, dto.mensagem(), RoleMensagem.USER);
        String payload = gerarPayload(atendimento);

//        System.out.println("Payload enviado para a IA:");
//        System.out.println(payload);

        String respostaIa = requisicaoIa(payload);
        salvarMensagem(atendimento, respostaIa, RoleMensagem.MODEL);
        respostaIa = verificarCadastroPaciente(atendimento, respostaIa);

//        System.out.println("Resposta da IA:");
//        System.out.println(respostaIa);

        if (Objects.equals(obterStringCampo(respostaIa, "atendimentoConcluido"), "Sim")) {
            concluirAtendimento(respostaIa, atendimento);
        }

        return respostaIa;
    }

    public Atendimento obterAtendimento(RealizarAtendimentoIaDTO dto) {
        String id = dto.id() != null ? dto.id() : "0";
        return atendimentoRepository.findById(id)
                .orElseGet(() -> {
                    Atendimento atendimento = new Atendimento();
                    atendimento.setStatus(StatusAtendimento.AGUARDANDO_TRIAGEM_ESPECIALIZADA);
                    return atendimentoRepository.save(atendimento);
                });
    }

    public Paciente obterPaciente(String respostaIa) {
        String cpf = obterStringCampo(respostaIa, "cpf");
        String dataNascimento = obterStringCampo(respostaIa, "dataNascimento");
        return pacienteRepository.findByCpf(cpf)
                .orElseGet(() -> {
                    Paciente novo = new Paciente(
                            null,
                            obterStringCampo(respostaIa, "nome"),
                            cpf,
                            LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            obterStringCampo(respostaIa, "endereco"),
                            obterStringCampo(respostaIa, "telefone")
                    );
                    return pacienteRepository.save(novo);
                });
    }

    public void salvarMensagem(Atendimento atendimento, String texto, RoleMensagem roleMensagem) {
        int proximoNumeroMensagem = atendimento.getMensagens() == null ? 1 : atendimento.getMensagens().size() + 1;
        if (proximoNumeroMensagem == 1) {
            Mensagem primeiraMensagem = new Mensagem(atendimento, obterTextoInicial(atendimento), proximoNumeroMensagem, RoleMensagem.USER);
            mensagemRepository.save(primeiraMensagem);
            atendimento.getMensagens().add(primeiraMensagem);
            proximoNumeroMensagem += 1;
            atendimento.setDataHoraInicioTriagem(LocalDateTime.now());
            atendimento.setStatus(StatusAtendimento.TRIAGEM_ESPECIALIZADA_EM_ANDAMENTO);
        }
        Mensagem novaMensagem = new Mensagem(atendimento, texto, proximoNumeroMensagem, roleMensagem);
        mensagemRepository.save(novaMensagem);
        atendimento.getMensagens().add(novaMensagem);
        atendimentoRepository.save(atendimento);
    }

    public String requisicaoIa(String payload) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
        String apiKey = "AIzaSyDlvzTMe2nRUkHME8vEaAcFCXLy49DPkSE";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String responseBody = response.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String text = root.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
        return text.replaceAll("(?s)```json\\s*|```", "").trim();
    }

    public String gerarPayload(Atendimento atendimento) {
        String template = """
            {
                "role": "%s",
                "parts": [{
                    "text": "%s"
                }]
            }""";
        return """
            {
                "contents": [
                    %s
                ]
            }
            """.formatted(
                atendimento.getMensagens().stream()
                        .sorted(java.util.Comparator.comparingInt(Mensagem::getNumeroSequencial))
                        .map(m -> template.formatted(
                                        m.getRoleMensagem().name().toLowerCase(),
                                        m.getTexto().replace("\"", "\\\"")
                                )
                        )
                        .collect(java.util.stream.Collectors.joining(",\n"))
        );
    }

    public String obterTextoInicial(Atendimento atendimento) {
        return """
        Você é um assistente virtual de triagem inicial para uma Clínica do SUS chamado Tina. Sua principal função é acolher o paciente, compreender a natureza de sua necessidade de atendimento e coletar informações essenciais para a montagem de uma ficha técnica prévia.

        Seu objetivo é ser empático, claro e objetivo. Faça perguntas de forma sequencial e paciente para preencher todos os campos necessários. Não prossiga para a próxima pergunta antes de obter uma resposta clara para a anterior.

        Sua abordagem deve ser a seguinte:

        1. Acolhimento Inicial: Inicie a conversa com uma saudação e explique brevemente seu papel.
        2. Identificação do Paciente: Colete dados básicos.
        3. Natureza da Queixa Principal: Entenda o motivo do contato.

        Os campos da ficha técnica que você deve preencher através das perguntas são:

        - Nome Completo do Paciente
        - CPF do paciente
        - Data de Nascimento
        - Telefone para Contato
        - Endereço Completo (Rua, Número, Bairro, Cidade, Estado)
        - Queixa Principal, qual é o sintoma
        - Faça classificação de Urgência inferindo com base nas respostas e nas perguntas que você fará sobre a gravidade dos sintomas): [Campo para a IA preencher com base nas informações coletadas, por exemplo: Não Urgente, Pouco Urgente, Urgente, Muito Urgente].
        
        Inicie a conversa apresentando-se e pergunte por todos os campos da ficha.

        Responda a conversa com um json neste formato: {
          "idAtendimento": %s,
          "nome": "Nome completo do Paciente",
          "cpf": "CPF do Paciente",
          "dataNascimento": "Data de Nascimento do Paciente (formato: DD/MM/YYYY)",
          "telefone": "Telefone do Paciente",
          "endereco": "Endereço completo do Paciente (Rua, Número, Bairro, Cidade, Estado)",
          "sintomasRecente": "Sintomas do Paciente",
          "classificacaoUrgencia": "Classificação de urgência do atendimento, inferir pelo sintoma. (formato: Não Urgente, Pouco Urgente, Urgente, Muito Urgente)",
          "mensagem": "Mensagem de acolhimento e perguntas para coletar as informações necessárias",
          "atendimentoConcluido": "Não"
        }
        
        Quando preencher os campos nome, cpf, dataNascimento, telefone, sintomasRecente e classificacaoUrgencia, altere atendimentoConcluido para Sim, encerre a conversa com uma mensagem de agradecimento e informe que as informações foram coletadas com sucesso. 
        """.formatted(atendimento.getId());
    }

    public String obterStringCampo(String texto, String campo) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(texto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode.path(campo).asText();
    }

    public ClassificacaoRisco converterClassificacaoUrgencia(String classificacaoUrgencia) {
        return switch (classificacaoUrgencia) {
            case "Não Urgente" -> ClassificacaoRisco.AZUL;
            case "Pouco Urgente" -> ClassificacaoRisco.VERDE;
            case "Urgente" -> ClassificacaoRisco.AMARELO;
            case "Muito Urgente" -> ClassificacaoRisco.VERMELHO;
            default -> throw new IllegalArgumentException("Classificação desconhecida: " + classificacaoUrgencia);
        };
    }

    public void concluirAtendimento(String respostaIa, Atendimento atendimento) {
        Paciente paciente = obterPaciente(respostaIa);
        atendimento.setPaciente(paciente);
        atendimento.setSintomasRelatados(obterStringCampo(respostaIa, "sintomasRecente"));
        atendimento.setAgenteIA(AgenteIA.GEMINI);
        atendimento.setStatus(StatusAtendimento.AGUARDANDO_ATENDIMENTO_ESPECIALIZADO);
        atendimento.setClassificacaoRiscoIa(
                converterClassificacaoUrgencia(
                        obterStringCampo(respostaIa, "classificacaoUrgencia")
                )
        );
        atendimentoRepository.save(atendimento);
    }

    public String verificarCadastroPaciente(Atendimento atendimento, String respostaIa) {
        String cpfPaciente = obterStringCampo(respostaIa, "cpf");
        if (!Objects.equals(cpfPaciente, "null") && !cpfPaciente.isEmpty() && (
                Objects.equals(obterStringCampo(respostaIa, "dataNascimento"), "null")
                        || Objects.equals(obterStringCampo(respostaIa, "endereco"), "null")
                        || Objects.equals(obterStringCampo(respostaIa, "telefone"), "null")
        )
        ) {
            Optional<Paciente> optionalPaciente = pacienteRepository.findByCpf(cpfPaciente);
            if (optionalPaciente.isPresent()) {
                Paciente paciente = optionalPaciente.get();
                String dadosPaciente = String.format(
                        "Informe o paciente que ele já está cadastrado no sistema. O paciente já possui esses dados, você não precisa pedir novamente pelos dados já existentes: { \"nome\": \"%s\", \"cpf\": \"%s\", \"dataNascimento\": \"%s\", \"endereco\": \"%s\", \"telefone\": \"%s\" }",
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getDataNascimento() != null ? paciente.getDataNascimento().toString() : "",
                        paciente.getEndereco(),
                        paciente.getTelefone()
                );
                salvarMensagem(atendimento, dadosPaciente, RoleMensagem.USER);
                String payloadDadosPaciente = gerarPayload(atendimento);
                return requisicaoIa(payloadDadosPaciente);
            }
        }
        return respostaIa;
    }

}