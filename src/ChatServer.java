import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        System.out.println("Waiting for clients...");
        ServerSocket ss = new ServerSocket(9806); //cria uma nova conexão socket
                                                  //create a new socket connection
        while(true){
            Socket soc = ss.accept();  //aceita conexões de maneira constante / Accepting new connections
            System.out.println("--CONNECTION ESTABLISHED--");
            ConversationHandler handle = new ConversationHandler(soc);
            handle.start();
        }
    }
}

class ConversationHandler extends Thread{
//Tratamento de conversas
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ConversationHandler(Socket socket) throws IOException{
        this.socket = socket;
    }
 
    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // "in" recebe as informações do cliente
            out = new PrintWriter(socket.getOutputStream(), true);
            //"out" imprime as informações recebidas do cliente, no servidor
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}