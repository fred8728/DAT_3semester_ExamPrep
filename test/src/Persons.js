import React, { useState, useEffect } from "react";
import apiFacade from "./apiFacade";
import {
  //BrowserRouter,
  useRouteMatch,
  Link
} from "react-router-dom";
import Search from "./Search";

const GetPeopleList = () => {
  const [people, setPeople] = useState([]);
  const[search, setSearch] = useState("");

  const handleChange = e => {
      setSearch(e.target.value)
  }
 
  useEffect(() => {
      apiFacade.getAllPersons().then(data=>{ 
        const sortPeople = data.sort(function(a,b){
            var nameA = a.name.toLowerCase(), nameB = b.name.toLowerCase();
            if(nameA<nameB)
            //sort string ASC
            return -1;
            if(nameA>nameB) return 1;
            return 0; //default value(no sorting)
        })
        setPeople(sortPeople)})
  }, [search]);
  console.log(people);

  return (
    <div>
        <h1>Register</h1>
    <table class="table">
    <Search value={search} onChange={handleChange} />
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Email</th>
          <th>Phone</th>
          <th>Street</th>
          <th>City</th>
          <th>Zipcode</th>
        </tr>
      </thead>
      <tbody>
      {people.filter(person =>{
          return (
              person.name.toLowerCase().includes(search.toLowerCase()) ||
              person.phone.includes(search)
          )
      })
            .map((data) => (
              <tr key={data.id}>
                  <td>{data.id}</td>
                  <td>{data.name}</td>
                <td>{data.email}</td>
                <td>{data.phone}</td>
                <td>{data.add.street}</td>
                <td>{data.add.city}</td>
                <td>{data.add.zip}</td>
              </tr>
            ))}
      </tbody>
    </table>
  </div>
  );
};

export default GetPeopleList;
