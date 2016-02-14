package controllers.ws;


import play.mvc.WebSocket;

import javax.transaction.Transactional;

/**
 * Handles all connections via websocket
 */
public class WSController {

    public WebSocket<String> doWS(){
        return new WebSocket<String>(){
            public void onReady(In<String> in, Out<String> out){
                new WSContainer().startConnection(in, out);
            }
        };
    }
}
