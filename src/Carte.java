
import java.time.Year;

/**
 * Clasa Carte reprezinta o carte din biblioteca.
 * Contine informatii despre identificator, titlu,
 * autor, an, categorie si disponibilitate.
 */
public class Carte {

    private int id;
    private String titlu;
    private String autor;
    private int anPublicare;
    private String categorie;
    private boolean disponibila;

    /**
     * Constructor pentru crearea unei carti.
     *
     * @param id identificatorul unic
     * @param titlu titlul cartii
     * @param autor autorul cartii
     * @param anPublicare anul publicarii
     * @param categorie categoria cartii
     */
    public Carte(int id, String titlu, String autor,
                 int anPublicare, String categorie) {

        if (id <= 0) {
            throw new DateInvalideException(
                    "ID-ul cartii trebuie sa fie pozitiv!");
        }

        this.id = id;
        modificaDate(titlu, autor, anPublicare, categorie);
        this.disponibila = true;
    }

    public int getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnPublicare() {
        return anPublicare;
    }

    public String getCategorie() {
        return categorie;
    }

    public boolean isDisponibila() {
        return disponibila;
    }

    public void setDisponibila(boolean disponibila) {
        this.disponibila = disponibila;
    }

    /**
     * Modifica informatiile unei carti.
     * Verifica datele inainte de actualizare.
     */
    public void modificaDate(String titlu, String autor,
                             int anPublicare, String categorie) {

        if (titlu == null || titlu.isBlank()) {
            throw new DateInvalideException(
                    "Titlul cartii nu poate fi gol!");
        }

        if (autor == null || autor.isBlank()) {
            throw new DateInvalideException(
                    "Autorul cartii nu poate fi gol!");
        }

        if (anPublicare < 1450 ||
                anPublicare > Year.now().getValue()) {
            throw new DateInvalideException(
                    "Anul publicarii este invalid!");
        }

        if (categorie == null || categorie.isBlank()) {
            throw new DateInvalideException(
                    "Categoria nu poate fi goala!");
        }

        if (titlu.contains(";") || autor.contains(";")
                || categorie.contains(";")) {
            throw new DateInvalideException(
                    "Campurile text nu pot contine ;");
        }

        this.titlu = titlu.trim();
        this.autor = autor.trim();
        this.anPublicare = anPublicare;
        this.categorie = categorie.trim();
    }

    /**
     * Returneaza informatiile cartii sub forma de text.
     *
     * @return descrierea cartii
     */
    @Override
    public String toString() {

        StringBuilder rezultat = new StringBuilder();

        rezultat.append("ID: ").append(id);
        rezultat.append(" | Titlu: ").append(titlu);
        rezultat.append(" | Autor: ").append(autor);
        rezultat.append(" | An: ").append(anPublicare);
        rezultat.append(" | Categorie: ").append(categorie);
        rezultat.append(" | Stare: ");

        if (disponibila) {
            rezultat.append("Disponibila");
        } else {
            rezultat.append("Imprumutata");
        }

        return rezultat.toString();
    }
}
