import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
        
        sendButton.addActionListener(new Listener()); //botão enviar
        textField.addActionListener(new Listener()); //tecla "ENTER" também serve como enviat

    }

    void startChat() throws Exception{

        String ipAddress = JOptionPane.showInputDialog(
            chatWindow,  //em qual frame vai aparecer
            "Enter IP Address: ", //Mensagem que ira aparecer
            "IP Address Required!",  //Titulo da janela
            JOptionPane.PLAIN_MESSAGE);

        Socket soc = new Socket(ipAddress, 8080);
        BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(soc.getOutputStream(), true);

        while(true){

            String str = in.readLine(); //ler mensagens p/ servidor
            if(str.equals("NAME-REQUIRED"))
            {
                String name = JOptionPane.showInputDialog(
                    chatWindow,
                    "Enter a unique name: ",    //caixa de diálogo pedindo um nome ao entrar
                    "Name Required!",
                    JOptionPane.PLAIN_MESSAGE);

                out.println(name);
            } 
            else if(str.equals("NAME-ALREADY-EXISTS"))
            {
                String name = JOptionPane.showInputDialog(
                    chatWindow,
                    "Enter another name: ", //caixa de diálogo pedindo outro nome caso já exista
                    "Name Already Exists!!",
                    JOptionPane.WARNING_MESSAGE);

                out.println(name);
            } 
            else if (str.equals("NAME-ACCEPTED"))
            {
                textField.setEditable(true);
            }
            else
            {
                chatArea.append(str + "\n"); //acrescenta a mensagem ao chat
            }

        }

    }

    public static void main(String[] args) throws Exception {

        ChatClient client = new ChatClient();
        client.startChat();
     
        
    }
}

class Listener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        ChatClient.out.println(ChatClient.textField.getText()); //envia o texto p/ servidor
        ChatClient.textField.setText(" "); //limpa a área de digitação
        
    }


}