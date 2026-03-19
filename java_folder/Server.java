import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6000); // 5000 (Python) 6000 (Java)
        System.out.println("Servidor Java a correr na porta 6000...");

        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread(new ClientHandler(socket));
            t.start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Cliente ligado: " + socket.getInetAddress());

                String data;
                while ((data = in.readLine()) != null) {
                    System.out.println("Recebido de " + socket.getInetAddress() + ": " + data);
                    out.println(data.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Cliente " + socket.getInetAddress() + " desligou inesperadamente");
            } finally {
                try { socket.close(); } catch (IOException e) {}
                System.out.println("Cliente " + socket.getInetAddress() + " desligou");
            }
        }
    }
}