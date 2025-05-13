public class NotaAula extends PDFEntry {
    public NotaAula(String autor, String titulo) {
        super(autor, titulo);
    }

    @Override
    public String getTipo() {
        return "Nota de Aula";
    }
}
