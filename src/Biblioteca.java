
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clasa Biblioteca gestioneaza cartile si imprumuturile.
 */
public class Biblioteca {

    private ArrayList<Carte> carti;
    private HashMap<Integer, Imprumut> imprumuturi;

    /**
     * Constructorul clasei Biblioteca.
     */
    public Biblioteca() {
        carti = new ArrayList<Carte>();
        imprumuturi = new HashMap<Integer, Imprumut>();
    }

    /**
     * Adauga o carte noua.
     */
    public void adaugaCarte(Carte carte) {

        if (carte == null) {
            throw new DateInvalideException(
                    "Cartea nu poate fi null!");
        }

        if (gasesteCarteDupaId(carte.getId()) != null) {
            throw new DateInvalideException(
                    "Exista deja o carte cu acest ID!");
        }

        carti.add(carte);
        System.out.println("Cartea a fost adaugata!");
    }

    /**
     * Returneaza descrierea tuturor cartilor.
     */
    @Override
    public String toString() {

        StringBuilder rezultat = new StringBuilder();

        for (Carte carte : carti) {
            rezultat.append(carte.toString());
            rezultat.append("\n");
        }

        return rezultat.toString();
    }

    /**
     * Afiseaza toate cartile.
     */
    public void afiseazaCarti() {

        if (carti.isEmpty()) {
            System.out.println("Biblioteca nu contine carti.");
        } else {
            System.out.print(this.toString());
        }
    }

    /**
     * Gaseste o carte dupa ID.
     */
    public Carte gasesteCarteDupaId(int id) {

        for (Carte carte : carti) {
            if (carte.getId() == id) {
                return carte;
            }
        }

        return null;
    }

    /**
     * Cauta carti dupa titlu.
     */
    public void cautaDupaTitlu(String titlu) {

        boolean gasit = false;

        for (Carte carte : carti) {

            if (carte.getTitlu().toLowerCase()
                    .contains(titlu.toLowerCase())) {

                System.out.println(carte);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Nu s-au gasit carti.");
        }
    }

    /**
     * Cauta carti dupa autor.
     */
    public void cautaDupaAutor(String autor) {

        boolean gasit = false;

        for (Carte carte : carti) {

            if (carte.getAutor().toLowerCase()
                    .contains(autor.toLowerCase())) {

                System.out.println(carte);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Nu s-au gasit carti.");
        }
    }

    /**
     * Cauta carti dupa categorie.
     */
    public void cautaDupaCategorie(String categorie) {

        boolean gasit = false;

        for (Carte carte : carti) {

            if (carte.getCategorie()
                    .equalsIgnoreCase(categorie)) {

                System.out.println(carte);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println(
                    "Nu exista carti in aceasta categorie.");
        }
    }

    /**
     * Afiseaza cartile disponibile.
     */
    public void afiseazaCartiDisponibile() {

        for (Carte carte : carti) {

            if (carte.isDisponibila()) {
                System.out.println(carte);
            }
        }
    }

    /**
     * Afiseaza cartile imprumutate.
     */
    public void afiseazaCartiImprumutate() {

        for (Carte carte : carti) {

            if (!carte.isDisponibila()) {
                System.out.println(carte);
            }
        }
    }

    /**
     * Inregistreaza un imprumut.
     */
    public void imprumutaCarte(int idImprumut,
                               int idCarte,
                               String numeCititor,
                               LocalDate data) {

        Carte carte = gasesteCarteDupaId(idCarte);

        if (carte == null) {
            throw new DateInvalideException(
                    "Cartea nu exista!");
        }

        if (!carte.isDisponibila()) {
            throw new CarteIndisponibilaException(
                    "Cartea este deja imprumutata!");
        }

        if (imprumuturi.containsKey(idImprumut)) {
            throw new DateInvalideException(
                    "ID-ul imprumutului exista deja!");
        }

        Imprumut imprumut = new Imprumut(
                idImprumut, idCarte, numeCititor, data);

        imprumuturi.put(idImprumut, imprumut);
        carte.setDisponibila(false);

        System.out.println("Cartea a fost imprumutata!");
    }

    /**
     * Inregistreaza returnarea unei carti.
     */
    public void returneazaCarte(int idImprumut,
                                LocalDate dataReturnare) {

        Imprumut imprumut = imprumuturi.get(idImprumut);

        if (imprumut == null) {
            throw new DateInvalideException(
                    "Imprumutul nu exista!");
        }

        if (!imprumut.esteActiv()) {
            throw new IllegalStateException(
                    "Cartea a fost deja returnata!");
        }

        Carte carte = gasesteCarteDupaId(
                imprumut.getIdCarte());

        if (carte == null) {
            throw new IllegalStateException(
                    "Cartea asociata nu exista!");
        }

        imprumut.returneazaCarte(dataReturnare);
        carte.setDisponibila(true);

        System.out.println("Cartea a fost returnata!");
    }

    /**
     * Cauta imprumuturi dupa data.
     */
    public void cautaImprumuturiDupaData(LocalDate data) {

        boolean gasit = false;

        for (Imprumut imprumut : imprumuturi.values()) {

            if (imprumut.getDataImprumut().equals(data)) {
                System.out.println(imprumut);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println(
                    "Nu exista imprumuturi la aceasta data.");
        }
    }

    /**
     * Afiseaza toate imprumuturile.
     */
    public void afiseazaImprumuturi() {

        if (imprumuturi.isEmpty()) {
            System.out.println("Nu exista imprumuturi.");
            return;
        }

        for (Imprumut imprumut : imprumuturi.values()) {
            System.out.println(imprumut);
        }
    }

    /**
     * Returneaza o copie a listei de carti.
     */
    public ArrayList<Carte> getCarti() {
        return new ArrayList<Carte>(carti);
    }

    /**
     * Returneaza o copie a colectiei de imprumuturi.
     */
    public HashMap<Integer, Imprumut> getImprumuturi() {
        return new HashMap<Integer, Imprumut>(imprumuturi);
    }

    /**
     * Incarca un imprumut din fisier.
     */
    public void incarcaImprumut(Imprumut imprumut) {

        if (imprumut == null) {
            throw new DateInvalideException(
                    "Imprumut invalid!");
        }

        if (imprumuturi.containsKey(
                imprumut.getIdImprumut())) {

            throw new DateInvalideException(
                    "ID imprumut duplicat!");
        }

        Carte carte = gasesteCarteDupaId(
                imprumut.getIdCarte());

        if (carte == null) {
            throw new DateInvalideException(
                    "Cartea asociata imprumutului nu exista!");
        }

        if (imprumut.esteActiv()
                && !carte.isDisponibila()) {

            throw new IllegalStateException(
                    "Cartea are deja un imprumut activ!");
        }

        imprumuturi.put(
                imprumut.getIdImprumut(), imprumut);

        if (imprumut.esteActiv()) {
            carte.setDisponibila(false);
        }
    }

    /**
     * Modifica datele unei carti.
     */
    public void modificaCarte(int id, String titlu,
                              String autor, int anPublicare,
                              String categorie) {

        Carte carte = gasesteCarteDupaId(id);

        if (carte == null) {
            throw new DateInvalideException(
                    "Nu exista o carte cu ID-ul " + id);
        }

        carte.modificaDate(
                titlu, autor, anPublicare, categorie);

        System.out.println(
                "Cartea a fost modificata cu succes!");
    }

    /**
     * Sterge o carte care nu are imprumuturi
     * inregistrate in istoric.
     */
    public void stergeCarte(int id) {

        Carte carte = gasesteCarteDupaId(id);

        if (carte == null) {
            throw new DateInvalideException(
                    "Nu exista o carte cu ID-ul " + id);
        }

        for (Imprumut imprumut : imprumuturi.values()) {

            if (imprumut.getIdCarte() == id) {
                throw new IllegalStateException(
                        "Cartea nu poate fi stearsa deoarece " +
                                "are imprumuturi inregistrate!");
            }
        }

        carti.remove(carte);

        System.out.println(
                "Cartea a fost stearsa cu succes!");
    }
}
