# Azure Event Grid and CloudEvents
How to interact with Azure Event Grids using CloudEvents APIs instead of Azure Event Grid Client Library

## Modules
- CloudEvents2EventGrid
  - This is a client application which posts cloudevents to Azure Event Grid using CloudEvents APIs.
- EventGridSubscriber
  - This is a JAX-RS application which consumes cloudevents through Azure Event Grid using CloudEvents APIs. This application subscribes Azure Event Griid topic and Event Grid sends events with webhook.

## Article

This content is used in the following articles. For more details, please check the following articles.

### Japanese 
- Logico Inside
  - [https://logico-jp.io/2020/09/06/use-cloudevents-schema-in-azure-event-grid/](https://logico-jp.io/2020/09/06/use-cloudevents-schema-in-azure-event-grid/)
  - [https://logico-jp.io/2020/10/23/tips-for-using-event-grid-sdk-to-handle-cloudevents/](https://logico-jp.io/2020/10/23/tips-for-using-event-grid-sdk-to-handle-cloudevents/)
  - [https://logico-jp.io/2020/10/30/using-cloudevents-apis-to-post-events-to-azure-event-grid/](https://logico-jp.io/2020/10/30/using-cloudevents-apis-to-post-events-to-azure-event-grid/)
  - [https://logico-jp.io/2020/10/31/using-cloudevents-apis-to-create-an-application-which-subscribe-an-azure-event-grid-topic/](https://logico-jp.io/2020/10/31/using-cloudevents-apis-to-create-an-application-which-subscribe-an-azure-event-grid-topic/)

### English
- Medium
  - [https://logico-jp.medium.com/use-cloudevents-apis-to-interact-with-azure-event-grid-32dc63518af3](https://logico-jp.medium.com/use-cloudevents-apis-to-interact-with-azure-event-grid-32dc63518af3)
