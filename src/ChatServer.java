import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    static ArrayList<String> userNames = new ArrayList<String>();
    //lista para evitar a duplicação de nomes no chat
    static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();
    //faz o servidor enviar a  mensagem para todos os clientes

    public static void main(String[] args) throws Exception {
        System.out.println("Waiting for clients...");
        ServerSocket ss = new ServerSocket(8080); //cria uma nova conexão socket
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
    String name;

    public ConversationHandler(Socket socket) throws IOException{
        this.socket = socket;
    }
 
    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // "in" recebe as informações do cliente
            out = new PrintWriter(socket.getOutputStream(), true);
            //"out" imprime as informações recebidas do cliente, no servidor

            int count = 0;
            while (true) {
                if(count > 0)
                {
                    out.println("NAME-ALREADY-EXISTS"); //mostra para o usuário que o nome já existe
                } 
                else
                {
                    out.println("NAME-REQUIRED");
                }
                name = in.readLine();

                if(name == null)
                {
                    return; //volta pro inicio
                }

                if(!ChatServer.userNames.contains(name))
                { //caso o nome não seja duplicado
                    ChatServer.userNames.add(name);
                    break;  //quebra o loop
                }
                count++; 
            }

            out.println("NAME-ACCEPTED");
            ChatServer.printWriters.add(out);

            while (true) {
                String message = in.readLine();

                if(message == null)
                {
                    return; 
                }

                for(PrintWriter writer : ChatServer.printWriters)
                { //pra cada writer da lista, irá enviar a mensagem
                    writer.println(">>" + name + ": " + message);
                }

                
            }



        } catch (Exception e) {
            System.out.println(e);
        }
    }

}