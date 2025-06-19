import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

interface GerenciadorColecoes {
    void criarColecao(String nome, String tipo, String autor, int capacidade, List<PDFEntry> entradas) throws ExcecaoColecao;
    void adicionarEntradas(String nomeColecao, List<PDFEntry> novasEntradas) throws ExcecaoColecao;
    void removerEntrada(String nomeColecao, PDFEntry entrada) throws ExcecaoColecao;
    void listarColecoes();
    void gerarBibTex(String nomeColecao, String pathDestino) throws IOException, ExcecaoColecao;
    void empacotarColecao(String nomeColecao, String pathDestino, String nomeArquivo) throws IOException, ExcecaoColecao;
}

class ColecaoManager implements GerenciadorColecoes {
    private Map<String, Colecao<? extends PDFEntry>> colecoes = new HashMap<>();

    public void criarColecaoViaUsuario(Scanner scanner, Biblioteca biblioteca) throws ExcecaoColecao {
    System.out.print("Nome da coleção: ");
    String nome = scanner.nextLine();
    System.out.print("Tipo (livro, nota, slide): ");
    String tipo = scanner.nextLine();
    System.out.print("Autor: ");
    String autor = scanner.nextLine();
    System.out.print("Capacidade máxima: ");
    int capacidade = Integer.parseInt(scanner.nextLine());

    List<PDFEntry> entradas = new ArrayList<>();
    System.out.println("Quantas entradas deseja adicionar agora?");
    int qtd = Integer.parseInt(scanner.nextLine());

    for (int i = 0; i < qtd; i++) {
        System.out.print("Título da entrada: ");
        String titulo = scanner.nextLine();
        PDFEntry entrada = biblioteca.buscarPorTitulo(titulo);
        if (entrada != null) entradas.add(entrada);
        else System.out.println("Entrada não encontrada.");
    }

    criarColecao(nome, tipo, autor, capacidade, entradas);
    System.out.println("Coleção criada.");
}

public void adicionarEntradasViaUsuario(Scanner scanner, Biblioteca biblioteca) throws ExcecaoColecao {
    System.out.print("Nome da coleção: ");
    String nome = scanner.nextLine();

    List<PDFEntry> novasEntradas = new ArrayList<>();
    System.out.println("Quantas entradas deseja adicionar?");
    int qtd = Integer.parseInt(scanner.nextLine());

    for (int i = 0; i < qtd; i++) {
        System.out.print("Título da entrada: ");
        String titulo = scanner.nextLine();
        PDFEntry entrada = biblioteca.buscarPorTitulo(titulo);
        if (entrada != null) novasEntradas.add(entrada);
        else System.out.println("Entrada não encontrada.");
    }

    adicionarEntradas(nome, novasEntradas);
}

public void removerEntradaViaUsuario(Scanner scanner, Biblioteca biblioteca) throws ExcecaoColecao {
    System.out.print("Nome da coleção: ");
    String nome = scanner.nextLine();
    System.out.print("Título da entrada a remover: ");
    String titulo = scanner.nextLine();

    PDFEntry entrada = biblioteca.buscarPorTitulo(titulo);
    if (entrada != null) {
        removerEntrada(nome, entrada);
        System.out.println("Entrada removida.");
    } else {
        System.out.println("Entrada não encontrada.");
    }
}

public void gerarBibTexViaUsuario(Scanner scanner) throws Exception {
    System.out.print("Nome da coleção: ");
    String nome = scanner.nextLine();
    System.out.print("Caminho para salvar o arquivo .bib: ");
    String path = scanner.nextLine();
    gerarBibTex(nome, path);
}

public void empacotarColecaoViaUsuario(Scanner scanner) throws Exception {
    System.out.print("Nome da coleção: ");
    String nome = scanner.nextLine();
    System.out.print("Caminho para salvar o .zip: ");
    String path = scanner.nextLine();
    System.out.print("Nome do arquivo .zip: ");
    String nomeZip = scanner.nextLine();
    empacotarColecao(nome, path, nomeZip);
}


    @Override
    public void criarColecao(String nome, String tipo, String autor, int capacidade, List<PDFEntry> entradas) throws ExcecaoColecao {
        if (colecoes.containsKey(nome)) throw new ExcecaoColecao("Coleção já existe.");

        Colecao<PDFEntry> novaColecao = new Colecao<>(nome, tipo, autor, capacidade);
        for (PDFEntry e : entradas) novaColecao.adicionarEntrada(e);

        colecoes.put(nome, novaColecao);
    }

    @Override
    public void adicionarEntradas(String nomeColecao, List<PDFEntry> novasEntradas) throws ExcecaoColecao {
        @SuppressWarnings("unchecked")
Colecao<PDFEntry> colecao = (Colecao<PDFEntry>) colecoes.get(nomeColecao);


        if (colecao == null) throw new ExcecaoColecao("Coleção não encontrada.");

        for (PDFEntry e : novasEntradas) colecao.adicionarEntrada(e);
    }

    @Override
    public void removerEntrada(String nomeColecao, PDFEntry entrada) throws ExcecaoColecao {
        @SuppressWarnings("unchecked")
Colecao<PDFEntry> colecao = (Colecao<PDFEntry>) colecoes.get(nomeColecao);


        if (colecao == null) throw new ExcecaoColecao("Coleção não encontrada.");

        colecao.removerEntrada(entrada);
        if (colecao.estaVazia()) colecoes.remove(nomeColecao);
    }

    @Override
    public void listarColecoes() {
        if (colecoes.isEmpty()) {
            System.out.println("Nenhuma coleção criada.");
            return;
        }
        for (String nome : colecoes.keySet()) {
            @SuppressWarnings("unchecked")
Colecao<PDFEntry> c = (Colecao<PDFEntry>) colecoes.get(nome);
            System.out.println("- " + nome + " | Tipo: " + c.getTipo() + " | Autor: " + c.getAutor() + " | Itens: " + c.getEntradas().size());
        }
    }

    @Override
    public void gerarBibTex(String nomeColecao, String pathDestino) throws IOException, ExcecaoColecao {
        @SuppressWarnings("unchecked")
Colecao<PDFEntry> colecao = (Colecao<PDFEntry>) colecoes.get(nomeColecao);


        if (colecao == null) throw new ExcecaoColecao("Coleção não encontrada.");
        if (!colecao.getTipo().equalsIgnoreCase("livro")) throw new ExcecaoColecao("Só pode gerar BibTex de livros.");

        Path saida = Paths.get(pathDestino, nomeColecao + ".bib");
        try (BufferedWriter writer = Files.newBufferedWriter(saida)) {
            for (PDFEntry e : colecao.getEntradas()) {
                writer.write("@book{" + e.getTitulo().replaceAll("\\s", "_") + ",\n");
                writer.write("  author = {" + e.getAutor() + "},\n");
                writer.write("  title = {" + e.getTitulo() + "},\n");
                writer.write("}\n\n");
            }
        }
        System.out.println("BibTex gerado em: " + saida);
    }

    @Override
    public void empacotarColecao(String nomeColecao, String pathDestino, String nomeArquivo) throws IOException, ExcecaoColecao {
        @SuppressWarnings("unchecked")
Colecao<PDFEntry> colecao = (Colecao<PDFEntry>) colecoes.get(nomeColecao);


        if (colecao == null) throw new ExcecaoColecao("Coleção não encontrada.");

        Path zipPath = Paths.get(pathDestino, nomeArquivo + ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            for (PDFEntry e : colecao.getEntradas()) {
                Path arquivo = Paths.get(e.getCaminhoPDF());
                zos.putNextEntry(new ZipEntry(arquivo.getFileName().toString()));
                Files.copy(arquivo, zos);
                zos.closeEntry();
            }
        }
        System.out.println("Coleção empacotada em: " + zipPath);
    }
}
