package school.sptech.model;

import java.time.LocalDate;

public class Partida {

    private LocalDate data;
    private String resultado;
    private Integer pontos;
    private String tipo;

    public Partida(LocalDate data, String resultado, Integer pontos, String tipo) {
        this.data = data;
        this.resultado = resultado;
        this.pontos = pontos;
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}