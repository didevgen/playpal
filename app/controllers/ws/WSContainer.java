package controllers.ws;

import constants.CommandContainer;
import constants.Commands;
import play.libs.F;
import play.libs.Json;
import play.mvc.WebSocket;
import services.ResponseWrapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for WebSocket sessions
 */
public class WSContainer {
    private static List<WebSocket.Out<String>> connections = new ArrayList<WebSocket.Out<String>>();
    public void startConnection(WebSocket.In<String> in, WebSocket.Out<String> out){

        connections.add(out);
        /*
        When connection is established user would have amount of responses
         */
        out.write(Json.toJson(new ResponseWrapper(false)).toString());
        /**
         * When the message is received every customer must be notified
         */
        in.onMessage(new F.Callback<String>(){
            public void invoke(String event){
                CommandContainer.getCommand(Commands.NOTIFY_ALL).execute(event);
            }
        });
        /**
         * Removing the connection from the container when connection is closed
         */
        in.onClose(new F.Callback0(){
            public void invoke(){
                connections.remove(out);
            }
        });
    }
    public static List<WebSocket.Out<String>> getConnections() {
        return connections;
    }

}
