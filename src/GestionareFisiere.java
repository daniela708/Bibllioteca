
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clasa pentru salvarea si incarcarea datelor
 * bibliotecii folosind fisiere text.
 */
public class GestionareFisiere {

    private static final String FISIER_CARTI = "carti.txt";
    private static final String FISIER_IMPRUMUTURI =
            "imprumuturi.txt";

    /**
     * Salveaza cartile in fisierul carti.txt.
     */
    public static void salveazaCarti(Biblioteca biblioteca)
            throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(FISIER_CARTI),
                StandardCharsets.UTF_8)) {

            for (Carte carte : biblioteca.getCarti()) {

                writer.write(
                        carte.getId() + ";" +
                                carte.getTitlu() + ";" +
                                carte.getAutor() + ";" +
                                carte.getAnPublicare() + ";" +
                                carte.getCategorie() + ";" +
                                carte.isDisponibila()
                );

                writer.newLine();
            }
        }
    }

    /**
     * Salveaza imprumuturile in fisier.
     */
    public static void salveazaImprumuturi(
            Biblioteca biblioteca) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(FISIER_IMPRUMUTURI),
                StandardCharsets.UTF_8)) {

            ArrayList<Imprumut> lista = new ArrayList<>(
                    biblioteca.getImprumuturi().values()
            );

            lista.sort(Comparator.comparingInt(
                    Imprumut::getIdImprumut
            ));

            for (Imprumut imprumut : lista) {

                writer.write(
                        imprumut.getIdImprumut() + ";" +
                                imprumut.getIdCarte() + ";" +
                                imprumut.getNumeCititor() + ";" +
                                imprumut.getDataImprumut() + ";" +
                                (imprumut.getDataReturnare() == null
                                        ? ""
                                        : imprumut.getDataReturnare())
                );

                writer.newLine();
            }
        }
    }

    /**
     * Incarca toate cartile din fisier.
     */
    public static void incarcaCarti(Biblioteca biblioteca)
            throws IOException {

        Path cale = Path.of(FISIER_CARTI);

        if (!Files.exists(cale)) {
            System.out.println(
                    "Nu exista carti.txt. Biblioteca porneste goala."
            );
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                cale, StandardCharsets.UTF_8)) {

            String linie;
            int nrLinie = 0;

            while ((linie = reader.readLine()) != null) {
                nrLinie++;

                if (linie.isBlank()) {
                    continue;
                }

                try {
                    String[] date = linie.split(";", -1);

                    if (date.length != 6) {
                        throw new IllegalArgumentException(
                                "Numar invalid de campuri"
                        );
                    }

                    int id = Integer.parseInt(date[0]);
                    String titlu = date[1];
                    String autor = date[2];
                    int an = Integer.parseInt(date[3]);
                    String categorie = date[4];

                    if (id <= 0 || an <= 0 ||
                            titlu.isBlank() ||
                            autor.isBlank() ||
                            categorie.isBlank()) {
                        throw new IllegalArgumentException(
                                "Date incomplete sau invalide"
                        );
                    }

                    if (!date[5].equals("true") &&
                            !date[5].equals("false")) {
                        throw new IllegalArgumentException(
                                "Disponibilitate invalida"
                        );
                    }

                    Carte carte = new Carte(
                            id, titlu, autor, an, categorie
                    );

                    // Starea finala va fi verificata
                    // folosind istoricul imprumuturilor.
                    biblioteca.adaugaCarte(carte);

                } catch (RuntimeException e) {
                    throw new IOException(
                            "Eroare in carti.txt, linia " +
                                    nrLinie + ": " + e.getMessage(), e
                    );
                }
            }
        }
    }

    /**
     * Incarca imprumuturile din fisier.
     */
    public static void incarcaImprumuturi(
            Biblioteca biblioteca) throws IOException {

        Path cale = Path.of(FISIER_IMPRUMUTURI);

        if (!Files.exists(cale)) {
            System.out.println(
                    "Nu exista imprumuturi.txt."
            );
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                cale, StandardCharsets.UTF_8)) {

            String linie;
            int nrLinie = 0;

            while ((linie = reader.readLine()) != null) {
                nrLinie++;

                if (linie.isBlank()) {
                    continue;
                }

                try {
                    String[] date = linie.split(";", -1);

                    if (date.length != 5) {
                        throw new IllegalArgumentException(
                                "Numar invalid de campuri"
                        );
                    }

                    int idImprumut =
                            Integer.parseInt(date[0]);
                    int idCarte =
                            Integer.parseInt(date[1]);
                    String numeCititor = date[2];

                    if (idImprumut <= 0 || idCarte <= 0 ||
                            numeCititor.isBlank()) {
                        throw new IllegalArgumentException(
                                "Date imprumut invalide"
                        );
                    }

                    LocalDate dataImprumut =
                            LocalDate.parse(date[3]);

                    LocalDate dataReturnare =
                            date[4].isBlank()
                                    ? null
                                    : LocalDate.parse(date[4]);

                    Imprumut imprumut = new Imprumut(
                            idImprumut, idCarte,
                            numeCititor, dataImprumut,
                            dataReturnare
                    );

                    biblioteca.incarcaImprumut(imprumut);

                } catch (RuntimeException e) {
                    throw new IOException(
                            "Eroare in imprumuturi.txt, linia " +
                                    nrLinie + ": " + e.getMessage(), e
                    );
                }
            }
        }
    }

    /**
     * Salveaza toate datele bibliotecii.
     */
    public static void salveazaDate(Biblioteca biblioteca)
            throws IOException {

        salveazaCarti(biblioteca);
        salveazaImprumuturi(biblioteca);

        System.out.println(
                "Datele au fost salvate cu succes!"
        );
    }

    /**
     * Incarca datele la pornirea aplicatiei.
     */
    public static void incarcaDate(Biblioteca biblioteca)
            throws IOException {

        incarcaCarti(biblioteca);
        incarcaImprumuturi(biblioteca);

        System.out.println(
                "Incarcarea datelor s-a finalizat!"
        );
    }
}
