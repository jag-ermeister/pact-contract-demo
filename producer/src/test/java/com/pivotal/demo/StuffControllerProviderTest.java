package com.pivotal.demo;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRestPactRunner.class)
//@PactFolder("pacts")
@PactBroker(host="localhost", port = "80")
@Provider("provider_service")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"server.port=8081"}
)
public class StuffControllerProviderTest {

    @TestTarget
    public final Target target = new HttpTarget(8081);

    @MockBean
    private StuffRepository stuffRepository;

    @State({"test GET"})
    public void toCreatePersonState() {
        StuffDTO stuff = new StuffDTO(1, "jeremy", "is awesome");
        when(stuffRepository.getStuff()).thenReturn(stuff);
    }
}