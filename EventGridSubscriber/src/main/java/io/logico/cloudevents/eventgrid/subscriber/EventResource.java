
package io.logico.cloudevents.eventgrid.subscriber;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import org.glassfish.json.JsonUtil;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Path("/event")
public class EventResource {

    @Path("/updates")
    @GET
    public Response getEvent() {
        return Response.noContent().status(Response.Status.OK).build();
    }

    @Path("/updates")
    @POST
    public Response receiveEvent(Optional<JsonObject> obj) {
            if(obj.isEmpty()) return Response.noContent().status(Response.Status.OK).build();

        EventFormat format = EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE);

        CloudEvent ce = format.deserialize(obj.get().toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("Received JSON String -- " + obj.get().toString());
        System.out.println("Converted to CloudEvent -- " + ce.toString());
        JsonObject customData = JsonUtil.toJson(new String(ce.getData())).asJsonObject();
        System.out.println("Data in CloudEvent -- " + customData.toString());
        return Response.noContent().status(Response.Status.ACCEPTED).build();
    }

    @Path("/updates")
    @OPTIONS
    public Response isWebhookEnabled() {
        return Response.ok()
                .allow("GET", "POST", "OPTIONS")
                .header("Webhook-Allowed-Origin","eventgrid.azure.net")
                .build();
    }
}
