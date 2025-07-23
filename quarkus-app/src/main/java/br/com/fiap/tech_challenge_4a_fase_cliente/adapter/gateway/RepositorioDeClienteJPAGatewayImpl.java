package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.gateway;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.mapper.ClienteMapper;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.entity.ClienteEntity;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.repository.ClienteRepository;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioDeClienteJPAGatewayImpl implements ClienteGateway {

    @Inject
    ClienteRepository clienteRepository;
    ClienteMapper mapper = new ClienteMapper();

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = mapper.toClienteEntity(cliente);
        clienteRepository.persist(entity);
        return mapper.toClientDomain(entity);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        ClienteEntity entity = clienteRepository.findById(id);
        return Optional.ofNullable(entity).map(mapper::toClientDomain);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findByCpf(cpf);
        return clienteEntity.map( e-> mapper.toClientDomain(e));
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(id);
        ClienteEntity entity = mapper.toClienteEntity(cliente);
        clienteRepository.persist(entity);
        return mapper.toClientDomain(entity);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository
                .listAll()
                .stream()
                .map(entity -> mapper.toClientDomain(entity))
                .toList();
    }
}

