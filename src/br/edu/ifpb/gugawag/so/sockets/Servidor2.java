package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor2 {

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor NFS ==");

        List<String> arquivos = new ArrayList<>();
        arquivos.add("arquivo1.txt");
        arquivos.add("arquivo2.txt");
        arquivos.add("documento.pdf");

        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while (true) {
            String comando = dis.readUTF();
            System.out.println("Comando recebido: " + comando);
            String[] partes = comando.split(" ", 2);
            String operacao = partes[0];
            String argumento = partes.length > 1 ? partes[1] : "";

            switch (operacao.toLowerCase()) {
                case "readdir":
                    dos.writeUTF(String.join(",", arquivos));
                    break;

                case "rename":
                    String[] nomes = argumento.split(" ", 2);
                    if (nomes.length < 2) {
                        dos.writeUTF("Erro: comando inválido.");
                    } else if (arquivos.contains(nomes[0])) {
                        arquivos.remove(nomes[0]);
                        arquivos.add(nomes[1]);
                        dos.writeUTF("Arquivo renomeado com sucesso.");
                    } else {
                        dos.writeUTF("Erro: arquivo não encontrado.");
                    }
                    break;

                case "create":
                    if (arquivos.contains(argumento)) {
                        dos.writeUTF("Erro: arquivo já existe.");
                    } else {
                        arquivos.add(argumento);
                        dos.writeUTF("Arquivo criado com sucesso.");
                    }
                    break;

                case "remove":
                    if (arquivos.contains(argumento)) {
                        arquivos.remove(argumento);
                        dos.writeUTF("Arquivo removido com sucesso.");
                    } else {
                        dos.writeUTF("Erro: arquivo não encontrado.");
                    }
                    break;

                default:
                    dos.writeUTF("Comando desconhecido.");
            }
        }
    }
}
