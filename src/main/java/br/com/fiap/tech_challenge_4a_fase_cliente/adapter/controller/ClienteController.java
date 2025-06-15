package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.request.ClienteRequestDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.response.ClienteResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ClienteController {

    ClienteResponseDto cadastrarCliente(ClienteRequestDto dto);

    List<ClienteResponseDto> listarClientes();

    ClienteResponseDto buscarClientePorId(Long id);

    ResponseEntity<Void> deletarCliente(Long id);

    ResponseEntity<?> atualizarCliente(Long id, ClienteRequestDto dto);

    ResponseEntity<?> atualizarClienteParcial(Long id, Map<String, Object> campos, HttpServletRequest request);


}
