package com.dest1.steam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:steam_contexto")
class SteamApplicationTests {

	@Test
	void contextLoads() {
	}

}
