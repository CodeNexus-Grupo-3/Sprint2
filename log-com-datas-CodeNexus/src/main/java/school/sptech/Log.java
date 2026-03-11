package school.sptech;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Log {
    ArrayList<String> log = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss '-' dd/MM/yyyy");

    public void registrarLogJogador(String nome) {
        LocalDateTime registroJogador = LocalDateTime.now();
        String registroJogadorFormatado = "Jogador " + nome + " Cadastrado: " + registroJogador.format(formatter);
        log.add(registroJogadorFormatado);
    }

    public void registrarLogPartida(LocalDate data, String tipo) {
        LocalDateTime registroPartida = LocalDateTime.now();
        String registroPartidaFormatado = "Partida de " + tipo + " da data " + data + " Cadastrada: " + registroPartida.format(formatter);
        log.add(registroPartidaFormatado);
    }

    public void mostrarLog() {
        for (int i = 0; i < log.size(); i++) {
            System.out.println(log.get(i));
        }
    }

}
