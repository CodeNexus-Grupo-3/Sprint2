package school.sptech.model;

import java.time.LocalDateTime;

public class Log {

    private LocalDateTime dataHora;
    private String status;
    private String evento;
    private String servico;
    private String mensagemErro;
    private String stacktrace;
    private Integer fkUsuario;

    public Log(
            LocalDateTime dataHora,
            String status,
            String evento,
            String servico,
            String mensagemErro,
            String stacktrace,
            Integer fkUsuario
    ) {
        this.dataHora = dataHora;
        this.status = status;
        this.evento = evento;
        this.servico = servico;
        this.mensagemErro = mensagemErro;
        this.stacktrace = stacktrace;
        this.fkUsuario = fkUsuario;
    }

    public Log(
            LocalDateTime dataHora,
            String status,
            String evento,
            String servico
    ) {
        this.dataHora = dataHora;
        this.status = status;
        this.evento = evento;
        this.servico = servico;
        mensagemErro = null;
        stacktrace = null;
        fkUsuario = null;
    }

    public Log(
            LocalDateTime dataHora,
            String status,
            String evento,
            String servico,
            String mensagemErro,
            String stacktrace
    ) {
        this.dataHora = dataHora;
        this.status = status;
        this.evento = evento;
        this.servico = servico;
        this.mensagemErro = mensagemErro;
        this.stacktrace = stacktrace;
        fkUsuario = null;
    }

    public String getEvento() {
        return evento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getStatus() {
        return status;
    }

    public String getServico() {
        return servico;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }
}