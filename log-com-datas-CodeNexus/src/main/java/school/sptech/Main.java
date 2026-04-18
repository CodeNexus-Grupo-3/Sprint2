package school.sptech;

import school.sptech.service.JogadorService;
import school.sptech.service.PartidaService;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Log log = new Log();

        JogadorService jogador = new JogadorService(sc, log);
        PartidaService partida = new PartidaService(sc, log);

        boolean encerrar = true;

        for (Integer i = 0; i < 10; i++) {
            Integer aleatorio = ThreadLocalRandom.current().nextInt(5);
            if (aleatorio.equals(1)) {
                Integer jogadorAleatorio = ThreadLocalRandom.current().nextInt(6);
                if (jogadorAleatorio.equals(1)) {
                    log.registrarLogJogador("Marina");
                } else if (jogadorAleatorio.equals(2)) {
                    log.registrarLogJogador("Vinícius");
                } else if (jogadorAleatorio.equals(3)) {
                    log.registrarLogJogador("Lucas");
                } else if (jogadorAleatorio.equals(4)) {
                    log.registrarLogJogador("Nicolas");
                } else {
                    log.registrarLogJogador("Lesley");
                }
            } else if (aleatorio.equals(2)) {
                log.registrarLogJogadorErro();
            } else if (aleatorio.equals(3)) {
                Integer partidaAleatoria = ThreadLocalRandom.current().nextInt(6);
                if (partidaAleatoria.equals(1)) {
                    log.registrarLogPartida("Campeonato");
                } else if (partidaAleatoria.equals(2)) {
                    log.registrarLogPartida("Treino");
                } else {
                    log.registrarLogPartida("Amistoso");
                }
            } else {
                log.registrarLogPartidaErro();
            }
        }

        System.out.print("CODENEXUS\nInsira seu e-mail: ");
        String emailCoach = sc.nextLine();
        System.out.print("Insira sua senha: ");
        String senhaCoach = sc.nextLine();

        System.out.println();

        System.out.println("Olá, Bem vindo a CodeNexus!");

        do {
            System.out.println("Escolha uma de nossas funcionalidades:\n- Cadastrar Jogador (1)\n- Adicionar Partida (2)\n- Lista de Jogadores (3)\n- Histórico de Partidas (4)\n(Digite o número da funcionalidade que deseja acessar)");
            Integer funcionalidade = Integer.parseInt(sc.nextLine());
            System.out.println();
            if (funcionalidade.equals(1)) {
                jogador.cadastrarJogadores();
            } else if (funcionalidade.equals(2)) {
                partida.cadastrarPartida();
            } else if (funcionalidade.equals(3)) {
                jogador.listaJogadores();
            } else if (funcionalidade.equals(4)) {
                partida.historicoPartidas();
            } else {
                System.out.println("Opção inválida!");
            }

            System.out.println("Deseja acessar outra funcionalidade?(s/n)");
            String respostaFuncionalidade = sc.nextLine();
            if (respostaFuncionalidade.equals("n")) {
                encerrar = false;
            }
            System.out.println();
        } while (encerrar);

        log.mostrarLog();
    }
}