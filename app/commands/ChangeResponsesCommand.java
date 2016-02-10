package commands;

import controllers.ws.WSContainer;
import play.libs.Json;
import play.mvc.WebSocket;
import services.ResponseWrapper;

public class ChangeResponsesCommand implements Command{
    @Override
    public void execute(Object arg) {
        ResponseWrapper wrapper = new ResponseWrapper();
        wrapper.setResponseAmount(0);
        String message = Json.toJson(wrapper).toString();
        for (WebSocket.Out<String> out : WSContainer.getConnections()) {
            out.write(message);
        }
    }
}
