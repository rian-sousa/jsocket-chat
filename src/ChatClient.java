import javax.swing.*;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    static JFrame chatWindow = new JFrame("Chat Application");
    static JTextArea chatArea = new JTextArea(22, 40);    //definindo tamanho do chat
    static JTextField textField = new JTextField(40);    //tamanho do campo de digitação
    static JLabel blankLabel = new JLabel("             ");  //Linha em branco entre mensagens
    static JButton sendButton = new JButton("Enviar/Send");
    static PrintWriter out;

    ChatClient(){

        chatWindow.setLayout(new FlowLayout());

        chatWindow.add(new JScrollPane(chatArea));   //Barra de rolagem do chat
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);

        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatWindow.setSize(475,500);
        chatWindow.setVisible(true);

        textField.setEditable(false);  //só é permitido o usuário digitar após a conexão ser estabelecida
        chatArea.setEditable(false);

    }

    void startChat() throws Exception{

        String ipAddress = JOptionPane.showInputDialog(
            chatWindow,  //qual fram vai aparecer
            "Enter IP Address: ", //Mensagem que ira aparecer
            "IP Address Required!",  //Titulo da janela
            JOptionPane.PLAIN_MESSAGE);

        Socket soc = new Socket(ipAddress, 9800);
        BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        PrintWriter out = new PrintWriter(soc.getOutputStream(), true);

        while(true){

        }

    }



    public static void main(String[] args) throws Exception {

        ChatClient client = new ChatClient();
        client.startChat();
     
        
    }
}