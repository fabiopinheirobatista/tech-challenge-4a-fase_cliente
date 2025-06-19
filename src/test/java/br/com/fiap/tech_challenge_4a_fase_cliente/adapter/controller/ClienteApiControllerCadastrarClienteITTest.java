package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller;

import br.com.fiap.tech_challenge_4a_fase_cliente.TechChallenge4aFaseClienteApplication;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.request.ClienteRequestDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.CriarClienteUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TechChallenge4aFaseClienteApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ClienteApiControllerCadastrarClienteITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CriarClienteUseCase criarUsuarioUseCase;

    ClienteRequestDto.EnderecoRequestDto endereco;
    ClienteRequestDto clienteRequestDto;

    @Test
    void cadastrarClienteSucesso() throws Exception {
        endereco = new ClienteRequestDto.EnderecoRequestDto(
                "Avenida Paulista",
                "1000",
                "Sala 123",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01.310-100"
        );
        clienteRequestDto = new ClienteRequestDto(
                "João da Silva",
                "123.456.789-10",
                LocalDate.of(1990, 1, 1),
                endereco
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-10"));
    }

    @Test
    void cadastrarClienteComDadosInvalidos() throws Exception {
        // Arrange
        endereco = new ClienteRequestDto.EnderecoRequestDto(
                "",  // logradouro vazio - violação de constraint
                "",  // número vazio - violação de constraint
                "Sala 123",
                "",  // bairro vazio - violação de constraint
                "",  // cidade vazia - violação de constraint
                "",  // estado vazio - violação de constraint
                "01310-100"
        );

        clienteRequestDto = new ClienteRequestDto(
                "",  // nome vazio - violação de constraint
                "123.456.789-10",
                LocalDate.of(1990, 1, 1),
                endereco
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Act & Assert
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
//                .andExpect(jsonPath("$.message").exists())
//                .andExpect(jsonPath("$.errors").isArray())
//                .andExpect(jsonPath("$.errors[*].field").exists())
//                .andExpect(jsonPath("$.errors[*].message").exists())
        ;
    }

    @Test
    void cadastrarClienteCPFVazio() throws Exception {
        endereco = new ClienteRequestDto.EnderecoRequestDto(
                "Avenida Paulista",
                "1000",
                "Sala 123",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01.310-100"
        );
        clienteRequestDto = new ClienteRequestDto(
                "João da Silva",
                "",
                LocalDate.of(1990, 1, 1),
                endereco
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void cadastrarClienteCPFRetetido() throws Exception {
        endereco = new ClienteRequestDto.EnderecoRequestDto(
                "Avenida Paulista",
                "1000",
                "Sala 123",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01.310-100"
        );
        clienteRequestDto = new ClienteRequestDto(
                "João da Silva",
                "123.456.789-10",
                LocalDate.of(1990, 1, 1),
                endereco
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void cadastrarClienteCPFErrro() throws Exception {
        endereco = new ClienteRequestDto.EnderecoRequestDto(
                "Avenida Paulista",
                "1000",
                "Sala 123",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01.310-100"
        );
        clienteRequestDto = new ClienteRequestDto(
                "João da Silva",
                "123.456.789-1",
                LocalDate.of(1990, 1, 1),
                endereco
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void cadastrarClienteCEPErrro() throws Exception {
        endereco = new ClienteRequestDto.EnderecoRequestDto(
                "Avenida Paulista",
                "1000",
                "Sala 123",
                "Bela Vista",
                "São Paulo",
                "SP",
                "01.310-10"
        );
        clienteRequestDto = new ClienteRequestDto(
                "João da Silva",
                "123.456.789-10",
                LocalDate.of(1990, 1, 1),
                endereco
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequestDto)))
                .andExpect(status().isConflict());
    }
}