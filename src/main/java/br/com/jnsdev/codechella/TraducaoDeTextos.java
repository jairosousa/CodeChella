package br.com.jnsdev.codechella;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Autor Jairo Nascimento
 * @Created 10/09/2025 - 16:49
 */
public class TraducaoDeTextos {

    private TraducaoDeTextos() {
    }

    public static Mono<String> obterTraducao(String texto, String idioma) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api-free.deepl.com/v2/translate")
                .build();

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();

        req.add("text", texto);
        req.add("target_lang", idioma);

        return webClient.post()
                .header("Authorization", "DeepL-Auth-Key " + System.getenv("DEEPL_APIKEY"))
                .body(BodyInserters.fromFormData(req))
                .retrieve()
                .bodyToMono(Traducao.class)
                .map(Traducao::getTexto);
    }
}
