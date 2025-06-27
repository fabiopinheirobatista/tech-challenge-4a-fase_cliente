package br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente;


import br.com.fiap.tech_challenge_4a_fase_cliente.TechChallenge4aFaseClienteApplication;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.exception.ClienteNaoEncontradoException;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.gateways.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TechChallenge4aFaseClienteApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class DeletarClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private DeletarClienteUseCase deletarClienteUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteGateway = mock(ClienteGateway.class);
        deletarClienteUseCase = new DeletarClienteUseCase(clienteGateway);
    }

    @Test
    void deveDeletarClienteQuandoExistente() {
        // Arrange
        Long id = 1L;
        Cliente cliente = new Cliente(id, "Nome", "123.456.789-10", LocalDate.of(1990, 1, 1), null);

        doReturn(Optional.of(cliente)).when(clienteGateway).buscarPorId(id);
        doNothing().when(clienteGateway).deletar(id);

        // Act & Assert
        assertDoesNotThrow(() -> deletarClienteUseCase.executar(id));

        // Verify
        verify(clienteGateway, times(1)).buscarPorId(id);
        verify(clienteGateway, times(1)).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExistente() {
        // Arrange
        Long id = 2L;
        doReturn(Optional.empty()).when(clienteGateway).buscarPorId(id);

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> deletarClienteUseCase.executar(id));

        // Verify
        verify(clienteGateway, times(1)).buscarPorId(id);
        verify(clienteGateway, never()).deletar(anyLong());
    }
}