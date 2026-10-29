
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Biblioteca {

    // Colectie pentru stocarea cartilor
    private ArrayList<Carte> carti;

    // Colectie pentru stocarea imprumuturilor
    private HashMap<Integer, Imprumut> imprumuturi;

    public Biblioteca() {
        carti = new ArrayList<>();
        imprumuturi = new HashMap<>();
    }

    /**
     * Adauga o carte noua in biblioteca.
     */
    public void adaugaCarte(Carte carte) {
        if (carte == null) {
            throw new IllegalArgumentException(
                    "Cartea nu poate fi null!"
            );
        }

        if (gasesteCarteDupaId(carte.getId()) != null) {
            throw new IllegalArgumentException(
                    "Exista deja o carte cu acest ID!"
            );
        }

        carti.add(carte);
        System.out.println("Cartea a fost adaugata!");
    }

    /**
     * Afiseaza toate cartile.
     */
    public void afiseazaCarti() {
        if (carti.isEmpty()) {
            System.out.println("Biblioteca nu contine carti.");
            return;
        }

        for (Carte carte : carti) {
            System.out.println(carte);
        }
    }

    /**
     * Cauta o carte dupa ID.
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
     * Filtreaza cartile dupa categorie.
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
                    "Nu exista carti in aceasta categorie."
            );
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
     * Inregistreaza imprumutarea unei carti.
     */
    public void imprumutaCarte(int idImprumut,
                               int idCarte,
                               String numeCititor,
                               LocalDate data) {

        Carte carte = gasesteCarteDupaId(idCarte);

        if (carte == null) {
            throw new IllegalArgumentException(
                    "Cartea nu exista!"
            );
        }


        if (!carte.isDisponibila()) {
            throw new CarteIndisponibilaException(
                    "Cartea cu ID-ul " + idCarte +
                            " este deja imprumutata!"
            );
        }


        if (imprumuturi.containsKey(idImprumut)) {
            throw new IllegalArgumentException(
                    "ID-ul imprumutului exista deja!"
            );
        }

        if (numeCititor == null ||
                numeCititor.isBlank() || data == null) {
            throw new IllegalArgumentException(
                    "Datele imprumutului sunt invalide!"
            );
        }

        Imprumut imprumut = new Imprumut(
                idImprumut, idCarte, numeCititor, data
        );

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
            throw new IllegalArgumentException(
                    "Imprumutul nu exista!"
            );
        }

        if (!imprumut.esteActiv()) {
            throw new IllegalStateException(
                    "Cartea a fost deja returnata!"
            );
        }

        Carte carte = gasesteCarteDupaId(
                imprumut.getIdCarte()
        );

        if (carte == null) {
            throw new IllegalStateException(
                    "Cartea asociata nu exista!"
            );
        }

        imprumut.returneazaCarte(dataReturnare);
        carte.setDisponibila(true);

        System.out.println("Cartea a fost returnata!");
    }

    /**
     * Cauta imprumuturi efectuate la o anumita data.
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
                    "Nu exista imprumuturi la aceasta data."
            );
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
     * Returneaza lista cartilor pentru salvare.
     */
    public ArrayList<Carte> getCarti() {
        return new ArrayList<>(carti);
    }

    /**
     * Returneaza imprumuturile pentru salvare.
     */
    public HashMap<Integer, Imprumut> getImprumuturi() {
        return new HashMap<>(imprumuturi);
    }

    /**
     * Incarca un imprumut salvat anterior.
     */
    public void incarcaImprumut(Imprumut imprumut) {

        if (imprumut == null) {
            throw new IllegalArgumentException(
                    "Imprumut invalid!"
            );
        }

        if (imprumuturi.containsKey(
                imprumut.getIdImprumut())) {
            throw new IllegalArgumentException(
                    "ID imprumut duplicat!"
            );
        }

        Carte carte = gasesteCarteDupaId(
                imprumut.getIdCarte()
        );

        if (carte == null) {
            throw new IllegalArgumentException(
                    "Cartea asociata imprumutului nu exista!"
            );
        }

        // O carte poate avea un singur imprumut activ
        if (imprumut.esteActiv() && !carte.isDisponibila()) {
            throw new IllegalStateException(
                    "Cartea are deja un imprumut activ!"
            );
        }

        imprumuturi.put(
                imprumut.getIdImprumut(), imprumut
        );

        if (imprumut.esteActiv()) {
            carte.setDisponibila(false);
        }
    }

    /**
     * Modifica datele unei carti existente.
     *
     * @param id ID-ul cartii
     * @param titlu noul titlu
     * @param autor noul autor
     * @param anPublicare noul an de publicare
     * @param categorie noua categorie
     */
    public void modificaCarte(int id, String titlu,
                              String autor, int anPublicare,
                              String categorie) {

        Carte carte = gasesteCarteDupaId(id);

        if (carte == null) {
            throw new DateInvalideException(
                    "Nu exista o carte cu ID-ul " + id
            );
        }

        carte.modificaDate(
                titlu, autor, anPublicare, categorie
        );

        System.out.println(
                "Cartea a fost modificata cu succes!"
        );
    }

    /**
     * Sterge o carte daca nu are imprumuturi
     * asociate in istoricul bibliotecii.
     *
     * @param id ID-ul cartii de sters
     */
    public void stergeCarte(int id) {

        Carte carte = gasesteCarteDupaId(id);

        if (carte == null) {
            throw new DateInvalideException(
                    "Nu exista o carte cu ID-ul " + id
            );
        }

        // Verificam daca exista imprumuturi pentru carte
        for (Imprumut imprumut : imprumuturi.values()) {

            if (imprumut.getIdCarte() == id) {
                throw new IllegalStateException(
                        "Cartea nu poate fi stearsa deoarece " +
                                "are imprumuturi inregistrate!"
                );
            }
        }

        carti.remove(carte);

        System.out.println(
                "Cartea a fost stearsa cu succes!"
        );
    }


}

