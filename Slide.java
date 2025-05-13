public class Slide extends PDFEntry {
    public Slide(String autor, String titulo) {
        super(autor, titulo);
    }

    @Override
    public String getTipo() {
        return "Slide";
    }
}
