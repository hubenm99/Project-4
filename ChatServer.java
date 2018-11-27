

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final class ChatServer {
    private static int uniqueId = 0;
    private final List<ClientThread> clients = new ArrayList<>();
    private final int port;



    private ChatServer(int port) {
        this.port = port;
    }

    /*
     * This is what starts the ChatServer.
     * Right now it just creates the socketServer and adds a new ClientThread to a list to be handled
     */
    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable r = new ClientThread(socket, uniqueId++);
                Thread t = new Thread(r);
                clients.add((ClientThread) r);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     *  > java ChatServer
     *  > java ChatServer portNumber
     *  If the port number is not specified 1500 is used
     */
    public static void main(String[] args) {
        ChatServer server;
        if (args.length == 3) {
            server = new ChatServer(Integer.parseInt(args[2]));
        } else {
            server = new ChatServer(1500);
        }
        server.start();
    }


    /*
     * This is a private class inside of the ChatServer
     * A new thread will be created to run this every time a new client connects.
     */
    private final class ClientThread implements Runnable {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        int id;
        String username;
        ChatMessage cm;

        private ClientThread(Socket socket, int id) {
            this.id = id;
            this.socket = socket;
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                username = (String) sInput.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        synchronized private void broadcast(String message) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss ");
            String finalDate = dateFormat.format(date);
            // need to add filter
            ChatFilter filter = new ChatFilter(message);
            String newMessage = filter.filter(message);
            writeMessage(finalDate +  newMessage);
        }

        synchronized private void directMessage(String message, String username) {
//            message = message.substring(message.indexOf(" "), message.length());
//            message = message.substring(message.indexOf(" "), message.length());
//            message = message.substring(message.indexOf(" "), message.length());
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss ");
            String finalDate = dateFormat.format(date);
            ChatFilter filter = new ChatFilter(message);
            String newMessage = filter.filter(message);
            writeMessage(finalDate + username + " -> " + newMessage);
        }

        private boolean writeMessage(String message) {
            if (!socket.isConnected()) {
                return false;
            } else {
                try {
                    sOutput.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } //end catch

                return true;
            } //end else
        }

        synchronized private void remove(int id) {
            clients.remove(id);
        }

        private void close() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        /*
         * This is what the client thread actually runs.
         */
        @Override
        public void run() {
            // Read the username sent to you by client

            try {
//            if (cm.getTypeOfMessage() == 1) {
//                socket.close();
//            } else {
                System.out.println(username + ": Ping");

                try {
                    broadcast(username + ": Pong\n");
                    broadcast(username + ": ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (true) {

                    try {
                        cm = (ChatMessage) sInput.readObject();

                        if (cm.getMessage().contains("/msg")) {
                            String message = cm.getMessage();
                            message = message.substring(message.indexOf(cm.getRecipient())
                                    + cm.getRecipient().length() + 1);
                            directMessage(cm.getRecipient() + " : " + message + "\n", username);
                            broadcast(username + " : ");

                        } else if (cm.getMessage().contains("/list")) {
                            for (int i = 0; i < clients.size(); i++) {
                                if (!clients.get(i).toString().equals(username)) {
                                    System.out.println(clients.get(i).toString());
                                }
                            }
                        } else {
                            broadcast(username + ": " + cm.getMessage() + "\n");
                            broadcast(username + ": ");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
//                    System.out.println(username + ": Ping");
//
//
//                    // Send message back to the client
//                    try {
//                        sOutput.writeObject("Pong");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
                //}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}