
import java.time.LocalDate;

/**
 * Clasa Imprumut reprezinta un imprumut
 * efectuat de un cititor al bibliotecii.
 *
 * Contine informatii despre cartea imprumutata,
 * cititor, data imprumutului si data returnarii.
 */
public class Imprumut {

    // Atributele clasei
    private int idImprumut;
    private int idCarte;
    private String numeCititor;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;

    /**
     * Constructor pentru inregistrarea unui imprumut.
     *
     * @param idImprumut identificatorul imprumutului
     * @param idCarte identificatorul cartii
     * @param numeCititor numele persoanei care imprumuta
     * @param dataImprumut data efectuarii imprumutului
     * @throws DateInvalideException daca datele sunt invalide
     */
    public Imprumut(int idImprumut, int idCarte,
                    String numeCititor,
                    LocalDate dataImprumut) {

        if (idImprumut <= 0) {
            throw new DateInvalideException(
                    "ID-ul imprumutului trebuie sa fie pozitiv!"
            );
        }

        if (idCarte <= 0) {
            throw new DateInvalideException(
                    "ID-ul cartii trebuie sa fie pozitiv!"
            );
        }

        if (numeCititor == null || numeCititor.isBlank()) {
            throw new DateInvalideException(
                    "Numele cititorului nu poate fi gol!"
            );
        }

        if (numeCititor.contains(";")) {
            throw new DateInvalideException(
                    "Numele cititorului nu poate contine ;"
            );
        }

        if (dataImprumut == null) {
            throw new DateInvalideException(
                    "Data imprumutului este obligatorie!"
            );
        }

        this.idImprumut = idImprumut;
        this.idCarte = idCarte;
        this.numeCititor = numeCititor.trim();
        this.dataImprumut = dataImprumut;
        this.dataReturnare = null;
    }

    /**
     * Constructor utilizat la incarcarea
     * imprumuturilor din fisiere.
     *
     * Permite restaurarea datei returnarii.
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

    // Metode GET

    /**
     * Returneaza ID-ul imprumutului.
     */
    public int getIdImprumut() {
        return idImprumut;
    }

    /**
     * Returneaza ID-ul cartii.
     */
    public int getIdCarte() {
        return idCarte;
    }

    /**
     * Returneaza numele cititorului.
     */
    public String getNumeCititor() {
        return numeCititor;
    }

    /**
     * Returneaza data imprumutului.
     */
    public LocalDate getDataImprumut() {
        return dataImprumut;
    }

    /**
     * Returneaza data returnarii.
     */
    public LocalDate getDataReturnare() {
        return dataReturnare;
    }

    /**
     * Inregistreaza returnarea cartii.
     *
     * @param dataReturnare data la care cartea
     *                      este returnata
     * @throws DateInvalideException daca data este invalida
     */
    public void returneazaCarte(LocalDate dataReturnare) {

        if (dataReturnare == null ||
                dataReturnare.isBefore(dataImprumut)) {

            throw new DateInvalideException(
                    "Data returnarii nu poate fi inaintea imprumutului!"
            );
        }

        if (this.dataReturnare != null) {
            throw new IllegalStateException(
                    "Cartea a fost deja returnata!"
            );
        }

        this.dataReturnare = dataReturnare;
    }

    /**
     * Verifica daca imprumutul este activ.
     *
     * @return true daca imprumutul este activ,
     *         false daca a fost returnat
     */
    public boolean esteActiv() {
        return dataReturnare == null;
    }

    /**
     * Afiseaza informatiile despre imprumut.
     *
     * @return informatiile imprumutului sub forma de text
     */
    @Override
    public String toString() {

        return "ID imprumut: " + idImprumut +
                " | ID carte: " + idCarte +
                " | Cititor: " + numeCititor +
                " | Data imprumut: " + dataImprumut +
                " | Data returnare: " +
                (dataReturnare == null
                        ? "Nereturnata"
                        : dataReturnare.toString());
    }
}
