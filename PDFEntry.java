public abstract class PDFEntry {
    protected String autor;
    protected String titulo;
    protected String caminhoPDF;

    public PDFEntry(String autor, String titulo, String caminhoPDF) {
        this.autor = autor;
        this.titulo = titulo;
        this.caminhoPDF = caminhoPDF;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCaminhoPDF() {
        return caminhoPDF;
    }

    public abstract String getTipo();
}
