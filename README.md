# Azure Event Grid and CloudEvents

Micronaut 5 / Java 25 samples for publishing and receiving Azure Event Grid events using the CloudEvents SDK instead of an Azure Event Grid client library.

## Modules

- `EventPublisherDemo`: Picocli-based Micronaut command application that creates a CloudEvent, serializes it as CloudEvents JSON, and posts it to an Azure Event Grid topic endpoint.
- `EventSubscriberDemo`: Micronaut Netty HTTP application that exposes a webhook endpoint for Azure Event Grid and deserializes incoming CloudEvents JSON payloads.

## Requirements

- Java 25
- Maven 3.9 or the included Maven wrapper in each module
- An Azure Event Grid topic or domain endpoint when running the publisher against Azure

## Key Dependencies

- Micronaut Platform `5.0.2`
- CloudEvents SDK `4.1.1`
- Jakarta JSON Processing API `2.1.3`
- Eclipse Parsson `1.1.9`

## Build And Test

Run tests for both modules:

```bash
cd EventPublisherDemo
./mvnw test

cd ../EventSubscriberDemo
./mvnw test
```

Build both modules:

```bash
cd EventPublisherDemo
./mvnw package

cd ../EventSubscriberDemo
./mvnw package
```

## Event Publisher

The publisher posts to Azure Event Grid with these Micronaut configuration keys:

- `eventgrid.endpoint`: Event Grid topic endpoint, for example `https://<topic-name>.<region>-1.eventgrid.azure.net`
- `eventgrid.access-key`: Event Grid access key or SAS key

Run the command app with system properties:

```bash
cd EventPublisherDemo
./mvnw mn:run \
	-Dmicronaut.config.files=src/main/resources/application.properties \
	-Deventgrid.endpoint=https://<topic-endpoint> \
	-Deventgrid.access-key=<access-key>
```

The publisher creates a CloudEvent in `CloudEventsProducer`, serializes it with the CloudEvents JSON format, and sends it from `EventPublisher` to `/api/events?api-version=2018-01-01`.

## Event Subscriber

The subscriber exposes the webhook endpoint:

```text
GET     /event/updates
POST    /event/updates
OPTIONS /event/updates
```

Run the subscriber locally:

```bash
cd EventSubscriberDemo
./mvnw mn:run
```

By default, Micronaut starts on port `8080`. The `POST /event/updates` endpoint expects `Content-Type: application/cloudevents+json` and deserializes the request body with `CloudEventsDeserializer`.

## Local Webhook Testing

After starting `EventSubscriberDemo`, send a structured CloudEvents JSON payload:

```bash
curl -i http://localhost:8080/event/updates \
	-H 'Content-Type: application/cloudevents+json' \
	-d '{
		"specversion": "1.0",
		"id": "local-test-1",
		"type": "dev.logico-jp.ExampleEventType",
		"source": "dev/logico-jp/source",
		"datacontenttype": "application/json",
		"data": { "message": "hello from curl" }
	}'
```

## Notes

- Azure Event Grid webhook validation uses request headers and validation events. The subscriber includes an `OPTIONS /event/updates` response for allowed origins and methods.
- Do not commit Azure Event Grid keys. Pass them through environment-specific configuration or command-line properties.
