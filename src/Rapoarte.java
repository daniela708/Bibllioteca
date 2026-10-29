
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Clasa Rapoarte genereaza rapoarte text
 * pe baza informatiilor din biblioteca.
 */
public class Rapoarte {

    private static final Path DIRECTOR =
            Path.of("rapoarte");

    /**
     * Creeaza un fisier pentru un raport.
     */
    private static BufferedWriter creeazaRaport(
            String numeFisier) throws IOException {

        Files.createDirectories(DIRECTOR);

        return Files.newBufferedWriter(
                DIRECTOR.resolve(numeFisier),
                StandardCharsets.UTF_8
        );
    }

    /**
     * Genereaza raportul cartilor unui autor.
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
            writer.write("-----------------------------");
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

            writer.write("-----------------------------");
            writer.newLine();
            writer.write("Total carti gasite: " + numar);
        }

        System.out.println(
                "Raportul dupa autor a fost generat!"
        );
    }

    /**
     * Genereaza raportul cartilor dintr-o categorie.
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
            writer.write("-----------------------------");
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

            writer.write("-----------------------------");
            writer.newLine();
            writer.write("Total carti gasite: " + numar);
        }

        System.out.println(
                "Raportul dupa categorie a fost generat!"
        );
    }

    /**
     * Genereaza raportul imprumuturilor
     * efectuate la o anumita data.
     */
    public static void raportImprumuturi(
            Biblioteca biblioteca, LocalDate data)
            throws IOException {

        try (BufferedWriter writer =
                     creeazaRaport("raport_imprumuturi.txt")) {

            writer.write("RAPORT IMPRUMUTURI");
            writer.newLine();
            writer.write("Data cautata: " + data);
            writer.newLine();
            writer.write("-----------------------------");
            writer.newLine();

            int numar = 0;

            for (Imprumut imprumut :
                    biblioteca.getImprumuturi().values()) {

                if (imprumut.getDataImprumut()
                        .equals(data)) {

                    writer.write(imprumut.toString());
                    writer.newLine();
                    numar++;
                }
            }

            writer.write("-----------------------------");
            writer.newLine();
            writer.write("Total imprumuturi: " + numar);
        }

        System.out.println(
                "Raportul imprumuturilor a fost generat!"
        );
    }

    /**
     * Genereaza un raport general al bibliotecii.
     * Utilizeaza un vector si o matrice de tip int.
     */
    public static void raportGeneral(
            Biblioteca biblioteca) throws IOException {

        ArrayList<Carte> carti = biblioteca.getCarti();

        // Vector pentru cartile pe perioade
        int[] perioade = new int[4];

        // Categorii distincte, ordonate
        TreeSet<String> categorii = new TreeSet<>();

        int disponibile = 0;
        int imprumutate = 0;

        for (Carte carte : carti) {

            categorii.add(carte.getCategorie());

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

        ArrayList<String> listaCategorii =
                new ArrayList<>(categorii);

        // Matrice: disponibile / imprumutate
        int[][] matrice =
                new int[listaCategorii.size()][2];

        for (Carte carte : carti) {
            int index = listaCategorii.indexOf(
                    carte.getCategorie()
            );

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
            writer.write("============================");
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

            writer.write("Inainte de 1900: " + perioade[0]);
            writer.newLine();

            writer.write("1900-1949: " + perioade[1]);
            writer.newLine();

            writer.write("1950-1999: " + perioade[2]);
            writer.newLine();

            writer.write("2000-prezent: " + perioade[3]);
            writer.newLine();

            writer.newLine();
            writer.write("CARTI PE CATEGORII");
            writer.newLine();

            for (int i = 0; i < listaCategorii.size(); i++) {
                writer.write(
                        listaCategorii.get(i) +
                                " | Disponibile: " + matrice[i][0] +
                                " | Imprumutate: " + matrice[i][1]
                );
                writer.newLine();
            }

            writer.newLine();
            writer.write(
                    "Total imprumuturi inregistrate: " +
                            biblioteca.getImprumuturi().size()
            );
        }

        System.out.println(
                "Raportul general a fost generat!"
        );
    }
}
