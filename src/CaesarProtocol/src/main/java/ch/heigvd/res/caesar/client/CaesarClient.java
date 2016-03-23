package ch.heigvd.res.caesar.client;

import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 * @author Ciani Antony
 * @author Peretti Christophe
 */
public class CaesarClient {

    private static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());

    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    boolean connected = false;
    int clientId;
    boolean keyReceived = false;
    int secretKey = -1;
    Scanner keyboard = new Scanner(System.in);

        public void run() {
            String notification;
            try {
                while ((connected && (notification = in.readLine()) != null)) {

                    if(!keyReceived){
                        //LOG.info(notification.substring(0, Protocol.CMD_SENDKEY.length()));
                        if(notification.substring(0, Protocol.CMD_SENDKEY.length()).equals(Protocol.CMD_SENDKEY )){
                            secretKey = Integer.valueOf(notification.substring(Protocol.CMD_SENDKEY.length(), notification.length()));
                            keyReceived = true;
                            LOG.log(Level.INFO, "Key received");
                            LOG.log(Level.INFO, "Key is " + secretKey);

                        }
                       sendMsg("HELLOOOO");
                    }
                    else{
                        LOG.log(Level.INFO, "Server echoed crypted message is: " + notification);
                        notification = Protocol.caesarDecrypt(notification, secretKey);
                        LOG.log(Level.INFO, "Server echoed decrypted message is: " + notification);
                        if(notification.equals(Protocol.CMD_QUIT)){
                            disconnect();
                        }
                       String msg = keyboard.nextLine();
                       sendMsg(msg);

                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
                connected = false;
            } finally {
                cleanup();
            }
        }


    public void connect(String serverAddress, int serverPort) {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            connected = true;
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
            cleanup();
            return;
        }
        // Let us start a thread, so that we can listen for server notifications
        run();
        
        
    }

    
    
    public void disconnect() {
        LOG.log(Level.INFO, "You requested to be disconnected.");
        connected = false;
        out.println();
        cleanup();
    }
    
    public void sendMsg(String msg){
        LOG.info(msg + " sent to the server");
        out.println(Protocol.caesarEncrypt(msg, secretKey));
        out.flush();
        
    }

    private void cleanup() {

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (out != null) {
            out.close();
        }

        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Server > %5$s%n");
        LOG.info("Caesar client starting...");
        LOG.info("Protocol constant: " + Protocol.DEFAULT_PORT);
        CaesarClient cc = new CaesarClient();
        //cc.connect("10.192.94.149", Protocol.DEFAULT_PORT);
        cc.connect("localhost", Protocol.DEFAULT_PORT);

        
    }

}
