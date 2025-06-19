public class Livro extends PDFEntry {
    public Livro(String autor, String titulo, String caminhoPDF) {
        super(autor, titulo, caminhoPDF);
    }

    @Override
    public String getTipo() {
        return "Livro";
    }

}

