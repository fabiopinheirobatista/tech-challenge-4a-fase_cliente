package br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente;

import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.endereco.Endereco;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.gateways.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private CriarClienteUseCase criarClienteUseCase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        criarClienteUseCase = new CriarClienteUseCase(clienteGateway);
    }

    @Test
    void deveCriarClienteComSucesso() {
        Endereco endereco = new Endereco("Rua Teste", "123", "Apto 1", "Bairro", "Cidade", "SP", "01.234-567");
        Cliente cliente = new Cliente(null, "Maria", "123.456.789-10", LocalDate.of(1995, 5, 20), endereco);
        Cliente clienteSalvo = new Cliente(1L, "Maria", "123.456.789-10", LocalDate.of(1995, 5, 20), endereco);

        when(clienteGateway.salvar(cliente)).thenReturn(clienteSalvo);

        Cliente resultado = criarClienteUseCase.executar(cliente);

        assertNotNull(resultado);
        assertEquals(clienteSalvo.getId(), resultado.getId());
        assertEquals(clienteSalvo.getNome(), resultado.getNome());
        assertEquals(clienteSalvo.getCpf(), resultado.getCpf());
        assertEquals(clienteSalvo.getDataNascimento(), resultado.getDataNascimento());
        assertEquals(clienteSalvo.getEndereco(), resultado.getEndereco());
        verify(clienteGateway).salvar(cliente);
    }
}