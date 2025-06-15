package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.gateway;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.mapper.ClienteMapper;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.entity.ClienteEntity;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.repository.ClienteRepository;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.gateways.ClienteGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepositorioDeClienteJPAGatewayImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    public RepositorioDeClienteJPAGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = ClienteMapper.toClienteEntity(cliente);
        clienteRepository.save(entity);
        return ClienteMapper.toClientDomain(entity);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        Optional<ClienteEntity> clienteEntityOptional = clienteRepository.findById(id);
        return clienteEntityOptional.map(ClienteMapper::toClientDomain);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(ClienteMapper::toClientDomain);
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(id);
        ClienteEntity entity = ClienteMapper.toClienteEntity(cliente);
        clienteRepository.save(entity);
        return ClienteMapper.toClientDomain(entity);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository
                .findAll()
                .stream()
                .map(entity -> ClienteMapper.toClientDomain(entity))
                .collect(Collectors.toList());
    }
}

