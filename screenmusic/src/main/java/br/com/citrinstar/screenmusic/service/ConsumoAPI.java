package br.com.citrinstar.screenmusic.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ConsumoAPI {

    public String obterDados(String endereco, Optional<String> headerName, Optional<String> headerValue){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = setRequest(endereco, headerName, headerValue);
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

        return response.body();
    }
    private HttpRequest setRequest(String endereco, Optional<String> headerName, Optional<String> headerValue){
        HttpRequest request;
        if(headerName.isPresent() && headerValue.isPresent()){
            request = HttpRequest.newBuilder()
                    .setHeader(headerName.get(), headerValue.get())
                    .uri(URI.create(endereco))
                    .build();
        }else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
        }
        return request;
    }


}
