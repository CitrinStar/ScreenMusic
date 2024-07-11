package br.com.citrinstar.screenmusic.principal;

import br.com.citrinstar.screenmusic.model.Artista;
import br.com.citrinstar.screenmusic.service.ConsumoAPI;
import br.com.citrinstar.screenmusic.service.ConverteDados;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitor = new Scanner(System.in);
    ConsumoAPI chamadaAPI = new ConsumoAPI();
    ConverteDados converter = new ConverteDados();
    String caminhoChamada = "https://en.wikipedia.org/api/rest_v1/page/summary/";

    public void exibeMenu(){

        var opcao = -1;
        System.out.println("""
                Menu:
                
                1- Cadastrar Musica
                2- Cadastrar Artista
                3- Listar Albuns
                4- Buscar Músicas por Artista
                5- Resumo sobre Artista
                
                0- Sair
                
                """);

        opcao = leitor.nextInt();
        leitor.nextLine();

        switch (opcao){
            case 0:
                System.out.println("Saindo");
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                consultarResumoArtista();
                break;
            default:
                System.out.println("Opção inválida!");

        }
    }

    /*TODO: Adicionar verificação se informação do artista já existe no banco
    *  TODO: Se artista não existir, pedir para criar o registro e retornar a consulta de resumo*/
    private void consultarResumoArtista(){
        System.out.println("Digite o nome do artista:");
        var nomeArtista = leitor.nextLine();
        //buscar Artista pelo título
        //se não encontrado, pede pra cadastrar
        //se encontrado, verificar se possui campo sobre preenchido
        //se tiver, exibe informação
        //se não, realiza chamda api e atualiza o artista
        String resumo = buscaResumoPorAPI(nomeArtista);

        System.out.printf("Resumo do artista %s: %s", nomeArtista, resumo);
    }

    private String buscaResumoPorAPI(String nomeArtista){
        String request = caminhoChamada + nomeArtista + "?redirect=false";
        String response = chamadaAPI.obterDados(request,
                Optional.of("User-Agent"),
                Optional.of("ScreenMusic/1.0 (lima.stephanielemos@gmail.com)"));
        return converter.obterDadosPathString(response, "extract");
    }


}
