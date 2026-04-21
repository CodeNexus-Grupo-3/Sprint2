package school.sptech.service;

import school.sptech.model.Log;

import java.time.LocalDateTime;

public class LogService {

    public Log sucesso(
            String status,
            String evento,
            String servico)
    {
        return new Log(LocalDateTime.now(), status, evento, servico);
    }

    public Log erro(
            String status,
            String evento,
            String servico,
            String mensagemErro,
            String stacktrace)
    {
        return new Log(LocalDateTime.now(), status, evento, servico, mensagemErro, stacktrace);
    }
}