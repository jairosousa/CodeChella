package br.com.jnsdev.codechella;

import java.util.List;

/**
 * @Autor Jairo Nascimento
 * @Created 10/09/2025 - 06:25
 */
public record Traducao(List<Texto> translations) {

    public String getTexto() {
        return translations.getFirst().text();
    }

}
