
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

/**
 * Clasa Statistici realizeaza prelucrarea
 * datelor bibliotecii folosind vectori
 * si matrici de tip int.
 */
public class Statistici {

    /**
     * Afiseaza statisticile bibliotecii.
     */
    public static void afiseazaStatistici(
            Biblioteca biblioteca) {

        ArrayList<Carte> carti = biblioteca.getCarti();

        int total = carti.size();
        int disponibile = 0;
        int imprumutate = 0;

        // Numararea cartilor dupa disponibilitate
        for (Carte carte : carti) {
            if (carte.isDisponibila()) {
                disponibile++;
            } else {
                imprumutate++;
            }
        }

        System.out.println("\n===== STATISTICI BIBLIOTECA =====");
        System.out.println("Total carti: " + total);
        System.out.println("Carti disponibile: " + disponibile);
        System.out.println("Carti imprumutate: " + imprumutate);

        // PRIMUL MASIV: vector de tip int[]
        // Grupeaza cartile dupa perioada publicarii
        int[] cartiPePerioade = new int[4];

        for (Carte carte : carti) {
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

        System.out.println("\n--- CARTI PE PERIOADE ---");
        System.out.println("Inainte de 1900: " +
                cartiPePerioade[0]);
        System.out.println("1900-1949: " +
                cartiPePerioade[1]);
        System.out.println("1950-1999: " +
                cartiPePerioade[2]);
        System.out.println("2000-prezent: " +
                cartiPePerioade[3]);

        // Identificam categoriile distincte
        HashSet<String> categoriiSet = new HashSet<>();

        for (Carte carte : carti) {
            categoriiSet.add(carte.getCategorie());
        }

        ArrayList<String> categorii =
                new ArrayList<>(categoriiSet);

        Collections.sort(categorii);

        // AL DOILEA MASIV: matrice de tip int[][]
        // Coloana 0 = disponibile
        // Coloana 1 = imprumutate
        int[][] statisticiCategorii =
                new int[categorii.size()][2];

        for (Carte carte : carti) {

            int index = categorii.indexOf(
                    carte.getCategorie()
            );

            if (carte.isDisponibila()) {
                statisticiCategorii[index][0]++;
            } else {
                statisticiCategorii[index][1]++;
            }
        }

        System.out.println("\n--- CARTI PE CATEGORII ---");

        for (int i = 0; i < categorii.size(); i++) {

            int nrDisponibile =
                    statisticiCategorii[i][0];

            int nrImprumutate =
                    statisticiCategorii[i][1];

            System.out.println(
                    "Categorie: " + categorii.get(i)
            );

            System.out.println(
                    "  Disponibile: " + nrDisponibile
            );

            System.out.println(
                    "  Imprumutate: " + nrImprumutate
            );

            System.out.println(
                    "  Total: " +
                            (nrDisponibile + nrImprumutate)
            );
        }
    }
}
