package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
            instance.insertMovie("Shawshank Redemption",1994, new String[]{"Tim Robbins","Morgan Freeman"} );
            instance.insertMovie("Catch me if you can",2002, new String[]{"Leonardo DiCaprio","Tom Hanks"} );
            
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM Movie r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

    }

    public Movie getMovieById(int id) {
        EntityManager em = getEntityManager();
        return em.find(Movie.class, (long) id);
    }
    
    public Movie insertMovie(String title, int releaseYear, String[] actors){
        EntityManager em = getEntityManager();
        Movie m = new Movie(title, releaseYear, actors);
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
        return m;
    }
    
    public List<Movie> getAllMovies() {
        EntityManager em = getEntityManager();
        TypedQuery q = em.createQuery("SELECT m FROM Movie m", Movie.class);
        return q.getResultList();
    }
}
