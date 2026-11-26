package com.dest1.steam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dest1.steam.repositories.JogoRepository;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:catalogo_test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.h2.console.enabled=false"
})
class JogoControllerIntegrationTests {

    @LocalServerPort
    private int porta;

    @Autowired
    private JogoRepository repository;

    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    @Test
    void fluxoDeCadastroEListagem() throws Exception {
        HttpResponse<String> vazia = requisitar("GET", "/api/jogos", null);
        assertEquals(200, vazia.statusCode());
        assertEquals("[]", vazia.body());

        String dados = """
                {"nome":"Aventura","genero":"Obby","criador":"Estúdio Pixel","ano":2024,
                "modo":"Individual","classificacao":"Livre","descricao":"Explore ilhas",
                "capa":"","disponivel":true}
                """;
        HttpResponse<String> cadastro = requisitar("POST", "/api/jogos", dados);
        assertEquals(200, cadastro.statusCode());
        assertTrue(cadastro.body().contains("\"nome\":\"Aventura\""));
        assertTrue(requisitar("GET", "/api/jogos", null).body().contains("Aventura"));
    }

    private HttpResponse<String> requisitar(String metodo, String caminho, String corpo)
            throws Exception {
        HttpRequest.Builder requisicao = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + porta + caminho))
                .header("Accept", "application/json");

        if (corpo == null) {
            requisicao.method(metodo, HttpRequest.BodyPublishers.noBody());
        } else {
            requisicao.header("Content-Type", "application/json; charset=UTF-8")
                    .method(metodo, HttpRequest.BodyPublishers.ofString(corpo, StandardCharsets.UTF_8));
        }

        return client.send(requisicao.build(), HttpResponse.BodyHandlers.ofString());
    }
}
