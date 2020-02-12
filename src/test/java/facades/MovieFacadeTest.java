package facades;

import utils.EMF_Creator;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
        private static Movie r1, r2;

    public MovieFacadeTest() {
    }

    //@BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = MovieFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = MovieFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        r1 = new Movie("Shawshank Redemption", 1994, new String[]{"Tim Robbins", "Morgan Freeman"});
        r2 = new Movie("Catch me if you can", 2002, new String[]{"Leonardo DiCaprio", "Tom Hanks"});
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(r1);
            em.persist(r2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testgetMovieCountMethod() {
        assertEquals(2, facade.getMovieCount(), "Expects two rows in the database");
    }

    @Test
    public void testgetMovieByIdMethod() {
        assertEquals("Shawshank Redemption", facade.getMovieById(Math.toIntExact(r1.getId())).getTitle(), "Expects Shawshank Redemption movie");
    }

    @Test
    public void testgetMovieByNameMethod() {
        List<Movie> resultList = facade.getMovieByName("Shawshank Redemption");
        assertEquals(1, resultList.size(), "Expects size of 1");
        assertEquals("Shawshank Redemption", resultList.get(0).getTitle(), "Expects Shawshank Redemption movie");

    }

    @Test
    public void testgetAllMoviesMethod() {
        List<Movie> resultList = facade.getAllMovies().getMovies();
        assertEquals(2, resultList.size(), "Expects two rows in the database");

    }
    
    @Test
    public void testinsertMovieMethod() {
        assertEquals(2, facade.getMovieCount(), "Expects two rows in the database");
        facade.insertMovie("Star Wars A New Hope", 1977, new String[]{"Mark Hamill","Carrie Fisher", "Harrison Ford"});
        assertEquals(3, facade.getMovieCount(), "Expects three rows in the database");

    }


    }
