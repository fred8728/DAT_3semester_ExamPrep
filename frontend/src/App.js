import React, { Component, useState } from "react";
import facade from "./apiFacade";
import LoggedIn from './LoggedIn'
import LogIn from './LogIn'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  NavLink
} from "react-router-dom";
import Persons from './Persons'

function App() {
  const [loggedIn, setLoggedIn] = useState(false);

  const logout = () => {
    facade.logout();
    setLoggedIn(false);
  };

  const login = (user, pass) => {
    facade.login(user, pass).then(res => {
      setLoggedIn(true);
    });
  };

    return (
      <div>
        {!loggedIn ? (
          <LogIn login={login} />
        ) : (
          
          <div>
            <Router>
            <Header />
            <Content />
          </Router>
            <button onClick={logout}>Logout</button>
          </div>
        )}
      </div>
    );
  }

  function Header() {
    return (
      <div>
        <ul className="header">
          <li>
            <NavLink exact activeClassName="active" to="/">
              Home
            </NavLink>
          </li>
          <li>
            <NavLink activeClassName="active" to="/people">
              People
            </NavLink>
          </li>
        </ul>
      </div>
    );
  }
  
  function Content() {
    return (
      <Switch>
        <Route path="/people"><Persons/></Route>
      </Switch>
    );
  }
  
export default App;
