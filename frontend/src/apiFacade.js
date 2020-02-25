const URL = "http://localhost:8080/securitystarter";
function handleHttpErrors(res) {
  if (!res.ok) {
    return Promise.reject({ status: res.status, fullError: res.json() });
  }
  return res.json();
}

function ApiFacade() {
  const login = (user, pass) => {
    const options = makeOptions("POST", true, {
      username: user,
      password: pass
    });
    return fetch(URL + "/api/login", options)
      .then(handleHttpErrors)
      .then(res => {
        setToken(res.token);
      });
  };

  const setToken = token => {
    localStorage.setItem("jwtToken", token);
  };

  const getToken = () => {
    return localStorage.getItem("jwtToken");
  };

  const loggedIn = () => {
    const loggedIn = getToken() != null;
    return loggedIn;
  };

  const logout = () => {
    localStorage.removeItem("jwtToken");
  };

  function makeOptions(method, addToken, body) {
    var opts = {
      method: method,
      headers: {
        "Content-type": "application/json",
        Accept: "application/json"
      }
    };
    if (addToken && loggedIn()) {
      opts.headers["x-access-token"] = getToken();
    }
    if (body) {
      opts.body = JSON.stringify(body);
    }
    return opts;
  }

  const getAllHobbies = () => {
    const options = makeOptions("GET", true); //True add's the token
    return fetch(URL + "/api/krak/hobbies/all", options).then(handleHttpErrors);
  };
  const getAllPersons = () => {
    const options = makeOptions("GET", true); //True add's the token
    return fetch(URL + "/api/krak/person/all", options).then(handleHttpErrors);
  };
  const getPersonById = (id) => {
    const options = makeOptions("GET", true); //True add's the token
    return fetch(URL + "/api/krak/person/" + id, options).then(handleHttpErrors);
  };
  const getPersonByHobby = (hobby) => {
    const options = makeOptions("GET", true); //True add's the token
    return fetch(URL + "/api/krak/hobbies/" + hobby, options).then(handleHttpErrors);
  };

  return {
    getAllHobbies,
    getAllPersons,
    getPersonById,
    getPersonByHobby,
    login,
    logout
  };
}

const facade = new ApiFacade();
export default facade;
