package br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.resource;

import br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.dto.ClienteDTO;
import br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.entity.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @POST
    public Response criar(ClienteDTO dto) {
        Cliente cliente = service.criar(dto);
        return Response.created(URI.create("/clientes/" + cliente.id)).entity(cliente).build();
    }

    @GET
    public List<Cliente> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, ClienteDTO dto) {
        return service.atualizar(id, dto)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        boolean removed = service.deletar(id);
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
