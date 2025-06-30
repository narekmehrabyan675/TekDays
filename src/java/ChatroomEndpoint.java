/*
import com.tekdays.GetHttpSessionConfigurator;
*/
import com.tekdays.ChatMessage;
import com.tekdays.ChatService;
import com.tekdays.TekUser;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.*;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

import grails.util.Holders;


@ServerEndpoint(value = "/chatroom", configurator = GetHttpSessionConfigurator.class)
@WebListener
public class ChatroomEndpoint implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ChatroomEndpoint.class);
    private static final Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
    private static final Map<String, Session> userSessions = Collections.synchronizedMap(new HashMap<String , Session>());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        ServerContainer serverContainer = (ServerContainer) servletContext.getAttribute("javax.websocket.server.ServerContainer");

        try {
            serverContainer.addEndpoint(ChatroomEndpoint.class);
        } catch (DeploymentException e) {
            log.error("WebSocket endpoint deployment failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        if (httpSession == null || httpSession.getAttribute("user") == null) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        TekUser user = (TekUser) httpSession.getAttribute("user");
        String username = user.getUserName();
        userSessions.put(username, session);
        session.getUserProperties().put("username", username);
        users.add(session);

        ChatService chatService = (ChatService) Holders.getGrailsApplication()
                .getMainContext()
                .getBean(ChatService.class);

        System.out.println(chatService.findUserByUsername("I am from username" + username));
        log.info("Chat opened by: " + username);


        String chatWith = getQueryParam(session, "chatWith");

        if (chatWith != null && !chatWith.isEmpty()) {
            // Download private messages
            List<ChatMessage> history = chatService.getPrivateMessages(username, chatWith);
            for (ChatMessage msg : history) {
                try {
                    String prefix = msg.getSender().getUserName().equals(username)
                            ? "[You -> " + chatWith + "]"
                            : "[Private] " + msg.getSender().getUserName();
                    session.getBasicRemote().sendText(prefix + ": " + msg.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // getting chat messages
            List<ChatMessage> history = chatService.getRecentMessages(20);
            for (ChatMessage msg : history) {
                try {
                    session.getBasicRemote().sendText("[History] " + msg.getSender().getUserName() + ": " + msg.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            sendMessage(username + " has joined the chatroom.", null);
        }
    }


    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String username = (String) session.getUserProperties().get("username");
        if (message.startsWith("/history")) {
            String[] parts = message.split(" ", 3);
            if (parts.length < 2) return;

            String type = parts[1]; // "group" or "private"
            ChatService chatService = (ChatService) Holders.getGrailsApplication()
                    .getMainContext()
                    .getBean(ChatService.class);

            if ("group".equals(type)) {
                List<ChatMessage> history = chatService.getRecentMessages(20);
                for (ChatMessage msg : history) {
                    session.getBasicRemote().sendText("[History] " + msg.getSender().getUserName() + ": " + msg.getMessage());
                }
            } else if ("private".equals(type) && parts.length == 3) {
                String chatWith = parts[2];
                List<ChatMessage> history = chatService.getPrivateMessages(username, chatWith);
                for (ChatMessage msg : history) {
                    String prefix = msg.getSender().getUserName().equals(username)
                            ? "[You -> " + chatWith + "]"
                            : "[Private] " + msg.getSender().getUserName();
                    session.getBasicRemote().sendText(prefix + ": " + msg.getMessage());
                }
            }
            return;
        }

        if (message.startsWith("/w ")) {
            // Private message
            String[] parts = message.split(" ", 3);
            if (parts.length < 3) return;

            String to = parts[1];
            String text = parts[2];

            Session toSession = userSessions.get(to);
            if (toSession != null && toSession.isOpen()) {
                toSession.getBasicRemote().sendText("[Private] " + username + ": " + text);
            }

            try {
                session.getBasicRemote().sendText("[You -> " + to + "] " + text);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Saving on private
            ChatService chatService = (ChatService) Holders.getGrailsApplication()
                    .getMainContext()
                    .getBean(ChatService.class);
            chatService.savePrivateMessage(username, to, text);

        } else {
            if (username == null) {
                session.getUserProperties().put("username", message);
                sendMessage(message + " has joined the chatroom.", null);
                return;
            }
            sendMessage(message, session);

            ChatService chatService = (ChatService) Holders.getGrailsApplication()
                    .getMainContext()
                    .getBean(ChatService.class);
            chatService.saveMessage(username, message);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        String username = (String) session.getUserProperties().get("username");
        users.remove(session);

        if (username != null) {
            sendMessage(username + " has left the chatroom.", null);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket error", throwable);
    }

    private void sendMessage(String message, Session fromSession) {
        String prefix = "";
        if (fromSession != null) {
            String username = (String) fromSession.getUserProperties().get("username");
            prefix = username + ": ";
        }

        synchronized (users) {
            for (Session user : users) {
                try {
                    user.getBasicRemote().sendText(prefix + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String getQueryParam(Session session, String name) {
        String query = session.getQueryString();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2 && pair[0].equals(name)) {
                    return pair[1];
                }
            }
        }
        return null;
    }

}
