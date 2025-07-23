package br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.service;

import br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.entity.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.dto.ClienteDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteService {

    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return Cliente.findByIdOptional(id);
    }

    @Transactional
    public Cliente criar(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        preencher(cliente, dto);
        cliente.persist();
        return cliente;
    }

    @Transactional
    public Optional<Cliente> atualizar(Long id, ClienteDTO dto) {
        Optional<Cliente> clienteOpt = Cliente.findByIdOptional(id);
        clienteOpt.ifPresent(c -> {
            preencher(c, dto);
        });
        return clienteOpt;
    }

    @Transactional
    public boolean deletar(Long id) {
        return Cliente.deleteById(id);
    }

    private void preencher(Cliente c, ClienteDTO dto) {
        c.nome = dto.nome;
        c.cpf = dto.cpf;
        c.dataNascimento = dto.dataNascimento;
        c.logradouro = dto.logradouro;
        c.numero = dto.numero;
        c.complemento = dto.complemento;
        c.bairro = dto.bairro;
        c.cidade = dto.cidade;
        c.estado = dto.estado;
        c.cep = dto.cep;
    }
}
