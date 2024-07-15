package br.com.citrinstar.screenmusic.principal;

import br.com.citrinstar.screenmusic.model.*;
import br.com.citrinstar.screenmusic.repository.AlbumRepository;
import br.com.citrinstar.screenmusic.repository.ArtistaRepository;
import br.com.citrinstar.screenmusic.repository.MusicaRepository;
import br.com.citrinstar.screenmusic.service.ConsumoAPI;
import br.com.citrinstar.screenmusic.service.ConverteDados;

import java.util.*;

public class Principal {

    private Scanner leitor = new Scanner(System.in);
    ConsumoAPI chamadaAPI = new ConsumoAPI();
    ConverteDados converter = new ConverteDados();
    String caminhoChamada = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    private ArtistaRepository artistaRepository;
    private AlbumRepository albumRepository;
    private MusicaRepository musicaRepository;
    private List<Artista> artistas = new ArrayList<>();
    private List<Album> albuns = new ArrayList<>();
    private List<Musica> musicas = new ArrayList<>();

    public Principal(ArtistaRepository repoArtista, AlbumRepository repoAlbum, MusicaRepository repoMusica) {
        this.albumRepository = repoAlbum;
        this.artistaRepository = repoArtista;
        this.musicaRepository = repoMusica;
    }

    public void exibeMenu(){

        popularListas();

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
                cadastrarMusica();
                break;
            case 2:
                cadastrarArtista();
                break;
            case 3:
                listarAlbuns();
                break;
            case 4:
                buscarMusicasPorArtista();
                break;
            case 5:
                consultarResumoArtista();
                break;
            default:
                System.out.println("Opção inválida!");

        }
    }

    private void cadastrarMusica() {
        Artista artista = buscarArtistaDaLista();
        Album album;
        List<Musica> novasMusicas = new ArrayList<>();

        //Informar novo nome de album pra cadastro
        if(artista.getAlbuns().isEmpty()){
            album = criarNovoAlbum(artista);
        }

        //chamar a listagem de albuns, selecionando apenas 1
        System.out.println("Essa é a lista de albuns existente:\n");
        albuns.forEach(System.out::println);
        System.out.println("Selecione uma opção: \n1- Selecionar Album Existente \n2- Adicionar Novo Album");
        var albumMenu = leitor.nextInt();
        leitor.nextLine();

        if(albumMenu == 1){
            album = buscarAlbumDaLista(artista);
        }else {
            album = criarNovoAlbum(artista);
        }

        System.out.println("Cadastro de Música: ");
        var cadastrar = 1;
        while(cadastrar == 1){
            System.out.println("Digite o nome da Musica: ");
            var nomeMusica = leitor.nextLine();
            Musica novaMusica = new Musica(nomeMusica);
            novaMusica.setAlbum(album);
            novasMusicas.add(novaMusica);
            album.addMusica(novaMusica);
            System.out.println("Deseja cadastrar mais uma musica? 1- Sim | 2- Não");
            cadastrar = leitor.nextInt();
            leitor.nextLine();
        }

        albumRepository.save(album);
        musicaRepository.saveAll(novasMusicas);
        musicas.addAll(novasMusicas);
    }

    private void cadastrarArtista() {
        System.out.println("Cadastro de artista: ");
        Set<Artista> novosArtistas = new HashSet<>();
        var cadastrar = 1;
        while(cadastrar == 1){
            System.out.println("Digite o nome do artista ou banda: ");
            String nome = leitor.nextLine();
            System.out.println("Digite o genero musical do artista: ");
            Arrays.stream(Genero.values()).forEach(
                    System.out::println
            );
            String genero = leitor.nextLine();
            novosArtistas.add(new Artista(nome, Genero.fromString(genero)));
            System.out.println("\nDeseja realizar um novo cadastro?: 1 - Sim | 2 - Não");
            cadastrar = leitor.nextInt();
            leitor.nextLine();
        }

        if(!novosArtistas.isEmpty()){
            artistaRepository.saveAll(novosArtistas);
            artistas.addAll(novosArtistas);
        }
    }

    /*TODO: Adicionar verificação se informação do artista já existe no banco
    *  TODO: Se artista não existir, pedir para criar o registro e retornar a consulta de resumo*/
    private void consultarResumoArtista(){
        Artista artistaSobre = new Artista();
        System.out.println("Digite o nome do artista:");
        var nomeArtista = leitor.nextLine();
        Optional<Artista> artistaBuscado = artistaRepository.buscarArtistaPeloNome(nomeArtista);
        if(artistaBuscado.isPresent()){
            artistaSobre = artistaBuscado.get();
        }else {
            System.out.println("Favor cadastrar artista\n");
            cadastrarArtista();
            consultarResumoArtista();
        }

        if(artistaSobre.getSobre() == null){
            artistaSobre.setSobre(buscaResumoPorAPI(nomeArtista));
            artistaRepository.save(artistaSobre);
        }
        System.out.printf("Resumo do artista %s: %s", nomeArtista, artistaSobre.getSobre());
    }

    private String buscaResumoPorAPI(String nomeArtista){
        String request = caminhoChamada + nomeArtista + "?redirect=false";
        String response = chamadaAPI.obterDados(request,
                Optional.of("User-Agent"),
                Optional.of("ScreenMusic/1.0 (lima.stephanielemos@gmail.com)"));
        return converter.obterDadosPathString(response, "extract");
    }

    private Artista buscarArtistaDaLista(){
        List<Artista> artistasList = new ArrayList<>(artistas);
        System.out.println("\nDigite o número do artista que deseja cadastrar a música: ");
        for(int i = 0; i < artistasList.size(); i++){
            System.out.println("Cod: " + i + " | Artista: " + artistasList.get(i).getNome());
        }
        var codArtista = leitor.nextInt();
        leitor.nextLine();
        return artistasList.get(codArtista);
    }

    private Album criarNovoAlbum(Artista artista){
        System.out.println("\nInforme o nome do album que deseja cadastrar as musicas: ");
        var nomeAlbum = leitor.nextLine();
        System.out.println("\nInforme o tipo desse album: ");
        Arrays.stream(TipoAlbum.values())
                .forEach(System.out::println);
        var tipoAlbum = leitor.nextLine();
        Album novoAlbum = new Album(nomeAlbum, artista, TipoAlbum.fromString(tipoAlbum));
        artista.addAlbum(novoAlbum);
        albuns.add(novoAlbum);
        albumRepository.save(novoAlbum);
        return novoAlbum;
    }

    private Album buscarAlbumDaLista(Artista artista){
        System.out.println("Selecione um album existente:\n");
        for(int i = 0; i < artista.getAlbuns().size(); i++){
            System.out.println("Cod: " + i + " | Album: " + artista.getAlbuns().get(i).getNome());
        }
        var albumOpcao = leitor.nextInt();
        leitor.nextLine();
        return artista.getAlbuns().get(albumOpcao);
    }

    private void popularListas(){

        albuns.addAll(albumRepository.findAll());
        artistas.addAll(artistaRepository.findAll());
        musicas.addAll(musicaRepository.findAll());
    }

    private void listarAlbuns(){
        System.out.println("Esses são os albuns disponíveis: ");
        albuns.forEach(a -> System.out.println(
                "Artista: " + a.getArtista().getNome() +
                        "\nNome do Album: " + a.getNome() +
                        "Músicas: " + a.getMusicas()
        ));
    }

    private void buscarMusicasPorArtista(){
        System.out.println("Digite o nome do artista: ");
        String nome = leitor.nextLine();

        List<Musica> musicasDoArtista = musicaRepository.buscarMusicasPorArtista(nome);

        if (!musicasDoArtista.isEmpty()){
            musicasDoArtista.stream()
                    .forEach(musica ->
                            System.out.println(
                                    "Musica: " + musica.getNome() +
                                            " | Album: " + musica.getAlbum().getNome() +
                                            " | Artista: " + musica.getAlbum().getArtista().getNome()));
        }
    }
}
