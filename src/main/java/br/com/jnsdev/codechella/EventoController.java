package br.com.jnsdev.codechella;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Autor Jairo Nascimento
 * @Created 02/09/2025 - 14:41
 */
@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterTodos() {
        return service.obterTodos();
    }
}
