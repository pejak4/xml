import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import HomePage from './HomePage/HomePage';
import Login from './Login/Login';
import Registration from './Registration/Registration';
import SearchPage from './SearchPage/SearchPage';
import Advertisement from './Advertisement/Advertisement';
import Codebook from './Codebook/Codebook';
import StateUser from './StateUser/StateUser';
import RentalRequest from './RentalRequest/RentalRequest';

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
            <Route path="/addAdvertisement" component={Advertisement} />
            <Route path="/codebook" component={Codebook} />
            <Route path="/setStateUser" component={StateUser} />
            <Route path="/rentalRequest" component={RentalRequest} />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
