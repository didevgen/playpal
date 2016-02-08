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

public class WSContainer {
    private static List<WebSocket.Out<String>> connections = new ArrayList<WebSocket.Out<String>>();
    public void startConnection(WebSocket.In<String> in, WebSocket.Out<String> out){

        connections.add(out);

        out.write(Json.toJson(new ResponseWrapper(false)).toString());

        in.onMessage(new F.Callback<String>(){
            public void invoke(String event){
                CommandContainer.getCommand(Commands.NOTIFY_ALL).execute(event);
            }
        });

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
