package facades;

import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class KrakFacadeTest {

    private static EntityManagerFactory emf;
    private static KrakFacade facade;
    private static Person p;
    private static Person p1;
    private static Person p2;

    public KrakFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = KrakFacade.getKrakFacade(emf);

        p = new Person("fskn@hotmail.com", 12345678, "Frederikke", "Nilsson");
        Hobby hob = new Hobby("Svømning", "Specielt crawl");
        Address add = new Address("Mosebakken 12", "Greve", 2670);

        p1 = new Person("fiine@hotmail.com", 12345678, "Hans", "Jensen");
        Hobby hob1 = new Hobby("Sang", "Rap");
        Address add1 = new Address("Bækmosen 4", "Greve", 2670);

        p2 = new Person("fskn@hotmail.com", 12345678, "Caroline", "Linas");
        Hobby hob2 = new Hobby("Gymnastik", "Springe over buk");
        Address add2 = new Address("Fuglevej 1", "Højbjerg", 2456);

        p.addHob(hob);
        p.setAddress(add);
        p1.addHob(hob1);
        p1.setAddress(add1);
        p2.addHob(hob2);
        p2.setAddress(add2);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();
        em.close();
    }
    
    @Test
        public void getPersonByIdTest() throws NotFoundException{
        PersonDTO person = facade.getPersonById(p2.getId());
        PersonDTO person1 = facade.getPersonById(p.getId());
        assertEquals(person.getName(), "Caroline Linas");
        assertEquals(person1.getEmail(), "fskn@hotmail.com");
}
        
     @Test
        public void getAmountOfPersonsTest(){
            assertEquals(3, facade.getPersonCount());
        }
     
}

