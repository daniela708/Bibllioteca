
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import java.io.IOException;

/**
 * Clasa principala a aplicatiei Biblioteca.
 * Permite utilizatorului sa interactioneze
 * cu biblioteca prin intermediul unui meniu.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        try {
            GestionareFisiere.incarcaDate(biblioteca);
        } catch (IOException e) {
            System.out.println(
                    "Eroare la incarcarea datelor: " + e.getMessage()
            );
            scanner.close();
            return;
        }




        int optiune;

        do {
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
            System.out.println("0. Iesire");
            System.out.print("\nAlegeti optiunea: ");

            try {
                optiune = Integer.parseInt(
                        scanner.nextLine().trim()
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Eroare: introduceti un numar!"
                );
                optiune = -1;
            }

            try {
                switch (optiune) {

                    case 1:
                        System.out.println("\n--- ADAUGARE CARTE ---");

                        System.out.print("ID carte: ");
                        int id = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.print("Titlu: ");
                        String titlu = scanner.nextLine();

                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();

                        System.out.print("An publicare: ");
                        int an = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.print("Categorie: ");
                        String categorie = scanner.nextLine();

                        Carte carteNoua = new Carte(
                                id, titlu, autor, an, categorie
                        );

                        biblioteca.adaugaCarte(carteNoua);
                        break;

                    case 2:
                        System.out.println("\n--- TOATE CARTILE ---");
                        biblioteca.afiseazaCarti();
                        break;

                    case 3:
                        System.out.print("Introduceti titlul: ");
                        String titluCautat = scanner.nextLine();

                        biblioteca.cautaDupaTitlu(titluCautat);
                        break;

                    case 4:
                        System.out.print("Introduceti autorul: ");
                        String autorCautat = scanner.nextLine();

                        biblioteca.cautaDupaAutor(autorCautat);
                        break;

                    case 5:
                        System.out.print("Introduceti categoria: ");
                        String categorieCautata = scanner.nextLine();

                        biblioteca.cautaDupaCategorie(
                                categorieCautata
                        );
                        break;

                    case 6:
                        System.out.println("\n--- CARTI DISPONIBILE ---");
                        biblioteca.afiseazaCartiDisponibile();
                        break;

                    case 7:
                        System.out.println("\n--- CARTI IMPRUMUTATE ---");
                        biblioteca.afiseazaCartiImprumutate();
                        break;

                    case 8:
                        System.out.println("\n--- IMPRUMUT CARTE ---");

                        System.out.print("ID imprumut: ");
                        int idImprumut = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.print("ID carte: ");
                        int idCarte = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.print("Nume cititor: ");
                        String numeCititor = scanner.nextLine();

                        System.out.print(
                                "Data imprumut (AAAA-LL-ZZ): "
                        );
                        LocalDate dataImprumut = LocalDate.parse(
                                scanner.nextLine().trim()
                        );

                        biblioteca.imprumutaCarte(
                                idImprumut,
                                idCarte,
                                numeCititor,
                                dataImprumut
                        );
                        break;

                    case 9:
                        System.out.println("\n--- RETURNARE CARTE ---");

                        System.out.print("ID imprumut: ");
                        int idReturnare = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.print(
                                "Data returnare (AAAA-LL-ZZ): "
                        );
                        LocalDate dataReturnare = LocalDate.parse(
                                scanner.nextLine().trim()
                        );

                        biblioteca.returneazaCarte(
                                idReturnare, dataReturnare
                        );
                        break;

                    case 10:
                        System.out.print(
                                "Data cautata (AAAA-LL-ZZ): "
                        );

                        LocalDate dataCautata = LocalDate.parse(
                                scanner.nextLine().trim()
                        );

                        biblioteca.cautaImprumuturiDupaData(
                                dataCautata
                        );
                        break;

                    case 11:
                        System.out.println("\n--- TOATE IMPRUMUTURILE ---");
                        biblioteca.afiseazaImprumuturi();
                        break;

                    case 12:
                        Statistici.afiseazaStatistici(biblioteca);
                        break;

                    case 13:
                        System.out.println("\n===== GENERARE RAPOARTE =====");
                        System.out.println("1. Raport dupa autor");
                        System.out.println("2. Raport dupa categorie");
                        System.out.println("3. Raport imprumuturi dupa data");
                        System.out.println("4. Raport general");

                        System.out.print("Alegeti tipul raportului: ");
                        int tipRaport = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        try {
                            switch (tipRaport) {

                                case 1:
                                    System.out.print("Introduceti autorul: ");
                                    String autorRaport = scanner.nextLine();

                                    Rapoarte.raportAutor(
                                            biblioteca, autorRaport
                                    );
                                    break;

                                case 2:
                                    System.out.print("Introduceti categoria: ");
                                    String categorieRaport = scanner.nextLine();

                                    Rapoarte.raportCategorie(
                                            biblioteca, categorieRaport
                                    );
                                    break;

                                case 3:
                                    System.out.print(
                                            "Introduceti data (AAAA-LL-ZZ): "
                                    );

                                    LocalDate dataRaport = LocalDate.parse(
                                            scanner.nextLine().trim()
                                    );

                                    Rapoarte.raportImprumuturi(
                                            biblioteca, dataRaport
                                    );
                                    break;

                                case 4:
                                    Rapoarte.raportGeneral(biblioteca);
                                    break;

                                default:
                                    System.out.println(
                                            "Tip de raport invalid!"
                                    );
                            }

                        } catch (IOException e) {
                            System.out.println(
                                    "Eroare la generarea raportului: " +
                                            e.getMessage()
                            );
                        }
                        break;


                    case 14:
                        System.out.println("\n--- MODIFICARE CARTE ---");

                        System.out.print("Introduceti ID-ul cartii: ");
                        int idModificare = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        Carte carteExistenta =
                                biblioteca.gasesteCarteDupaId(idModificare);

                        if (carteExistenta == null) {
                            System.out.println(
                                    "Nu exista o carte cu acest ID!"
                            );
                            break;
                        }

                        System.out.println(
                                "Cartea actuala: " + carteExistenta
                        );

                        System.out.print("Noul titlu: ");
                        String titluNou = scanner.nextLine();

                        System.out.print("Noul autor: ");
                        String autorNou = scanner.nextLine();

                        System.out.print("Noul an de publicare: ");
                        int anNou = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.print("Noua categorie: ");
                        String categorieNoua = scanner.nextLine();

                        biblioteca.modificaCarte(
                                idModificare,
                                titluNou,
                                autorNou,
                                anNou,
                                categorieNoua
                        );

                        break;

                    case 15:
                        System.out.println("\n--- STERGERE CARTE ---");

                        System.out.print("Introduceti ID-ul cartii: ");
                        int idStergere = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        Carte carteDeSters =
                                biblioteca.gasesteCarteDupaId(idStergere);

                        if (carteDeSters == null) {
                            System.out.println(
                                    "Nu exista o carte cu acest ID!"
                            );
                            break;
                        }

                        System.out.println(
                                "Cartea selectata: " + carteDeSters
                        );

                        System.out.print(
                                "Sigur doriti sa stergeti cartea? (da/nu): "
                        );

                        String confirmare = scanner.nextLine().trim();

                        if (confirmare.equalsIgnoreCase("da")) {

                            biblioteca.stergeCarte(idStergere);

                        } else {
                            System.out.println(
                                    "Stergerea a fost anulata."
                            );
                        }

                        break;

                    case 0:
                        try {
                            GestionareFisiere.salveazaDate(biblioteca);
                            System.out.println(
                                    "Aplicatia se inchide. La revedere!"
                            );
                        } catch (IOException e) {
                            System.out.println(
                                    "Eroare la salvare: " + e.getMessage()
                            );
                        }
                        break;

                    default:
                        System.out.println(
                                "Optiune invalida! Incercati din nou."
                        );
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Eroare: trebuie sa introduceti un numar valid!"
                );

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Eroare: data trebuie sa fie in formatul AAAA-LL-ZZ!"
                );

            } catch (IllegalArgumentException |
                     IllegalStateException e) {
                System.out.println("Eroare: " + e.getMessage());
            }

        } while (optiune != 0);

        scanner.close();
    }
}
