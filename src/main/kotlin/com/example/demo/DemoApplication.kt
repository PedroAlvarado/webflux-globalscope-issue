package com.example.demo

import kotlinx.coroutines.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Configuration
class RouterConfig {
	@Bean
	fun router(handler: Handler) = coRouter {
		POST("/foo", handler::fooSomething)
	}
}

@Component
class Handler {
	suspend fun fooSomething(request: ServerRequest) : ServerResponse {
		GlobalScope.processRequest(request)
		println(request.awaitBodyOrNull<String>())
		return ServerResponse.ok().buildAndAwait()
	}

	private fun CoroutineScope.processRequest(request: ServerRequest) {
		launch(Dispatchers.IO) {
			delay(3000)
			try {
				val awaitBodyOrNull: String? = request.awaitBodyOrNull<String>()
				println(awaitBodyOrNull)
			}
			catch(e : Exception){
				println(e)
			}
		}
	}
}
