
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Clasa Rapoarte genereaza rapoarte
 * despre carti si imprumuturi.
 */
public class Rapoarte {

    private static final String DIRECTOR = "rapoarte";

    /**
     * Creeaza fisierul unui raport.
     */
    private static BufferedWriter creeazaRaport(
            String numeFisier) throws IOException {

        File director = new File(DIRECTOR);

        if (!director.exists() && !director.mkdirs()) {
            throw new IOException(
                    "Nu s-a putut crea directorul rapoarte!");
        }

        File fisier = new File(director, numeFisier);

        return new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fisier),
                        StandardCharsets.UTF_8));
    }

    /**
     * Genereaza raportul dupa autor.
     */
    public static void raportAutor(
            Biblioteca biblioteca, String autor)
            throws IOException {

        try (BufferedWriter writer =
                     creeazaRaport("raport_autor.txt")) {

            writer.write("RAPORT CARTI DUPA AUTOR");
            writer.newLine();
            writer.write("Autor cautat: " + autor);
            writer.newLine();

            int numar = 0;

            for (Carte carte : biblioteca.getCarti()) {

                if (carte.getAutor().toLowerCase()
                        .contains(autor.toLowerCase())) {

                    writer.write(carte.toString());
                    writer.newLine();
                    numar++;
                }
            }

            writer.write("Total carti gasite: " + numar);
            writer.newLine();
        }

        System.out.println(
                "Raportul dupa autor a fost generat!");
    }

    /**
     * Genereaza raportul dupa categorie.
     */
    public static void raportCategorie(
            Biblioteca biblioteca, String categorie)
            throws IOException {

        try (BufferedWriter writer =
                     creeazaRaport("raport_categorie.txt")) {

            writer.write("RAPORT CARTI DUPA CATEGORIE");
            writer.newLine();
            writer.write("Categorie: " + categorie);
            writer.newLine();

            int numar = 0;

            for (Carte carte : biblioteca.getCarti()) {

                if (carte.getCategorie()
                        .equalsIgnoreCase(categorie)) {

                    writer.write(carte.toString());
                    writer.newLine();
                    numar++;
                }
            }

            writer.write("Total carti gasite: " + numar);
            writer.newLine();
        }

        System.out.println(
                "Raportul dupa categorie a fost generat!");
    }

    /**
     * Genereaza raportul imprumuturilor
     * dintr-o anumita zi.
     */
    public static void raportImprumuturi(
            Biblioteca biblioteca, LocalDate data)
            throws IOException {

        try (BufferedWriter writer =
                     creeazaRaport("raport_imprumuturi.txt")) {

            writer.write("RAPORT IMPRUMUTURI");
            writer.newLine();
            writer.write("Data: " + data);
            writer.newLine();

            int numar = 0;

            for (Imprumut imprumut :
                    biblioteca.getImprumuturi().values()) {

                if (imprumut.getDataImprumut().equals(data)) {

                    writer.write(imprumut.toString());
                    writer.newLine();
                    numar++;
                }
            }

            writer.write("Total imprumuturi: " + numar);
            writer.newLine();
        }

        System.out.println(
                "Raportul imprumuturilor a fost generat!");
    }

    /**
     * Genereaza raportul general al bibliotecii.
     * Foloseste un vector si o matrice.
     */
    public static void raportGeneral(
            Biblioteca biblioteca) throws IOException {

        ArrayList<Carte> carti = biblioteca.getCarti();

        int disponibile = 0;
        int imprumutate = 0;

        int[] perioade = new int[4];

        HashSet<String> categoriiSet = new HashSet<>();

        for (Carte carte : carti) {

            categoriiSet.add(carte.getCategorie());

            if (carte.isDisponibila()) {
                disponibile++;
            } else {
                imprumutate++;
            }

            int an = carte.getAnPublicare();

            if (an < 1900) {
                perioade[0]++;
            } else if (an < 1950) {
                perioade[1]++;
            } else if (an < 2000) {
                perioade[2]++;
            } else {
                perioade[3]++;
            }
        }

        ArrayList<String> categorii =
                new ArrayList<>(categoriiSet);

        Collections.sort(categorii);

        int[][] matrice =
                new int[categorii.size()][2];

        for (Carte carte : carti) {

            int index = categorii.indexOf(
                    carte.getCategorie());

            if (carte.isDisponibila()) {
                matrice[index][0]++;
            } else {
                matrice[index][1]++;
            }
        }

        try (BufferedWriter writer =
                     creeazaRaport("raport_general.txt")) {

            writer.write("RAPORT GENERAL BIBLIOTECA");
            writer.newLine();
            writer.write("=========================");
            writer.newLine();

            writer.write("Total carti: " + carti.size());
            writer.newLine();

            writer.write("Carti disponibile: " + disponibile);
            writer.newLine();

            writer.write("Carti imprumutate: " + imprumutate);
            writer.newLine();

            writer.newLine();
            writer.write("CARTI PE PERIOADE");
            writer.newLine();

            String[] denumiri = {
                    "Inainte de 1900",
                    "1900-1949",
                    "1950-1999",
                    "2000-prezent"
            };

            for (int i = 0; i < perioade.length; i++) {
                writer.write(
                        denumiri[i] + ": " + perioade[i]);
                writer.newLine();
            }

            writer.newLine();
            writer.write("CARTI PE CATEGORII");
            writer.newLine();

            for (int i = 0; i < categorii.size(); i++) {

                writer.write(
                        categorii.get(i) +
                                " | Disponibile: " + matrice[i][0] +
                                " | Imprumutate: " + matrice[i][1]);

                writer.newLine();
            }

            writer.newLine();

            writer.write(
                    "Total imprumuturi inregistrate: " +
                            biblioteca.getImprumuturi().size());

            writer.newLine();
        }

        System.out.println(
                "Raportul general a fost generat!");
    }
}
