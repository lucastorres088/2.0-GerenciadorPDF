public class NotaAula extends PDFEntry {
    public NotaAula(String autor, String titulo, String caminhoPDF) {
        super(autor, titulo, caminhoPDF);
    }

    @Override
    public String getTipo() {
        return "Nota de Aula";
    }

}

