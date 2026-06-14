package dev.logicojp.micronaut;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.format.ContentType;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.core.v1.CloudEventBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

@Singleton
public class CloudEventsProducer {

    public CloudEvent createCloudEvent() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("message", OffsetDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).toString())
                .build();

        return new CloudEventBuilder()
                .withId("1234-5678-9012")
                .withType("dev.logico-jp.ExampleEventType")
                .withSource(URI.create("dev/logico-jp/source"))
                .withDataContentType(ContentType.JSON.value())
                .withData(jsonObject.toString().getBytes(StandardCharsets.UTF_8))
                .build();
    }

    public byte[] serializeCloudEvent(CloudEvent cloudEvent) {
        EventFormat format = EventFormatProvider.getInstance().resolveFormat(ContentType.JSON);

        if(format==null) {
            throw new IllegalStateException("CloudEvents JSON format is not available");
}       else {
            return format.serialize(cloudEvent);
        }
    }

}
