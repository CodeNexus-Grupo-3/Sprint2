package school.sptech.service;

import school.sptech.Log;
import school.sptech.model.Jogador;

import java.util.ArrayList;
import java.util.Scanner;

public class JogadorService {
    private final Scanner sc;
    private final Log log;

    private final ArrayList<Jogador> jogadores = new ArrayList<>();

    public JogadorService(Scanner sc, Log log) {
        this.sc = sc;
        this.log = log;
    }

    public void cadastrarJogadores() {
        boolean encerrarCadastro = true;

        do {
            System.out.println("Insira os dados cadastrais do novo jogador:");

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Posição: ");
            String posicao = sc.nextLine();

            System.out.print("E-mail: ");
            String email = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            Jogador novoJogador = new Jogador(nome, posicao, email, senha);
            jogadores.add(novoJogador);
            log.registrarLogJogador(nome);

            System.out.println("Deseja inserir mais um jogador?(s/n)");
            String respostaJogador = sc.nextLine();
            if (respostaJogador.equals("n")) {
                encerrarCadastro = false;
            }
            System.out.println();
        } while (encerrarCadastro);
    }

    public void listaJogadores() {
        if (jogadores.isEmpty()) {
            System.out.println("Não há jogadores cadastrados na sua equipe!");
        } else {
            System.out.println("LISTA DOS JOGADORES CADASTRADOS: ");
            for (int i = 0; i < jogadores.size(); i++) {
                Jogador jogador = jogadores.get(i);

                System.out.println("Jogador " + (i+1));
                System.out.println("Nome: " + jogador.getNome());
                System.out.println("Posição: " + jogador.getPosicao());
                System.out.println("E-mail: " + jogador.getEmail());
                System.out.println();
            }
        }
        System.out.println();
    }
}
