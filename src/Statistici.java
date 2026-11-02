
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Clasa Statistici calculeaza informatii
 * despre cartile din biblioteca.
 */
public class Statistici {

    /**
     * Afiseaza statisticile bibliotecii.
     *
     * @param biblioteca biblioteca analizata
     */
    public static void afiseazaStatistici(
            Biblioteca biblioteca) {

        ArrayList<Carte> carti = biblioteca.getCarti();

        int disponibile = 0;
        int imprumutate = 0;

        // Vector cu numarul cartilor pe perioade
        int[] cartiPePerioade = new int[4];

        for (Carte carte : carti) {

            if (carte.isDisponibila()) {
                disponibile++;
            } else {
                imprumutate++;
            }

            int an = carte.getAnPublicare();

            if (an < 1900) {
                cartiPePerioade[0]++;
            } else if (an < 1950) {
                cartiPePerioade[1]++;
            } else if (an < 2000) {
                cartiPePerioade[2]++;
            } else {
                cartiPePerioade[3]++;
            }
        }

        System.out.println(
                "\n===== STATISTICI BIBLIOTECA =====");

        System.out.println("Total carti: " + carti.size());
        System.out.println("Disponibile: " + disponibile);
        System.out.println("Imprumutate: " + imprumutate);

        System.out.println("\nCARTI PE PERIOADE:");

        String[] perioade = {
                "Inainte de 1900",
                "1900-1949",
                "1950-1999",
                "2000-prezent"
        };

        for (int i = 0; i < cartiPePerioade.length; i++) {
            System.out.println(
                    perioade[i] + ": " + cartiPePerioade[i]);
        }

        // Identificam categoriile distincte
        HashSet<String> categoriiSet = new HashSet<>();

        for (Carte carte : carti) {
            categoriiSet.add(carte.getCategorie());
        }

        ArrayList<String> categorii =
                new ArrayList<>(categoriiSet);

        Collections.sort(categorii);

        // Matrice:
        // coloana 0 = carti disponibile
        // coloana 1 = carti imprumutate
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

        System.out.println("\nCARTI PE CATEGORII:");

        for (int i = 0; i < categorii.size(); i++) {

            System.out.println(
                    "Categorie: " + categorii.get(i));

            System.out.println(
                    "Disponibile: " + matrice[i][0]);

            System.out.println(
                    "Imprumutate: " + matrice[i][1]);

            System.out.println(
                    "Total: " +
                            (matrice[i][0] + matrice[i][1]));

            System.out.println("-------------------");
        }
    }
}
