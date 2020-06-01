import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import HomePage from './HomePage/HomePage';
import Login from './Login/Login';
import Registration from './Registration/Registration';
import SearchPage from './SearchPage/SearchPage';

class App extends React.PureComponent {
  render() {
    return (
      <div className="App">
        <BrowserRouter>
          <Switch>
            <Route path="/" exact component={HomePage} />
            <Route path="/login" component={Login} />
            <Route path="/registration" component={Registration} />
            <Route path="/search" component={SearchPage} />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
