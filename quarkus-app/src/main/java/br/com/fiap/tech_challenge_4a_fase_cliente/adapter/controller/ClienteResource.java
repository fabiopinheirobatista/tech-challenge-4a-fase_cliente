package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.request.ClienteRequestDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.response.ClienteResponseDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.mapper.ClienteMapper;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/clientes")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource implements ClienteController {

    @Inject
    CriarClienteUseCase criarUsuarioUseCase;
    @Inject
    AtualizarClienteUseCase atualizarClienteUseCase;
    @Inject
    DeletarClienteUseCase deletarClienteUseCase;
    @Inject
    BuscarClienteUseCase buscarClientePorIdUseCase;
    @Inject
    ListarTodosClienteUseCase listarUsuariosUseCase;

    ClienteMapper mapper = new ClienteMapper();

    @POST
    @Transactional
    @Override
    public ClienteResponseDto cadastrarCliente(ClienteRequestDto dto) {
        Cliente usuario = mapper.toClienteDomain(dto);
        Cliente salvo = criarUsuarioUseCase.executar(usuario);
        return mapper.toClienteResponseDto(salvo);
    }

    @GET
    @Override
    public List<ClienteResponseDto> listarClientes() {
        return listarUsuariosUseCase.executar().stream()
                .map(mapper::toClienteResponseDto)
                .toList();
    }

    @GET
    @Path("/{id}")
    @Override
    public ClienteResponseDto buscarClientePorId(@PathParam("id") Long id) {
        Cliente cliente = buscarClientePorIdUseCase.executar(id);
        return mapper.toClienteResponseDto(cliente);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Override
    public void deletarCliente(@PathParam("id") Long id) {
        deletarClienteUseCase.executar(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Override
    public ClienteResponseDto atualizarCliente(@PathParam("id") Long id, ClienteRequestDto dto) {
        Cliente clienteDomain = mapper.toClienteDomain(dto);
        Cliente executar = atualizarClienteUseCase.executar(id, clienteDomain);
        return mapper.toClienteResponseDto(executar);
    }
}
