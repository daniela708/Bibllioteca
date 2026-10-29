
/**
 * Exceptie personalizata pentru situatiile
 * in care o carte nu poate fi imprumutata.
 */
public class CarteIndisponibilaException
        extends IllegalStateException {

    public CarteIndisponibilaException(String mesaj) {
        super(mesaj);
    }
}

