/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author fskn
 */
public class PersonDTO {

    private int id;
    private String name;
    private String email;
    private int phone;
    private AddressDTO add;
    private List<HobbyDTO> hob = new ArrayList();

    public PersonDTO() {
    }

    public PersonDTO(Person p) {
        this.id = p.getId();
        this.name = p.getFirstName() + " " + p.getLastName();
        this.email = p.getEmail();
        this.phone = p.getPhone();
        this.add = new AddressDTO(p.getAddress());
        for(Hobby h : p.getHob()){
            hob.add(new HobbyDTO(h));
        }
    }

    public PersonDTO getPersons(Person persons){
        PersonDTO p = new PersonDTO(persons);
        return p;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public AddressDTO getAdd() {
        return add;
    }

    public void setAdd(AddressDTO add) {
        this.add = add;
    }

    public void addHobbyDTO(HobbyDTO hobby) {
        this.hob.add(hobby);
    }

    public List<HobbyDTO> getHob() {
        return hob;
    }

    public void setHob(List<HobbyDTO> hob) {
        this.hob = hob;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", add=" + add + ", hob=" + hob + '}';
    }

}
