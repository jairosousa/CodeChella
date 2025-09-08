package br.com.jnsdev.codechella;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

/**
 * @Autor Jairo Nascimento
 * @Created 08/09/2025 - 16:04
 */
@RestController
@RequestMapping("/ingressos")
public class IngressoController {
    private final IngressoService service;
    private final Sinks.Many<IngressoDto> ingressoSink;

    public IngressoController(IngressoService service) {
        this.service = service;
        this.ingressoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping
    public Flux<IngressoDto> obterTodos() {
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    public Mono<IngressoDto> obterPorId(@PathVariable Long id) {
        return service.obterPorId(id);
    }

    @PostMapping
    public Mono<IngressoDto> cadastrar(@RequestBody IngressoDto dto) {
        return service.cadastrar(dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return service.excluir(id);

    }

    @PutMapping("/{id}")
    public Mono<IngressoDto> alterar(@PathVariable Long id, @RequestBody IngressoDto dto) {
        return service.alterar(id, dto);
    }

    @PostMapping("/compra")
    public Mono<IngressoDto> comprar(@RequestBody CompraDto dto) {
        return service.comprar(dto)
                .doOnSuccess(ingressoSink::tryEmitNext);
    }

    @GetMapping(value = "/{id}/disponivel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoDto> totalDisponivel(@PathVariable Long id) {
        return Flux.merge(service.obterPorId(id), ingressoSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }
}
