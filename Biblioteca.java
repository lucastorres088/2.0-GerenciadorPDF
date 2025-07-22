import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Biblioteca {
    private String pathBiblioteca;
    private final String arquivoPersistencia = "biblioteca.txt";

    public void inicializar(Scanner scanner) {
        pathBiblioteca = Persistencia.lerCaminho(arquivoPersistencia);
        if (pathBiblioteca == null || !Files.exists(Paths.get(pathBiblioteca))) {
            pathBiblioteca = criarNovaBiblioteca(scanner);
        }
    }

    public void adicionarPDF(Scanner scanner) throws ExcecaoBiblioteca {
        System.out.print("Tipo (livro, nota, slide): ");
        String tipo = scanner.nextLine().toLowerCase();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Caminho do PDF original: ");
        String origem = scanner.nextLine();

        Path origemPath = Paths.get(origem);

        if (!Files.exists(origemPath) || Files.isDirectory(origemPath) || !origem.toLowerCase().endsWith(".pdf")) {
        throw new ExcecaoBiblioteca("O caminho informado não é um arquivo PDF válido.");
}

        PDFEntry entry;

        switch (tipo.toLowerCase()) {
        case "livro":
            entry = new Livro(autor, titulo, origem);
            break;
        case "nota":
            entry = new NotaAula(autor, titulo, origem);
            break;
        case "slide":
            entry = new Slide(autor, titulo, origem);
            break;
        default:
            throw new ExcecaoBiblioteca("Tipo inválido.");
}


        Path destino = Paths.get(pathBiblioteca, tipo, autor, titulo + ".pdf");
        try {
            Files.createDirectories(destino.getParent());
            Files.copy(Paths.get(origem), destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("PDF adicionado em: " + destino);
        } catch (IOException e) {
            throw new ExcecaoBiblioteca("Erro ao copiar arquivo.");
        }
    }

    public void listarPDFs() throws ExcecaoBiblioteca {
        try {
            Files.walk(Paths.get(pathBiblioteca))
                .filter(p -> p.toString().endsWith(".pdf"))
                .forEach(System.out::println);
        } catch (IOException e) {
            throw new ExcecaoBiblioteca("Erro ao listar PDFs.");
        }
    }

    public void deletarPDF(Scanner scanner) throws ExcecaoBiblioteca {
        System.out.print("Caminho completo do PDF para deletar: ");
        String caminho = scanner.nextLine();
        try {
            Files.deleteIfExists(Paths.get(caminho));
            System.out.println("Arquivo deletado.");
        } catch (IOException e) {
            throw new ExcecaoBiblioteca("Erro ao deletar.");
        }
    }

    public void trocarBiblioteca(Scanner scanner) {
        this.pathBiblioteca = criarNovaBiblioteca(scanner);
    }

    private String criarNovaBiblioteca(Scanner scanner) {
        System.out.print("Novo caminho da biblioteca: ");
        String novoPath = scanner.nextLine();
        try {
            Files.createDirectories(Paths.get(novoPath));
            Persistencia.salvarCaminho(arquivoPersistencia, novoPath);
            return novoPath;
        } catch (IOException e) {
            System.out.println("Erro ao criar diretório.");
            return this.pathBiblioteca;
        }
    }

    public PDFEntry buscarPorTitulo(String titulo) {
    try {
        List<PDFEntry> resultados = new ArrayList<>();
        Files.walk(Paths.get(pathBiblioteca))
            .filter(p -> p.toString().endsWith(".pdf") && p.getFileName().toString().startsWith(titulo))
            .forEach(p -> resultados.add(new Livro("AutorDesconhecido", titulo, "caminho/desconhecido.pdf")));

        return resultados.isEmpty() ? null : resultados.get(0);
    } catch (IOException e) {
        return null;
    }
}
}
