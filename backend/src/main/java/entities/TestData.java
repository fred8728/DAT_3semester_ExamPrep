/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import errorhandling.NotFoundException;
import facades.KrakFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/**
 *
 * @author fskn
 */
public class TestData {

    public static void main(String[] args) throws NotFoundException, Exception {
        
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE);
        EntityManager em = emf.createEntityManager();
        Person p = new Person("fskn@hotmail.com", 12345987, "Frederikke", "Nilsson");
        Hobby hob = new Hobby("Svømning", "Specielt crawl");
        Address add = new Address("Mosebakken 12", "Greve", 2670);

        Person p1 = new Person("fiine@hotmail.com", 87654321, "Hans", "Jensen");
        Hobby hob1 = new Hobby("Sang", "Rap");
        Address add1 = new Address("Bækmosen 4", "Greve", 2670);
        
        Person p2 = new Person("fskn@hotmail.com", 23456789, "Caroline", "Linas");
        Hobby hob2 = new Hobby("Gymnastik", "Springe over buk");
        Address add2 = new Address("Fuglevej 1", "Højbjerg", 2456);
        
        p.addHob(hob);
        p.setAddress(add);
        p1.addHob(hob1);
        p1.setAddress(add1);
        p2.addHob(hob2);
        p2.setAddress(add2);
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        KrakFacade facade = KrakFacade.getKrakFacade(emf);
        System.out.println(facade.getAllHobbies().toString());
        System.out.println(facade.getAllPersons().toString());
        System.out.println(facade.getPersonsWithHobby("Sang"));
        System.out.println(facade.getPersonById(1));
        System.out.println(facade.getPersonByPhone(23456789));
        System.out.println(facade.getPersonByEmail("fiine@hotmail.com"));
        
        Person p3 = new Person("tina234@hotmail.com", 43522564, "Malene", "Sørensen");
        Hobby hob3 = new Hobby("Fitness", "Cardio og styrketræning");
        Address add3 = new Address("Maglebo 31", "Hundige", 2670);
        facade.addPerson(p3, add3, hob3);
        System.out.println(facade.getAllPersons());
        System.out.println(facade.getPersonCount());
        
        //facade.deletePerson(1);
        System.out.println(facade.getAllPersons());
    }

}
