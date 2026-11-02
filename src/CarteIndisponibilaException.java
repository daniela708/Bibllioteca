
/**
 * Exceptie personalizata pentru situatiile
 * in care o carte nu este disponibila
 * pentru imprumut.
 */
public class CarteIndisponibilaException
        extends IllegalStateException {

    /**
     * Constructorul exceptiei.
     *
     * @param mesaj descrierea erorii
     */
    public CarteIndisponibilaException(String mesaj) {
        super(mesaj);
    }
}
