package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ControllerExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        Problem problem = Problem.builder()
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .title("Erro de sistema")
                .detail(exception.getMessage())
                .build();
        return Response.status(problem.getStatus()).entity(problem).build();
    }
}
