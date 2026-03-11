package school.sptech.service;

import school.sptech.Log;
import school.sptech.model.Partida;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class PartidaService {

    private final Scanner sc;
    private final Log log;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ArrayList<Partida> partidas = new ArrayList<>();

    public PartidaService(Scanner sc, Log log) {
        this.sc = sc;
        this.log = log;
    }

    public void cadastrarPartida() {
        boolean encerrarPartida = true;
        do {
            System.out.println("Insira os dados da partida disputada pela equipe:");

            System.out.print("Data da partida (dia/mês/ano): ");
            String data = sc.nextLine();
            LocalDate dataPartida = LocalDate.parse(data, formatter);

            System.out.print("Resultado: ");
            String resultado = sc.nextLine();

            System.out.print("Pontos: ");
            Integer pontos = Integer.parseInt(sc.nextLine());

            System.out.print("Tipo (Campeonato/Treino/Amistoso): ");
            String tipo = sc.nextLine();

            Partida partida = new Partida(dataPartida, resultado, pontos, tipo);
            partidas.add(partida);
            log.registrarLogPartida(dataPartida, tipo);

            System.out.println("Deseja adicionar mais uma partida?(s/n)");
            String respostaPartida = sc.nextLine();
            if (respostaPartida.equals("n")) {
                encerrarPartida = false;
            }
            System.out.println();
        } while (encerrarPartida);
    }

    public void historicoPartidas() {
        if (partidas.isEmpty()) {
            System.out.println("Nenhuma partida foi cadastrada no sistema!");
        } else {
            System.out.println("HISTÓRICO DE PARTIDAS: ");
            for (int i = 0; i < partidas.size(); i++) {
                Partida partida = partidas.get(i);

                System.out.println("Partida " + (i+1));
                System.out.println("Data da partida: " + partida.getData().format(formatter));
                System.out.println("Resultado: " + partida.getResultado());
                System.out.println("Pontos: " + partida.getPontos());
                System.out.println("Tipo de partida: " + partida.getTipo());
                System.out.println();
            }
        }
        System.out.println();
    }
}