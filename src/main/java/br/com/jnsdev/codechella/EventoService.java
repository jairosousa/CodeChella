package br.com.jnsdev.codechella;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
        return  repositorio.findAll()
                .map(EventoDto::toDto);
    }
}
