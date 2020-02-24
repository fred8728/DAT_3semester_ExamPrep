/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.AddressDTO;
import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author fskn
 */
public class KrakFacade {

    private static EntityManagerFactory emf;
    private static KrakFacade instance;

    public KrakFacade() {
    }

    public static KrakFacade getKrakFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new KrakFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Person> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("Select p from Person p", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByPhone(int phone) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return new PersonDTO(em.createQuery("SELECT p FROM Person P WHERE p.phone=:phone", Person.class).setParameter("phone", phone).getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("The given phone id doesnt exist");
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByEmail(String email) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return new PersonDTO(em.createQuery("SELECT p FROM Person P WHERE p.email=:email", Person.class).setParameter("email", email).getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("The given email doesnt exists");
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonById(int id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return new PersonDTO(em.createQuery("SELECT p FROM Person p WHERE p.id=:id", Person.class).setParameter("id", id).getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("The given id doesnt exist");
        } finally {
            em.close();
        }
    }

    public List<Hobby> getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("Select h from Hobby h", Hobby.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public HobbyDTO getHobbyByName(String hobby) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return new HobbyDTO(em.createQuery("Select h from Hobby h WHERE h.name=:name", Hobby.class).setParameter("name", hobby).getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("The hobby doest exist");
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsWithHobby(String hobby) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query = em.createQuery("Select person from Person person JOIN person.hob hobbies where hobbies.name =:name", PersonDTO.class).setParameter("name", hobby);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("The hobby doest exist");
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Person p");
            return Integer.parseInt(query.getSingleResult().toString());
        } finally {
            em.close();
        }
    }

    public Person addPerson(Person p, Address add, Hobby hob) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            p.addHob(hob);
            p.setAddress(add);

            if (p.getEmail()
                    == null || p.getFirstName() == null || p.getLastName() == null || p.getPhone() == 0) {
                throw new Exception("You must fill all the info");
            }

            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

    public Person deletePerson(int id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("The given id doesnt exist - no person deleted");
        } finally {
            em.close();
        }
    }
}
