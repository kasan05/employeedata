import React, { Component } from 'react';
import Main from './container/Main';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import 'react-table/react-table.css'

class App extends Component {
  render() {
    return (
      <div className="App">
        <Main />
      </div>
    );
  }
}

export default App;
