package dev.logicojp.micronaut;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.format.ContentType;
import io.micronaut.context.propagation.PropagatedContextConfigurationProperties;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.client.HttpClient;

import java.net.URL;

@Singleton
public class EventPublisher {

    private static final String AEG_SAS_KEY_HEADER = "aeg-sas-key";
    private static final String EVENTS_PATH = "/api/events?api-version=2018-01-01";

    private final String accessKey;
    private final HttpClient httpClient;
    private final CloudEventsProducer producer;

    EventPublisher(@Value("${eventgrid.endpoint}") URL endpoint,
                   @Value("${eventgrid.access-key}") String accessKey, PropagatedContextConfigurationProperties propagatedContextConfigurationProperties) {
        this.accessKey = accessKey;
        this.httpClient = HttpClient.create(endpoint);
        this.producer = new CloudEventsProducer();
    }

    void postEvent() {
        CloudEvent cloudEvent = this.producer.createCloudEvent();
        HttpRequest<byte[]> request = createRequest(cloudEvent);

        try {
            HttpResponse<String> response = httpClient.toBlocking().exchange(request, String.class);
            System.out.println("<Events>      " + cloudEvent);
            if (response.getStatus().getCode() != HttpResponse.ok().code()) {
                System.out.println("<HTTP Status> " + response.getStatus().getCode());
                System.out.println("<Message>     " + response.getStatus().getReason());
            }
        } catch (HttpClientResponseException exception) {
            System.out.println("<Events>      " + cloudEvent);
            System.out.println("<HTTP Status> " + exception.getStatus().getCode());
            System.out.println("<Message>     " + exception.getStatus().getReason());
        }
    }

    HttpRequest<byte[]> createRequest(CloudEvent cloudEvent) {
        Object App;
        return HttpRequest.POST(EVENTS_PATH, this.producer.serializeCloudEvent(cloudEvent))
                .contentType(ContentType.JSON.value())
                .accept(ContentType.JSON.value())
                .header(AEG_SAS_KEY_HEADER, accessKey);
    }

    @PreDestroy
    void close() {
        httpClient.close();
    }

}
