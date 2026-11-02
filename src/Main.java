
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clasa principala a aplicatiei Biblioteca.
 * Contine meniul si interactiunea cu utilizatorul.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Citeste un numar intreg de la tastatura.
     */
    private static int citesteInt(String mesaj) {
        System.out.print(mesaj);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    /**
     * Citeste un text de la tastatura.
     */
    private static String citesteText(String mesaj) {
        System.out.print(mesaj);
        return scanner.nextLine();
    }

    /**
     * Citeste o data calendaristica.
     */
    private static LocalDate citesteData(String mesaj) {
        System.out.print(mesaj + " (AAAA-LL-ZZ): ");
        return LocalDate.parse(scanner.nextLine().trim());
    }

    /**
     * Afiseaza meniul principal.
     */
    private static void afiseazaMeniu() {
        System.out.println("\n========== MENIU BIBLIOTECA ==========");
        System.out.println("1. Adauga carte");
        System.out.println("2. Afiseaza toate cartile");
        System.out.println("3. Cauta carte dupa titlu");
        System.out.println("4. Cauta carti dupa autor");
        System.out.println("5. Filtreaza carti dupa categorie");
        System.out.println("6. Afiseaza carti disponibile");
        System.out.println("7. Afiseaza carti imprumutate");
        System.out.println("8. Imprumuta carte");
        System.out.println("9. Returneaza carte");
        System.out.println("10. Cauta imprumuturi dupa data");
        System.out.println("11. Afiseaza toate imprumuturile");
        System.out.println("12. Afiseaza statistici biblioteca");
        System.out.println("13. Genereaza rapoarte");
        System.out.println("14. Modifica o carte");
        System.out.println("15. Sterge o carte");
        System.out.println("0. Iesire si salvare");
    }

    /**
     * Adauga o carte in biblioteca.
     */
    private static void adaugaCarte(Biblioteca biblioteca) {
        System.out.println("\n--- ADAUGARE CARTE ---");

        int id = citesteInt("ID carte: ");
        String titlu = citesteText("Titlu: ");
        String autor = citesteText("Autor: ");
        int an = citesteInt("An publicare: ");
        String categorie = citesteText("Categorie: ");

        Carte carte = new Carte(
                id, titlu, autor, an, categorie);

        biblioteca.adaugaCarte(carte);
    }

    /**
     * Inregistreaza imprumutarea unei carti.
     */
    private static void imprumutaCarte(Biblioteca biblioteca) {
        System.out.println("\n--- IMPRUMUT CARTE ---");

        int idImprumut = citesteInt("ID imprumut: ");
        int idCarte = citesteInt("ID carte: ");
        String cititor = citesteText("Nume cititor: ");
        LocalDate data = citesteData("Data imprumut");

        biblioteca.imprumutaCarte(
                idImprumut, idCarte, cititor, data);
    }

    /**
     * Inregistreaza returnarea unei carti.
     */
    private static void returneazaCarte(Biblioteca biblioteca) {
        System.out.println("\n--- RETURNARE CARTE ---");

        int id = citesteInt("ID imprumut: ");
        LocalDate data = citesteData("Data returnare");

        biblioteca.returneazaCarte(id, data);
    }

    /**
     * Modifica informatiile unei carti.
     */
    private static void modificaCarte(Biblioteca biblioteca) {
        System.out.println("\n--- MODIFICARE CARTE ---");

        int id = citesteInt("ID carte: ");

        Carte carte = biblioteca.gasesteCarteDupaId(id);

        if (carte == null) {
            System.out.println("Cartea nu exista!");
            return;
        }

        System.out.println("Cartea actuala: " + carte);

        String titlu = citesteText("Noul titlu: ");
        String autor = citesteText("Noul autor: ");
        int an = citesteInt("Noul an de publicare: ");
        String categorie = citesteText("Noua categorie: ");

        biblioteca.modificaCarte(
                id, titlu, autor, an, categorie);
    }

    /**
     * Sterge o carte dupa confirmarea utilizatorului.
     */
    private static void stergeCarte(Biblioteca biblioteca) {
        System.out.println("\n--- STERGERE CARTE ---");

        int id = citesteInt("ID carte: ");

        Carte carte = biblioteca.gasesteCarteDupaId(id);

        if (carte == null) {
            System.out.println("Cartea nu exista!");
            return;
        }

        System.out.println("Cartea selectata: " + carte);

        String raspuns = citesteText(
                "Confirmati stergerea? (da/nu): ");

        if (raspuns.equalsIgnoreCase("da")) {
            biblioteca.stergeCarte(id);
        } else {
            System.out.println("Stergerea a fost anulata.");
        }
    }

    /**
     * Afiseaza submeniul pentru rapoarte.
     */
    private static void genereazaRapoarte(
            Biblioteca biblioteca) throws IOException {

        System.out.println("\n===== GENERARE RAPOARTE =====");
        System.out.println("1. Raport dupa autor");
        System.out.println("2. Raport dupa categorie");
        System.out.println("3. Raport imprumuturi dupa data");
        System.out.println("4. Raport general");

        int optiune = citesteInt("Alegeti raportul: ");

        switch (optiune) {
            case 1:
                String autor = citesteText("Autor: ");
                Rapoarte.raportAutor(biblioteca, autor);
                break;

            case 2:
                String categorie = citesteText("Categorie: ");
                Rapoarte.raportCategorie(
                        biblioteca, categorie);
                break;

            case 3:
                LocalDate data = citesteData("Data cautata");
                Rapoarte.raportImprumuturi(biblioteca, data);
                break;

            case 4:
                Rapoarte.raportGeneral(biblioteca);
                break;

            default:
                System.out.println("Tip de raport invalid!");
        }
    }

    /**
     * Metoda principala a aplicatiei.
     *
     * @param args argumentele programului
     */
    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        try {
            GestionareFisiere.incarcaDate(biblioteca);
        } catch (IOException e) {
            System.out.println(
                    "Eroare la incarcare: " + e.getMessage());
            scanner.close();
            return;
        }

        int optiune = -1;

        do {
            afiseazaMeniu();

            try {
                optiune = citesteInt("\nAlegeti optiunea: ");

                switch (optiune) {

                    case 1:
                        adaugaCarte(biblioteca);
                        break;

                    case 2:
                        biblioteca.afiseazaCarti();
                        break;

                    case 3:
                        biblioteca.cautaDupaTitlu(
                                citesteText("Titlu: "));
                        break;

                    case 4:
                        biblioteca.cautaDupaAutor(
                                citesteText("Autor: "));
                        break;

                    case 5:
                        biblioteca.cautaDupaCategorie(
                                citesteText("Categorie: "));
                        break;

                    case 6:
                        biblioteca.afiseazaCartiDisponibile();
                        break;

                    case 7:
                        biblioteca.afiseazaCartiImprumutate();
                        break;

                    case 8:
                        imprumutaCarte(biblioteca);
                        break;

                    case 9:
                        returneazaCarte(biblioteca);
                        break;

                    case 10:
                        biblioteca.cautaImprumuturiDupaData(
                                citesteData("Data cautata"));
                        break;

                    case 11:
                        biblioteca.afiseazaImprumuturi();
                        break;

                    case 12:
                        Statistici.afiseazaStatistici(biblioteca);
                        break;

                    case 13:
                        genereazaRapoarte(biblioteca);
                        break;

                    case 14:
                        modificaCarte(biblioteca);
                        break;

                    case 15:
                        stergeCarte(biblioteca);
                        break;

                    case 0:
                        GestionareFisiere.salveazaDate(biblioteca);
                        System.out.println(
                                "Aplicatia se inchide. La revedere!");
                        break;

                    default:
                        System.out.println(
                                "Optiune invalida!");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Eroare: introduceti un numar valid!");

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Eroare: folositi formatul AAAA-LL-ZZ!");

            } catch (IllegalArgumentException |
                     IllegalStateException e) {
                System.out.println(
                        "Eroare: " + e.getMessage());

            } catch (IOException e) {
                System.out.println(
                        "Eroare la fisier: " + e.getMessage());
            }

        } while (optiune != 0);

        scanner.close();
    }
}
