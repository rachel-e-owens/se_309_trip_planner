package com.example.demo.websockets;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException{
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        String message = "User:" + username + " has joined the convo.";
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException{
        String username = sessionUsernameMap.get(session);
        if(message.startsWith("@all")){
            broadcast(message);
        }else if(message.startsWith("@")){
            String recipient = message.split(" ")[0].substring(1);
            sendMessageToUser(recipient, "[DM] "+ username + ": " + message);
            sendMessageToUser(username, "[DM] "+ username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        String message = username + " has left the chat";
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        broadcast("error");
    }

    private void sendMessageToUser(String username, String message){
        try{
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch(IOException e){
            System.out.println("error");
            e.printStackTrace();
        }
    }

    private void broadcast(String message){
        sessionUsernameMap.forEach((session, username)->{
            try{
                session.getBasicRemote().sendText(message);
            } catch(IOException e){
                System.out.println("error");
                e.printStackTrace();
            }
        });
    }
}
