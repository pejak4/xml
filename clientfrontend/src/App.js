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
import CommentRequest from './CommentRequest/CommentRequest';
import Messages from './Messages/Messages';
import PrivateRouteUser from './PrivateRoute/PrivateRouteUser';
import PrivateRouteAdmin from './PrivateRoute/PrivateRouteAdmin';
import Overdraft from './Overdraft/Overdraft';

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
            <PrivateRouteUser path="/addAdvertisement" component={Advertisement} />
            <PrivateRouteAdmin path="/codebook" component={Codebook} />
            <PrivateRouteAdmin path="/setStateUser" component={StateUser} />
            <PrivateRouteUser path="/rentalRequest" component={RentalRequest} />
            <Route path="/singleCarPage/:carId" component={SingleCarPage} />
            <PrivateRouteUser path="/listCars" component={OccupancyCar} />
            <PrivateRouteUser path="/rentalRequestsFromMe" component={RentalRequestsFromMe} />
            <PrivateRouteAdmin path="/ratingRequest" component={RatingRequest} />
            <PrivateRouteAdmin path="/commentRequest" component={CommentRequest} />
            <PrivateRouteUser path="/messages" component={Messages}/>
            <PrivateRouteAdmin path="/overdraft" component={Overdraft}/>

          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
