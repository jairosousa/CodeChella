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

}
