package school.sptech;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Log {
    ArrayList<String> log = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

//    public void registrar(String nivel, String origem, String mensagem) {
//        LocalDateTime agora = LocalDateTime.now();
//        String registroFormatado = agora.format(formatter)
//                + " | " + nivel
//                + " | " + origem
//                + " | " + mensagem;
//
//        log.add(registroFormatado);
//    }
//
//    public void info(String origem, String mensagem) {
//        registrar("INFO ", origem, mensagem);
//    }
//
//    public void error(String origem, String mensagem) {
//        registrar("ERROR", origem, mensagem);
//    }

    public void registrarLogJogador(String nome) {
        LocalDateTime registroJogador = LocalDateTime.now();
        String registroJogadorFormatado = registroJogador.format(formatter) + " | SUCESS | JogadorService | Jogador " + nome + " cadastrado com sucesso";
        log.add(registroJogadorFormatado);
    }

    public void registrarLogPartida(String tipo) {
        LocalDateTime registroPartida = LocalDateTime.now();
        String registroPartidaFormatado = registroPartida.format(formatter) + " | SUCESS | PartidaService | Partida de " + tipo + " cadastrada com sucesso";
        log.add(registroPartidaFormatado);
    }

    public void registrarLogJogadorErro() {
        LocalDateTime registroJogador = LocalDateTime.now();
        String registroJogadorFormatado = registroJogador.format(formatter) + " | ERROR | JogadorService | Jogador não foi cadastrado";
        log.add(registroJogadorFormatado);
    }

    public void registrarLogPartidaErro() {
        LocalDateTime registroPartida = LocalDateTime.now();
        String registroPartidaFormatado = registroPartida.format(formatter) + " | ERROR | PartidaService | Partida não foi cadastrada";
        log.add(registroPartidaFormatado);
    }

    public void mostrarLog() {
        for (int i = 0; i < log.size(); i++) {
            System.out.println(log.get(i));
        }
    }

}
