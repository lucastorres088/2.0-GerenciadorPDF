import java.util.ArrayList;
import java.util.List;

public class Colecao<T extends PDFEntry> {
    private String nome;
    private String tipo;
    private String autor;
    private int capacidadeMaxima;
    private List<T> entradas;

    public Colecao(String nome, String tipo, String autor, int capacidadeMaxima) {
        this.nome = nome;
        this.tipo = tipo;
        this.autor = autor;
        this.capacidadeMaxima = capacidadeMaxima;
        this.entradas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAutor() {
        return autor;
    }

    public List<T> getEntradas() {
        return entradas;
    }

    public void adicionarEntrada(T entrada) throws ExcecaoColecao {
        if (entradas.size() >= capacidadeMaxima) {
            throw new ExcecaoColecao("A coleção atingiu a capacidade máxima.");
        }
        entradas.add(entrada);
    }

    public void removerEntrada(T entrada) {
        entradas.remove(entrada);
    }

    public boolean estaVazia() {
        return entradas.isEmpty();
    }
}
