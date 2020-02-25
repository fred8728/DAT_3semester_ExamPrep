import React, {Component} from 'react'

class LoggedIn extends Component {
    constructor(props) {
      super(props);
      this.state = { dataFromServer: "Fetching!!" };
    }
    componentDidMount() {}
    render() {
      return (
        <div>
          <h2>Data Received from server</h2>
          <h3>{this.state.dataFromServer}</h3>
        </div>
      );
    }
  }

  export default LoggedIn;