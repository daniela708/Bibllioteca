
/**
 * Exceptie personalizata pentru date invalide.
 * Este utilizata atunci cand datele introduse
 * nu respecta regulile aplicatiei.
 */
public class DateInvalideException
        extends IllegalArgumentException {

    /**
     * Constructorul exceptiei.
     *
     * @param mesaj descrierea erorii
     */
    public DateInvalideException(String mesaj) {
        super(mesaj);
    }
}
