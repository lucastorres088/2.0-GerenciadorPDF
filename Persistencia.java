import java.io.*;

public class Persistencia {
    public static String lerCaminho(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public static void salvarCaminho(String arquivo, String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            bw.write(path);
        } catch (IOException e) {
            System.out.println("Erro ao salvar caminho.");
        }
    }
}
