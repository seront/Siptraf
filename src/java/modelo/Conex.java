package modelo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author seront
 */
public class Conex {

    public Conex() {
    }

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEmf() {
        if (emf != null) {
            return emf;
        } else {
            emf = Persistence.createEntityManagerFactory("MODULO2.3PU");
            return emf;
        }
    }

}
