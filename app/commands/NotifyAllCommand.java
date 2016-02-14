package commands;

import controllers.ws.WSContainer;
import models.Response;
import play.libs.Json;
import play.mvc.WebSocket;
import services.ResponseWrapper;

/**
 * Command for notifying all subscribers about event on server
 */
public class NotifyAllCommand implements  Command{
    @Override
    public void execute(Object arg) {
        Response response = (Response) arg;
        ResponseWrapper wrapper = new ResponseWrapper(response);
        String message = Json.toJson(wrapper).toString();
        for (WebSocket.Out<String> out : WSContainer.getConnections()) {
            out.write(message);
        }
    }
}
