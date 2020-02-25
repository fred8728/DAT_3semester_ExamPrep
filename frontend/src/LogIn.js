import React, {useState} from 'react'

function LogIn(props) {
    const [state, setState] = useState();
  
    const login = evt => {
      evt.preventDefault();
      props.login(state.username, state.password);
    };
    const onChange = evt => {
      setState({ ...state, [evt.target.id]: evt.target.value });
    };
    return (
      <div>
        <h2>Login</h2>
        <form onSubmit={login} onChange={onChange}>
          <input placeholder="User Name" id="username" />
          <input placeholder="Password" id="password" />
          <button>Login</button>
        </form>
      </div>
    );
  }

  export default LogIn;