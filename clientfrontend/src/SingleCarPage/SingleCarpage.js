import React from 'react';
import axios from '../axios-objects';
// import {updateObject} from '../utility';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import './SingleCarPage.css';

class SingleCarPage extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            car: null
        }
    }

    componentDidMount = async () => {
        const id = this.props.match.params.carId;
        const response = await axios.get('/car-service/getSingleCar', {
            params: {
                id: id
            }
        });
        if (response) {
            this.setState({car: response.data});
        }
    }

    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="container showcase-container">
                        <div className="single-car">
                            <h2>{this.state.car !== null ? this.state.car.brand : null} {" "}
                            {this.state.car !== null ? this.state.car.model : null} </h2>
                            <div className="car-wrapper">
                                <div className="description">
                                    <p><strong>Fuel type</strong>: {this.state.car !== null ? this.state.car.fuelType : null}</p>
                                    <p><strong>Transmission</strong>: {this.state.car !== null ? this.state.car.transmission : null}</p>
                                    <p><strong>Class</strong>: {this.state.car !== null ? this.state.car.classCar : null}</p>
                                    <p><strong>Price(per day)</strong>: {this.state.car !== null ? this.state.car.price : null}$</p>
                                    <p><strong>Mileage</strong>: {this.state.car !== null ? this.state.car.mileage : null}km</p>
                                    <p><strong>Seats</strong>: {this.state.car !== null ? this.state.car.capacitySeats : null}</p>
                                    <p><strong>Seats for kids</strong>: {this.state.car !== null ? this.state.car.capacitySeatsForKids : null}</p>
                                    <p><strong>Horse power</strong>: {this.state.car !== null ? this.state.car.horsePower : null}</p>
                                    <p><strong>Location</strong>: {this.state.car !== null ? this.state.car.cityLocation : null}</p>
                                    <p><strong>USB</strong>: {this.state.car !== null ? (this.state.car.usb ? 'Yes' : 'No') : null}</p>
                                    <p><strong>GPS</strong>: {this.state.car !== null ? (this.state.car.gps ? 'Yes' : 'No') : null}</p>
                                </div>
                                <div className="image-wrapper">
                                    {this.state.car !== null ? <img alt="Car" src={require('../img/' + this.state.car.image)} /> : null}
                                    <p><strong>Description</strong>: {this.state.car !== null ? this.state.car.description : null} </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </header>
            </div>
        )
    }
}

export default SingleCarPage;