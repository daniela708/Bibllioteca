
/**
 * Exceptie personalizata pentru date invalide.
 */
public class DateInvalideException
        extends IllegalArgumentException {

    public DateInvalideException(String mesaj) {
        super(mesaj);
    }
}

