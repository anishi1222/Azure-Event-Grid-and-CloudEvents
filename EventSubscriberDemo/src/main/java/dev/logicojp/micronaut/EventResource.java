package dev.logicojp.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.cloudevents.CloudEvent;
import jakarta.json.JsonObject;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

import java.util.Optional;

@Controller("/event")
public class EventResource {
    private static final String ALLOW_METHODS = "GET, POST, HEAD";
    private static final String CLOUD_EVENTS_JSON = "application/cloudevents+json";

    @Inject
    private final CloudEventsDeserializer deserializer;

    public EventResource(CloudEventsDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Get("/updates")
    public HttpResponse<?> getEvent() {
        return HttpResponse.ok();
    }

    @Post(value="/updates", consumes=CLOUD_EVENTS_JSON)
    public HttpResponse<?> receiveEvent(@Nullable @Body Optional<String> eventJson) {
        if(eventJson.isEmpty() || eventJson.get().isBlank()) {
            return HttpResponse.badRequest("Empty body");
        }

        CloudEvent cloudEvent = this.deserializer.deserialize(eventJson.get());
        System.out.println("Received JSON String -- " + eventJson.get());
        System.out.println("Converted to CloudEvent -- " + cloudEvent);
        JsonObject customData = this.deserializer.readAsJsonObject(cloudEvent);
        System.out.println("Data in CloudEvent -- " + customData);
        return HttpResponse.accepted();
    }

    @Options("/updates")
    @Produces(CLOUD_EVENTS_JSON)
    public HttpResponse<?> isWebhookEnabled() {
        return HttpResponse.ok()
                .header("Allow", ALLOW_METHODS)
                .header("Webhook-Allowed-Origin","eventgrid.azure.net")
                .header("Webhook-Allowed-Methods", ALLOW_METHODS);
    }
}
