public abstract class PDFEntry {
    private String autor;
    private String titulo;

    public PDFEntry(String autor, String titulo) {
        this.autor = autor;
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public abstract String getTipo();

    public String getDescricao() {
        return "[" + getTipo() + "] " + titulo + " - " + autor;
    }
}
