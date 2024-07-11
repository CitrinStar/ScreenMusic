package br.com.citrinstar.screenmusic.service;

public interface ConverteDadosInterface {

    <T> T obterDados(String json, Class<T> classe);
}
