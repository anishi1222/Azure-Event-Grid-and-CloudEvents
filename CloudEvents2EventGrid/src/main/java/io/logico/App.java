package io.logico;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String AEG_KEY = "Access Key for Azure Event Grid or Shared Access Signature";
    private static final String AEG_ENDPOINT = "https://{Azure Event Grid Instance}.eventgrid.azure.net";
    public static void main( String... args ) {
        postEvent();
    }

    private static void postEvent() {
        JsonObject jsonObject = Json.createObjectBuilder().add("message", "Using CloudEvents.io API to send CloudEvents!!").build();

        CloudEvent ce = CloudEventBuilder.v1()
                .withId("A234-1234-1234")
                .withType("io.logico-jp.ExampleEventType")
                .withSource(URI.create("io/logico-jp/source"))
                .withTime(OffsetDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC)))
                .withDataContentType(MediaType.APPLICATION_JSON)
                .withData(jsonObject.toString().getBytes(StandardCharsets.UTF_8))
                .build();

        EventFormat format =EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE);


        byte[] serialized = format.serialize(ce);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("aeg-sas-key", AEG_KEY);
        Response response = ClientBuilder.newClient().target(AEG_ENDPOINT)
                .path("/api/events")
                .queryParam("api-version", "2018-01-01")
                .request("application/cloudevents+json")
                .headers(headers)
                .post(Entity.entity(serialized, "application/cloudevents+json"));

        System.out.println("<Events>      " + ce.toString());
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            System.out.println("<HTTP Status> " + response.getStatusInfo().getStatusCode());
            System.out.println("<Message>     " + response.getStatusInfo().getReasonPhrase());
        }
    }
}
