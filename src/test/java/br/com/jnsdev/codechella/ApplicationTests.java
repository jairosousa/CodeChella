package br.com.jnsdev.codechella;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void cadastraNovoEvento() {
        EventoDto dto = new EventoDto(null, TipoEvento.SHOW, "Kiss",
                LocalDate.parse("2025-01-01"), "Show da melhor banda que existe");

        webTestClient.post().uri("/eventos").bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EventoDto.class)
                .value(response -> {
                    assertNotNull(response.id());
                    assertEquals(dto.tipo(), response.tipo());
                    assertEquals(dto.nome(), response.nome());
                    assertEquals(dto.data(), response.data());
                    assertEquals(dto.descricao(), response.descricao());
                });
    }

    // código omitido

    @Test
    void buscarEvento() {
        EventoDto dto = new EventoDto(13L, TipoEvento.SHOW, "The Weeknd",
                LocalDate.parse("2025-11-02"), "Um show eletrizante ao ar livre com muitos efeitos especiais.");

        webTestClient.get().uri("/eventos")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(EventoDto.class)
                .value(response -> {
                    EventoDto eventoResponse = response.get(12);
                    assertEquals(dto.id(), eventoResponse.id());
                    assertEquals(dto.tipo(), eventoResponse.tipo());
                    assertEquals(dto.nome(), eventoResponse.nome());
                    assertEquals(dto.data(), eventoResponse.data());
                    assertEquals(dto.descricao(), eventoResponse.descricao());
                });
    }

    @Test
    void alteraEvento() {
        EventoDto dtoAtualizado = new EventoDto(5L, TipoEvento.CONCERTO, "Metallica",
                LocalDate.parse("2025-12-01"), "Concerto da banda Metallica");

        webTestClient.put().uri("/eventos/{id}", 5L).bodyValue(dtoAtualizado)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventoDto.class)
                .value(response -> {
                    assertEquals(dtoAtualizado.id(), response.id());
                    assertEquals(dtoAtualizado.tipo(), response.tipo());
                    assertEquals(dtoAtualizado.nome(), response.nome());
                    assertEquals(dtoAtualizado.data(), response.data());
                    assertEquals(dtoAtualizado.descricao(), response.descricao());
                });
    }

    @Test
    void excluiEvento() {
        webTestClient.delete().uri("/eventos/{id}", 10L)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get().uri("/eventos/{id}", 10L)
                .exchange()
                .expectStatus().isNotFound();
    }

}
