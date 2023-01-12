package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.webflux.dsl.WebFlux

@Configuration
class MyFlow {
    @Bean
    fun myIntegrationFlow(jobHandler: JobHandler): IntegrationFlow = IntegrationFlows
            .from(WebFlux.inboundGateway("/test")
                    .requestMapping { m ->
                        m.consumes(MediaType.APPLICATION_JSON_VALUE)
                        m.produces(MediaType.APPLICATION_JSON_VALUE)
                    }
                    .requestPayloadType(JobParams::class.java)
            )
            .handle(jobHandler)
            .get()
}