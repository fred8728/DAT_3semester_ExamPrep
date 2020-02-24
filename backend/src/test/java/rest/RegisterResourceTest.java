package rest;

import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.User;
import entities.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

@Disabled
public class RegisterResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static Person p;
    private static Person p1;
    private static Person p2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
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
        
        
        em.getTransaction().begin();
        em.persist(p);
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void serverIsRunning() {
        System.out.println("Testing is server UP");
        given().when().get("/krak").then().statusCode(200);
    }

    @Test
    public void personOnListTest() {
        given()
                .contentType("application/json")
                .get("/krak/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems("Frederikke Nilsson", "Caroline Linas", "Hans Jensen"));
    }

    @Test
    public void hobbyOnListTest() {
        given()
                .contentType("application/json")
                .get("/krak/hobbies/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", hasItems("Sang", "Svømning", "Gymnastik"));
    }

    /**
     * @Test
    public void getPersonByIdTest() {
        given()
                .contentType("application/json")
                .get("/krak/person/1").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Frederikke Nilsson"))
                .body("email", equalTo("fskn@hotmail.com"));
    }
     */

}
