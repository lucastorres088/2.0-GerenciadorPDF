public class Slide extends PDFEntry {
    public Slide(String autor, String titulo, String caminhoPDF) {
        super(autor, titulo, caminhoPDF);
    }

    @Override
    public String getTipo() {
        return "Slide";
    }

}
