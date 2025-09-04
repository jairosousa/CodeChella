package br.com.jnsdev.codechella;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @Autor Jairo Nascimento
 * @Created 02/09/2025 - 14:40
 */
public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
}
