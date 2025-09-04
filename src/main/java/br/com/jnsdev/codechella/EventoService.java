package br.com.jnsdev.codechella;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Autor Jairo Nascimento
 * @Created 04/09/2025 - 12:18
 */
@Service
public class EventoService {

    private final EventoRepository repositorio;

    public EventoService(EventoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Flux<EventoDto> obterTodos() {
        return repositorio.findAll()
                .map(EventoDto::toDto);
    }

    public Mono<EventoDto> obterPorId(Long id) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDto::toDto);
    }
}
