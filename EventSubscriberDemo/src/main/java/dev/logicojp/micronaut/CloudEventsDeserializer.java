package dev.logicojp.micronaut;

import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;
import io.cloudevents.core.format.ContentType;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import jakarta.inject.Singleton;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

@Singleton
public class CloudEventsDeserializer {

    public CloudEventsDeserializer() {
    }

    public CloudEvent deserialize(String eventJson) {
        EventFormat format = EventFormatProvider.getInstance().resolveFormat(ContentType.JSON);
        if(format==null) {
            throw new IllegalArgumentException("CloudEvents JSON format is not available");
        }

        return format.deserialize(eventJson.getBytes(StandardCharsets.UTF_8));
    }

    JsonObject readAsJsonObject(CloudEvent cloudEvent) {
        CloudEventData eventData = cloudEvent.getData();

        if(eventData==null) {
            return Json.createObjectBuilder().build();
        }

        String jsonData = new String(eventData.toBytes(), StandardCharsets.UTF_8);
        try (JsonReader jsonReader=Json.createReader(new StringReader(jsonData))){
            return jsonReader.readObject();
        }
    }
}
