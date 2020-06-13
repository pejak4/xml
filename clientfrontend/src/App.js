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
import SingleCarPage from './SingleCarPage/SingleCarpage';
import OccupancyCar from './OccupancyCar/OccupancyCar';
import RentalRequestsFromMe from './RentalRequest/RentalRequestsFromMe/RentalRequestsFromMe';
import RatingRequest from './RatingRequest/RatingRequest';

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
            <Route path="/singleCarPage/:carId" component={SingleCarPage} />
            <Route path="/listCars" component={OccupancyCar} />
            <Route path="/rentalRequestsFromMe" component={RentalRequestsFromMe} />
            <Route path="/ratingRequest" component={RatingRequest} />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
