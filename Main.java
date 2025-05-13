import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.inicializar(scanner);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Adicionar PDF");
            System.out.println("2 - Listar PDFs");
            System.out.println("3 - Deletar PDF");
            System.out.println("4 - Mudar Biblioteca");
            System.out.println("5 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1": biblioteca.adicionarPDF(scanner); break;
                    case "2": biblioteca.listarPDFs(); break;
                    case "3": biblioteca.deletarPDF(scanner); break;
                    case "4": biblioteca.trocarBiblioteca(scanner); break;
                    case "5": System.exit(0);
                    default: System.out.println("Opção inválida.");
                }
            } catch (ExcecaoBiblioteca e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
