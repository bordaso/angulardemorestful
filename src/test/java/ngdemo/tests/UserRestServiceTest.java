package ngdemo.tests;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class UserRestServiceTest {

    private final ServerProvider serverProvider;
    private final ClientProvider clientProvider;
    private WebResource webService;

    public UserRestServiceTest() {
        serverProvider = new ServerProvider();
        clientProvider = new ClientProvider(serverProvider);
    }

    @Before
    public void startServer() throws IOException {
        serverProvider.createServer();
        webService = clientProvider.getWebResource();
    }

    @After
    public void stopServer() {
        serverProvider.stop();
    }

    @Ignore
    @Test
    public void testGetAllUsersShouldReturnSuccessStatusAndCorrectData() throws IOException {
        ClientResponse resp = webService.path("web").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got stuff: " + resp);
        String actual = resp.getEntity(String.class);

        assertEquals(200, resp.getStatus());
        String expectedUser1 = "{\"firstName\":\"JonFromREST1\",\"lastName\":\"DoeFromREST1\"}";
        String expectedUser2 = "{\"firstName\":\"JonFromREST2\",\"lastName\":\"DoeFromREST2\"}";

        assertTrue(actual.contains(expectedUser1));
        assertTrue(actual.contains(expectedUser2));
    }
}