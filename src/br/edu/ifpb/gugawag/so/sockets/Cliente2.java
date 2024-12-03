package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {

    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente NFS ==");

        // Configurando o socket para conexão com o servidor
        Socket socket = new Socket("127.0.0.1", 7001);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);

        // Menu para o cliente
        while (true) {
            System.out.println("\nComandos disponíveis:");
            System.out.println("1. readdir - Listar arquivos");
            System.out.println("2. rename <antigo> <novo> - Renomear arquivo");
            System.out.println("3. create <nome> - Criar arquivo");
            System.out.println("4. remove <nome> - Remover arquivo");
            System.out.println("5. sair - Encerrar conexão");

            System.out.print("\nDigite um comando: ");
            String comando = scanner.nextLine();

            if (comando.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando conexão...");
                break;
            }

            // Envia o comando para o servidor
            dos.writeUTF(comando);

            // Lê a resposta do servidor
            String resposta = dis.readUTF();
            System.out.println("Resposta do servidor: " + resposta);
        }

        // Fechando conexões
        dos.close();
        dis.close();
        socket.close();
        scanner.close();
    }
}
