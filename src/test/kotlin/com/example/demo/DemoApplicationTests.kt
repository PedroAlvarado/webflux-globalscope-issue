package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient

@WebFluxTest
@Import(RouterConfig::class, Handler::class)
@TestConstructor(autowireMode = AutowireMode.ALL)
class DemoApplicationTests(private val webClient: WebTestClient) {

	@Test
	fun contextLoads() {
		webClient
				.post()
				.uri("/foo")
				.bodyValue("someFoo")
				.exchange()
				.expectBody()
				.returnResult()

		Thread.sleep(5000L)
	}

}
