public class Livro extends PDFEntry {
    public Livro(String autor, String titulo) {
        super(autor, titulo);
    }

    @Override
    public String getTipo() {
        return "Livro";
    }
}
