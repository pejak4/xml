import React from 'react';
import Navbar from '../Navbar/Navbar';
import axios from '../axios-objects';
import ReactStars from 'react-rating-stars-component';
import './EndRental.css';
import '../Advertisement/Advertisement.css';


class EndRental extends React.PureComponent{

    state = {
        rentalRequestPaid: [],

        mileage: 0,
        description: '',
    }

    componentDidMount = async() => {
        const token = sessionStorage.getItem('token');
        const response = await axios.get('/getLoggedUser', {
            headers: {
                'Authorization' : 'Bearer ' + token
            }
        });
        const fromUserId = response.data.id;

        const data = {fromUserId};
        const response01 = await axios.post('/getCarsRentalEnd', data);
        if(response01) {
            console.log(response01.data);
            this.setState({rentalRequestPaid: response01.data});
        }
    }

    selectHandler = (event, type) => {
        if(type === 'mileage') {
            this.setState({mileage: event.target.value})
        } else if(type === 'description') {
            this.setState({description: event.target.value})
        }
    }

    addHandler = async(rentalRequest) => {
        console.log(rentalRequest);
        const carId = rentalRequest.rentalRequestCar.id;
        const rentalRequestId = rentalRequest.id;
        const mileage = this.state.mileage;
        const description = this.state.description;
        const data = {carId, rentalRequestId, mileage, description};
        const response = await axios.post('/setMileageAndDescription', data);
        if(response) {
            console.log(response.data);
        }
    }

    renderCars() {
        return this.state.rentalRequestPaid.map((rentalRequest, i) => {
            return (
                <div key={i} className="car-card">
                    <h3>{rentalRequest.rentalRequestCar.brand} {rentalRequest.rentalRequestCar.model}</h3>
                    <div className="car-card-inner">
                        <div className="left-side">
                            <img alt="Car" src={require('../img/' + rentalRequest.rentalRequestCar.image)} />
                        </div>
                        <div className="right-side">
                            <div>
                                <div>
                                    <img alt="Fuel" src={require('../img/fuelTypeIcon.png')} title="Fuel Type"/>
                                    <p className="icon-text">{rentalRequest.rentalRequestCar.fuelType}</p>
                                </div>
                                <div>
                                    <img alt="Seats" src={require('../img/numberOfSeatsIcon.png')} title="Number Of Seats"/>
                                    <p className="icon-text">{rentalRequest.rentalRequestCar.capacitySeats}</p>
                                </div>
                            </div>
                            
                            <div>
                                <div>
                                    <img alt="Transmission" src={require('../img/transmissionIcon.png')} title="Transmission Type"/>
                                    <p className="icon-text">{rentalRequest.rentalRequestCar.transmission}</p>
                                </div>
                                <div>
                                    <img alt="GPS" src={require('../img/gpsIcon.png')} title="GPS"/>
                                    <p className="icon-text">{rentalRequest.rentalRequestCar.gps ? 'own' : '-'}</p>
                                </div>
                            </div>
                            
                            <div>
                                <div>
                                    <img alt="USB" src={require('../img/usbIcon.png')} title="USB"/>
                                    <p className="icon-text">{rentalRequest.rentalRequestCar.usb ? 'own' : '-'}</p>
                                </div>
                                <div>
                                    <img alt="Doors" src={require('../img/doorsIcon.png')} title="Number Of Doors"/>
                                    <p className="icon-text">{rentalRequest.rentalRequestCar.doors}</p>
                                </div>

                                <ReactStars
                                    value={rentalRequest.rentalRequestCar.rating}
                                    count={5}
                                    size={35}
                                    color2={'#ffd700'} />
                            </div>
                            <div>
                                <input type='number' placeholder='Add mileage' onChange={(event) => this.selectHandler(event, 'mileage')}/>
                                <input type='text' placeholder='Add description' onChange={(event) => this.selectHandler(event, 'description')}/>
                                <button clasName='btn' onClick = {(event) => {this.addHandler(rentalRequest)}}>Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })
    }




    render() {
        return(
            <div>
                <Navbar />
                <header id="showcase">
                    <br/><br/>
                    <div className="containerSearch showcase-containerSearch" style={{height:'87%'}}>
                        <div style={{overflowY:'scroll', maxWidth:'100%'}}>
                            {this.renderCars()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default EndRental;