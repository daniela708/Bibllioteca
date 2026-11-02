
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clasa pentru citirea si scrierea datelor
 * bibliotecii in fisiere text.
 */
public class GestionareFisiere {

    private static final String FISIER_CARTI = "carti.txt";
    private static final String FISIER_IMPRUMUTURI =
            "imprumuturi.txt";

    /**
     * Salveaza cartile in fisier.
     */
    public static void salveazaCarti(Biblioteca biblioteca)
            throws IOException {

        File fisier = new File(FISIER_CARTI);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fisier),
                        StandardCharsets.UTF_8))) {

            for (Carte carte : biblioteca.getCarti()) {

                StringBuilder linie = new StringBuilder();

                linie.append(carte.getId()).append(";");
                linie.append(carte.getTitlu()).append(";");
                linie.append(carte.getAutor()).append(";");
                linie.append(carte.getAnPublicare()).append(";");
                linie.append(carte.getCategorie()).append(";");
                linie.append(carte.isDisponibila());

                writer.write(linie.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Salveaza imprumuturile in fisier.
     */
    public static void salveazaImprumuturi(
            Biblioteca biblioteca) throws IOException {

        File fisier = new File(FISIER_IMPRUMUTURI);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fisier),
                        StandardCharsets.UTF_8))) {

            ArrayList<Imprumut> lista =
                    new ArrayList<Imprumut>(
                            biblioteca.getImprumuturi().values());

            lista.sort(Comparator.comparingInt(
                    Imprumut::getIdImprumut));

            for (Imprumut imprumut : lista) {

                StringBuilder linie = new StringBuilder();

                linie.append(imprumut.getIdImprumut())
                        .append(";");
                linie.append(imprumut.getIdCarte())
                        .append(";");
                linie.append(imprumut.getNumeCititor())
                        .append(";");
                linie.append(imprumut.getDataImprumut())
                        .append(";");

                if (imprumut.getDataReturnare() != null) {
                    linie.append(imprumut.getDataReturnare());
                }

                writer.write(linie.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Incarca cartile din fisier.
     */
    public static void incarcaCarti(Biblioteca biblioteca)
            throws IOException {

        File fisier = new File(FISIER_CARTI);

        if (!fisier.exists()) {
            System.out.println(
                    "Fisierul carti.txt nu exista.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fisier),
                        StandardCharsets.UTF_8))) {

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
                        throw new DateInvalideException(
                                "Numar invalid de campuri!");
                    }

                    int id = Integer.parseInt(date[0]);
                    String titlu = date[1];
                    String autor = date[2];
                    int an = Integer.parseInt(date[3]);
                    String categorie = date[4];

                    if (!date[5].equals("true")
                            && !date[5].equals("false")) {
                        throw new DateInvalideException(
                                "Disponibilitate invalida!");
                    }

                    Carte carte = new Carte(
                            id, titlu, autor, an, categorie);

                    // Disponibilitatea se stabileste
                    // din imprumuturile active.
                    biblioteca.adaugaCarte(carte);

                } catch (RuntimeException e) {
                    throw new IOException(
                            "Eroare in carti.txt la linia "
                                    + nrLinie + ": "
                                    + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Incarca imprumuturile din fisier.
     */
    public static void incarcaImprumuturi(
            Biblioteca biblioteca) throws IOException {

        File fisier = new File(FISIER_IMPRUMUTURI);

        if (!fisier.exists()) {
            System.out.println(
                    "Fisierul imprumuturi.txt nu exista.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fisier),
                        StandardCharsets.UTF_8))) {

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
                        throw new DateInvalideException(
                                "Numar invalid de campuri!");
                    }

                    int idImprumut =
                            Integer.parseInt(date[0]);

                    int idCarte =
                            Integer.parseInt(date[1]);

                    String numeCititor = date[2];

                    LocalDate dataImprumut =
                            LocalDate.parse(date[3]);

                    LocalDate dataReturnare = null;

                    if (!date[4].isBlank()) {
                        dataReturnare =
                                LocalDate.parse(date[4]);
                    }

                    Imprumut imprumut = new Imprumut(
                            idImprumut, idCarte,
                            numeCititor, dataImprumut,
                            dataReturnare);

                    biblioteca.incarcaImprumut(imprumut);

                } catch (RuntimeException e) {
                    throw new IOException(
                            "Eroare in imprumuturi.txt la linia "
                                    + nrLinie + ": "
                                    + e.getMessage(), e);
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
                "Datele au fost salvate cu succes!");
    }

    /**
     * Incarca datele la pornirea aplicatiei.
     */
    public static void incarcaDate(Biblioteca biblioteca)
            throws IOException {

        incarcaCarti(biblioteca);
        incarcaImprumuturi(biblioteca);

        System.out.println(
                "Incarcarea datelor s-a finalizat!");
    }
}
