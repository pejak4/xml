import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Registration from './Registration/Registration';
import Login from './Login/Login';
import HomePage from './HomePage/HomePage';
import PrivateRouteAdmin from './PrivateRoute/PrivateRouteAdmin';
import Advertisement from './Advertisement/Advertisement';
import Occupancy from './OccupancyCar/OccupancyCar';
import RentalRequest from './RentalRequest/RentalRequest';
import Graph from './Graph/Graph';
import EndRental from './EndRental/EndRental';

class App extends React.PureComponent {
  render() {
    return (
      <div>
        <BrowserRouter>
          <Switch>
            <Route path="/" exact component={HomePage} />
            <Route path="/registration" exact component={Registration} />
            <Route path="/login" exact component={Login} />
            <Route path="/advertisement" exact component={Advertisement} />
            <Route path="/occupancy" exact component={Occupancy} />
            <Route path="/rentalRequest" exact component={RentalRequest} />
            <Route path="/graph" exact component={Graph} />
            <Route path="/endRental" exact component={EndRental} />
         </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;