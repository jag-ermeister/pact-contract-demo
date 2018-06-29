package com.pivotal.demo;


import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class PactTest {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("provider_service", "localhost", 8090, this);

    //this defines the contract
    @Pact(consumer = "consumer_service", provider="provider_service")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/api/stuff")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(ImmutableMap.of("Content-Type", "application/json"))
                .body("{\"id\": 1, \"name\": \"jeremy\", \"notes\": \"is awesome\"}")
                .toPact();
    }

    //this is a consumer test
    //I should create a service/client that makes a call to a remote system and returns a DTO - assert on that
    @Test
    @PactVerification //this annotation starts the mock http service
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockProvider.getUrl() + "/api/stuff", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("id", "1", "name", "jeremy", "notes", "is awesome");
    }
}
