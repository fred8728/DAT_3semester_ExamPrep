/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.HobbyDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import facades.KrakFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author fskn
 */
@Path("krak")
public class RegisterResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static KrakFacade facade = KrakFacade.getKrakFacade(EMF);
    private static UserFacade facadeUser = UserFacade.getUserFacade(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello RegisterResource\"}";
    }

    @GET
    @Path("hobbies/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllHobbies() {
        List<HobbyDTO> hobbyList = new ArrayList();
        List<Hobby> hobby = facade.getAllHobbies();
        HobbyDTO hobbyDTO = new HobbyDTO();

        for (Hobby hob : hobby) {
            hobbyList.add(hobbyDTO.getHobbies(hob));
        }
        return gson.toJson(hobbyList);
    }

    @GET
    @Path("hobbies/{hobby}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHobbyByName(@PathParam("hobby") String hobby) throws NotFoundException {
        return gson.toJson(facade.getHobbyByName(hobby));
    }

    @GET
    @Path("person/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons() {
        List<PersonDTO> personList = new ArrayList();
        List<Person> persons = facade.getAllPersons();
        PersonDTO personDTO = new PersonDTO();
        for (Person p : persons) {
            personList.add(personDTO.getPersons(p));
        }
        return gson.toJson(personList);
    }

    @GET
    @Path("person/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonById(@PathParam("id") int id) throws NotFoundException {
        return gson.toJson(facade.getPersonById(id));
    }

    

}
