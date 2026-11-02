
import java.time.LocalDate;

/**
 * Clasa Imprumut reprezinta imprumutarea
 * unei carti de catre un cititor.
 */
public class Imprumut {

    private int idImprumut;
    private int idCarte;
    private String numeCititor;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;

    /**
     * Constructor pentru un imprumut nou.
     */
    public Imprumut(int idImprumut, int idCarte,
                    String numeCititor,
                    LocalDate dataImprumut) {

        if (idImprumut <= 0 || idCarte <= 0) {
            throw new DateInvalideException(
                    "ID-urile trebuie sa fie pozitive!");
        }

        if (numeCititor == null ||
                numeCititor.isBlank()) {
            throw new DateInvalideException(
                    "Numele cititorului nu poate fi gol!");
        }

        if (numeCititor.contains(";")) {
            throw new DateInvalideException(
                    "Numele cititorului nu poate contine ;");
        }

        if (dataImprumut == null) {
            throw new DateInvalideException(
                    "Data imprumutului este obligatorie!");
        }

        this.idImprumut = idImprumut;
        this.idCarte = idCarte;
        this.numeCititor = numeCititor.trim();
        this.dataImprumut = dataImprumut;
        this.dataReturnare = null;
    }

    /**
     * Constructor pentru incarcarea din fisier.
     */
    public Imprumut(int idImprumut, int idCarte,
                    String numeCititor,
                    LocalDate dataImprumut,
                    LocalDate dataReturnare) {

        this(idImprumut, idCarte,
                numeCititor, dataImprumut);

        if (dataReturnare != null) {
            returneazaCarte(dataReturnare);
        }
    }

    public int getIdImprumut() {
        return idImprumut;
    }

    public int getIdCarte() {
        return idCarte;
    }

    public String getNumeCititor() {
        return numeCititor;
    }

    public LocalDate getDataImprumut() {
        return dataImprumut;
    }

    public LocalDate getDataReturnare() {
        return dataReturnare;
    }

    /**
     * Verifica daca imprumutul este activ.
     *
     * @return true daca nu a fost returnata cartea
     */
    public boolean esteActiv() {
        return dataReturnare == null;
    }

    /**
     * Inregistreaza returnarea unei carti.
     *
     * @param dataReturnare data returnarii
     */
    public void returneazaCarte(LocalDate dataReturnare) {

        if (!esteActiv()) {
            throw new IllegalStateException(
                    "Cartea a fost deja returnata!");
        }

        if (dataReturnare == null ||
                dataReturnare.isBefore(dataImprumut)) {
            throw new DateInvalideException(
                    "Data returnarii este invalida!");
        }

        this.dataReturnare = dataReturnare;
    }

    /**
     * Returneaza descrierea imprumutului.
     */
    @Override
    public String toString() {

        StringBuilder rezultat = new StringBuilder();

        rezultat.append("ID imprumut: ")
                .append(idImprumut);

        rezultat.append(" | ID carte: ")
                .append(idCarte);

        rezultat.append(" | Cititor: ")
                .append(numeCititor);

        rezultat.append(" | Data imprumut: ")
                .append(dataImprumut);

        rezultat.append(" | Data returnare: ");

        if (esteActiv()) {
            rezultat.append("Nereturnata");
        } else {
            rezultat.append(dataReturnare);
        }

        return rezultat.toString();
    }
}
