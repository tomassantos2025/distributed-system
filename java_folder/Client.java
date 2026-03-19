import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 5000); // 5000 (Python) 6000 (Java)
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Cliente Java ligado. Ctrl+C para sair.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("\nCliente desligado pelo utilizador");
                socket.close();
            } catch (IOException e) {}
        }));

        try {
            String msg;
            while (true) {
                System.out.print("Mensagem: ");
                msg = teclado.readLine();
                if (msg == null) break;
                out.println(msg);

                String response = in.readLine();
                if (response == null) break;
                System.out.println("Resposta: " + response);
            }
        } catch (IOException e) {
            System.out.println("\nErro na conexão ou cliente fechado");
        } finally {
            socket.close();
        }
    }
}
