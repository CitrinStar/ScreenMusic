package br.com.citrinstar.screenmusic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConverteDados implements ConverteDadosInterface{

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T obterDados(String json, Class<T> classe) {

        try{
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public String obterDadosPathString(String json, String path) {
        String response = null;
        try{
            JsonNode rootNode = mapper.readTree(json);
            JsonNode extractNode = rootNode.path(path);
            response = extractNode.asText();
        } catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
