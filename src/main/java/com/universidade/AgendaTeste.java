package com.universidade;

import java.util.Scanner;

import com.universidade.entity.Contato;

public class AgendaTeste 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        // Menu inicial para dados de conexão
        System.out.println("--- CONFIGURAÇÃO DE CONEXÃO COM O BANCO ---");
        System.out.print("Digite o nome do banco de dados: ");
        String nomeBanco = sc.nextLine();
        System.out.print("Digite o usuário do banco: ");
        String usuario = sc.nextLine();
        System.out.print("Digite a senha do banco: ");
        String senha = sc.nextLine();  

        ConexaoBanco con = new ConexaoBanco(nomeBanco, senha, usuario);
        con.conectar();

        AgendaTelefonica agenda = new AgendaTelefonica(con);
        int opcao;

        do {
            System.out.println("\n--- MENU AGENDA TELEFÔNICA ---");
            System.out.println("1. Adicionar um novo contato");
            System.out.println("2. Remover um contato existente");
            System.out.println("3. Buscar um contato pelo nome");
            System.out.println("4. Listar todos os contatos");
            System.out.println("5. Sair do programa");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    Contato novo = new Contato();
                    System.out.print("Nome: ");
                    novo.setNome(sc.nextLine());
                    System.out.print("Telefone: ");
                    novo.setTelefone(sc.nextLine());
                    System.out.print("Email: ");
                    novo.setEmail(sc.nextLine());
                    if (agenda.adicionarContato(novo)) {
                        System.out.println("Contato adicionado!");
                    } else {
                        System.out.println("Erro ao adicionar contato.");
                    }
                    break;
                case 2:
                    System.out.print("Nome do contato a remover: ");
                    String nomeRemover = sc.nextLine();
                    if (agenda.removerContato(nomeRemover)) {
                        System.out.println("Contato removido.");
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 3:
                    System.out.print("Nome do contato a buscar: ");
                    String nomeBuscar = sc.nextLine();
                    Contato encontrado = agenda.buscarContato(nomeBuscar);
                    if (encontrado != null) {
                        System.out.println("Nome: " + encontrado.getNome());
                        System.out.println("Telefone: " + encontrado.getTelefone());
                        System.out.println("Email: " + encontrado.getEmail());
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("Lista de contatos:");
                    if(agenda.listarContatos().isEmpty()) {
                        System.out.println("Nenhum contato cadastrado.");
                        break;
                    }else{
                       for (Contato c : agenda.listarContatos()) {
                           System.out.println("Nome: " + c.getNome() + ", Telefone: " + c.getTelefone() + ", Email: " + c.getEmail());
                       }
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);

        System.out.println("Programa encerrado.");
        con.desconectar();
        sc.close();
    }
}