package br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente;

import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.gateways.ClienteGateway;
import lombok.RequiredArgsConstructor;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ListarTodosClienteUseCase {

    private final ClienteGateway clienteGateway;

    public List<Cliente> executar() {
        return clienteGateway.listarTodos();
    }
}
