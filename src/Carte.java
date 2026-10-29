
    public class Carte {

        private int id;
        private String titlu;
        private String autor;
        private int anPublicare;
        private String categorie;
        private boolean disponibila;


        /**
         * Constructorul clasei Carte.
         * Verifica validitatea datelor introduse.
         *
         * @param id identificatorul cartii
         * @param titlu titlul cartii
         * @param autor autorul cartii
         * @param anPublicare anul publicarii
         * @param categorie categoria cartii
         * @throws DateInvalideException daca datele sunt invalide
         */
        public Carte(int id, String titlu, String autor,
                     int anPublicare, String categorie) {

            if (id <= 0) {
                throw new DateInvalideException(
                        "ID-ul cartii trebuie sa fie pozitiv!"
                );
            }

            if (titlu == null || titlu.isBlank()) {
                throw new DateInvalideException(
                        "Titlul cartii nu poate fi gol!"
                );
            }

            if (autor == null || autor.isBlank()) {
                throw new DateInvalideException(
                        "Autorul cartii nu poate fi gol!"
                );
            }

            if (anPublicare < 1450 ||
                    anPublicare > java.time.Year.now().getValue()) {
                throw new DateInvalideException(
                        "Anul publicarii trebuie sa fie intre 1450 si anul curent!"
                );
            }

            if (categorie == null || categorie.isBlank()) {
                throw new DateInvalideException(
                        "Categoria cartii nu poate fi goala!"
                );
            }

            if (titlu.contains(";") ||
                    autor.contains(";") ||
                    categorie.contains(";")) {
                throw new DateInvalideException(
                        "Campurile text nu pot contine caracterul ;"
                );
            }

            this.id = id;
            this.titlu = titlu.trim();
            this.autor = autor.trim();
            this.anPublicare = anPublicare;
            this.categorie = categorie.trim();
            this.disponibila = true;
        }


        public int getId() {
            return id;
        }

        public String getTitlu() {
            return titlu;
        }

        public String getAutor() {
            return autor;
        }

        public int getAnPublicare() {
            return anPublicare;
        }

        public String getCategorie() {
            return categorie;
        }

        public boolean isDisponibila() {
            return disponibila;
        }

        // Metoda pentru modificarea disponibilitatii
        public void setDisponibila(boolean disponibila) {
            this.disponibila = disponibila;
        }

        // Afisarea informatiilor despre carte
        @Override
        public String toString() {
            return "ID: " + id +
                    " | Titlu: " + titlu +
                    " | Autor: " + autor +
                    " | An: " + anPublicare +
                    " | Categorie: " + categorie +
                    " | Stare: " +
                    (disponibila ? "Disponibila" : "Imprumutata");
        }

        /**
         * Modifica informatiile unei carti existente.
         *
         * @param titlu noul titlu
         * @param autor noul autor
         * @param anPublicare noul an de publicare
         * @param categorie noua categorie
         * @throws DateInvalideException daca datele sunt invalide
         */
        public void modificaDate(String titlu, String autor,
                                 int anPublicare, String categorie) {

            if (titlu == null || titlu.isBlank()) {
                throw new DateInvalideException(
                        "Titlul nu poate fi gol!"
                );
            }

            if (autor == null || autor.isBlank()) {
                throw new DateInvalideException(
                        "Autorul nu poate fi gol!"
                );
            }

            if (anPublicare < 1450 ||
                    anPublicare > java.time.Year.now().getValue()) {
                throw new DateInvalideException(
                        "Anul publicarii este invalid!"
                );
            }

            if (categorie == null || categorie.isBlank()) {
                throw new DateInvalideException(
                        "Categoria nu poate fi goala!"
                );
            }

            if (titlu.contains(";") ||
                    autor.contains(";") ||
                    categorie.contains(";")) {
                throw new DateInvalideException(
                        "Campurile text nu pot contine caracterul ;"
                );
            }

            this.titlu = titlu.trim();
            this.autor = autor.trim();
            this.anPublicare = anPublicare;
            this.categorie = categorie.trim();
        }

    }


