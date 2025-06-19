import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.inicializar(scanner);

        ColecaoManager colecaoManager = new ColecaoManager();

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Adicionar PDF");
            System.out.println("2 - Listar PDFs");
            System.out.println("3 - Deletar PDF");
            System.out.println("4 - Mudar Biblioteca");
            System.out.println("5 - Gerenciar Coleções");
            System.out.println("6 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1": biblioteca.adicionarPDF(scanner); break;
                    case "2": biblioteca.listarPDFs(); break;
                    case "3": biblioteca.deletarPDF(scanner); break;
                    case "4": biblioteca.trocarBiblioteca(scanner); break;
                    case "5": menuColecoes(scanner, biblioteca, colecaoManager); break;
                    case "6": System.exit(0);
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void menuColecoes(Scanner scanner, Biblioteca biblioteca, ColecaoManager colecaoManager) {
        while (true) {
            System.out.println("\n--- MENU DE COLEÇÕES ---");
            System.out.println("1 - Criar coleção");
            System.out.println("2 - Adicionar entrada à coleção");
            System.out.println("3 - Remover entrada da coleção");
            System.out.println("4 - Listar coleções");
            System.out.println("5 - Gerar BibTeX");
            System.out.println("6 - Empacotar coleção (.zip)");
            System.out.println("7 - Voltar");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1":
                        colecaoManager.criarColecaoViaUsuario(scanner, biblioteca);
                        break;
                    case "2":
                        colecaoManager.adicionarEntradasViaUsuario(scanner, biblioteca);
                        break;
                    case "3":
                        colecaoManager.removerEntradaViaUsuario(scanner, biblioteca);
                        break;
                    case "4":
                        colecaoManager.listarColecoes();
                        break;
                    case "5":
                        colecaoManager.gerarBibTexViaUsuario(scanner);
                        break;
                    case "6":
                        colecaoManager.empacotarColecaoViaUsuario(scanner);
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
