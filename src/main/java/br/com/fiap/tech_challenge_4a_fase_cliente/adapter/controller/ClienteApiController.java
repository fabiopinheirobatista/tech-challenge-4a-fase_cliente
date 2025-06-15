package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.request.ClienteRequestDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.response.ClienteResponseDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.exception.ClienteNaoEncontradoException;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.mapper.ClienteMapper;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.AtualizarClienteUseCase;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.BuscarClienteUseCase;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.CriarClienteUseCase;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.DeletarClienteUseCase;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.usecases.cliente.ListarTodosClienteUseCase;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;

@RestController
@RequestMapping("/clientes")
public class ClienteApiController implements ClienteController{

    private final CriarClienteUseCase criarUsuarioUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final DeletarClienteUseCase deletarClienteUseCase;
    private final BuscarClienteUseCase buscarClientePorIdUseCase;
    private final ListarTodosClienteUseCase listarUsuariosUseCase;
    private final ClienteMapper clienteMapper;

    public ClienteApiController(
            CriarClienteUseCase criarUsuarioUseCase,
            AtualizarClienteUseCase atualizarClienteUseCase,
            DeletarClienteUseCase deletarClienteUseCase,
            BuscarClienteUseCase buscarClientePorIdUseCase,
            ListarTodosClienteUseCase listarUsuariosUseCase,
            ClienteMapper clienteMapper) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.deletarClienteUseCase = deletarClienteUseCase;
        this.buscarClientePorIdUseCase = buscarClientePorIdUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.clienteMapper = clienteMapper;
    }

    @Override
    @PostMapping
    public ClienteResponseDto cadastrarCliente(@RequestBody ClienteRequestDto dto) {
        Cliente usuario = clienteMapper.toClienteDomain(dto);
        Cliente salvo = criarUsuarioUseCase.executar(usuario);
        return clienteMapper.toClienteResponseDto(salvo);
    }

    @Override
    @GetMapping
    public List<ClienteResponseDto> listarClientes() {
        return listarUsuariosUseCase.executar().stream()
                .map(ClienteMapper::toClienteResponseDto)
                .toList();
    }

    @Override
    @GetMapping("/{id}")
    public ClienteResponseDto buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> clienteOptional =  buscarClientePorIdUseCase.executar(id);
        return clienteOptional
            .map(ClienteMapper::toClienteResponseDto)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
                
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        buscarClientePorIdUseCase
            .executar(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        deletarClienteUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Esse metodo atualiza o objeto cliente inteiro
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequestDto dto) {

//        buscarClientePorIdUseCase.executar(id)
//                .map(ClienteMapper::toClienteResponseDto)
//                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        Cliente clienteDomain = ClienteMapper.toClienteDomain(dto);
        Cliente executar = atualizarClienteUseCase.executar(id,clienteDomain);
        return ResponseEntity.ok(clienteMapper.toClienteResponseDto(executar));
        
    }

    /**
     * Esse metodo atualiza parcialmente o objeto cliente
     *  Estou tentando utilizar o BeanUtils.copyProperties para copiar os atributos de dto para clienteAtual menos o id, mas nao ta funcioanndo, porque? corrija. Analise essas classes
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarClienteParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Optional<Cliente> clienteAtual =  buscarClientePorIdUseCase.executar(id);
        ClienteResponseDto clienteResponseDto = clienteAtual
                .map(ClienteMapper::toClienteResponseDto)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        ClienteDto clienteDto = ClienteMapper.toClienteDto(clienteAtual.get());


        merge(campos, clienteDto, request);
        //ClienteRequestDto clienteRequestDtoDto = modelMapper.map(clienteDTO, ClienteRequestDto.class);
        return atualizarCliente(id,ClienteMapper.toClienteRequestDto(clienteDto));
    }

    private void merge(Map<String, Object> dadosOrigem, Object destino, HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            for (Map.Entry<String, Object> entry : dadosOrigem.entrySet()) {
                String nomePropriedade = entry.getKey();
                Object valorPropriedade = entry.getValue();

                Field field = ReflectionUtils.findField(destino.getClass(), nomePropriedade);
                if (field != null) {
                    field.setAccessible(true);

                    if (valorPropriedade instanceof Map) {
                        Object valorAtual = ReflectionUtils.getField(field, destino);
                        if (valorAtual == null) {
                            // Se o objeto aninhado estiver nulo, cria uma nova instância
                            valorAtual = field.getType().getDeclaredConstructor().newInstance();
                            ReflectionUtils.setField(field, destino, valorAtual);
                        }
                        // Recursivamente atualiza os campos aninhados
                        merge((Map<String, Object>) valorPropriedade, valorAtual, request);
                    } else {
                        Object novoValor = objectMapper.convertValue(valorPropriedade, field.getType());
                        ReflectionUtils.setField(field, destino, novoValor);
                    }
                }
            }
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }
}
