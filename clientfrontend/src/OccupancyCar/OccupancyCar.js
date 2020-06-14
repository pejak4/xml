import React from 'react';
import axios from '../axios-objects';
import DatePicker from 'react-datepicker';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';

class OccupancyCar extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            allCarsLoggedUser: [],
            startDate: new Date(new Date().setTime(new Date().getTime() + 2 * 86400000)),
            endDate: new Date(new Date().setTime(new Date().getTime() + 3 * 86400000))
        }
    }

    componentDidMount = async() =>{
        const userEmail = sessionStorage.getItem('userEmail');
        const data = {userEmail};
        const response = await axios.post('/authentication-service/getLoggedUser', data);

        const userId = response.data.id;
        const data1 = {userId};
        const response1 = await axios.post('/car-service/getCarsLogedUser', data1);
        if(response1) {
            this.setState({allCarsLoggedUser: response1.data});
        }
    }

    startDateHandler = (date) => {
        this.setState({startDate: date});
    }

    endDateHandler = (date) => {
        this.setState({endDate: date});
    }

    addOccupancyHandler = async(event, car) =>{
        event.preventDefault();

        let startDate = this.state.startDate;
        let endDate = this.state.endDate;
        const data = {startDate, endDate, car};
        await axios.post('/car-service/addOccupancy', data);
    }

    renderAllCarsLoggedUser(){
        return (
            this.state.allCarsLoggedUser.map((car, i) => {
                return (
                    <div key={i} className="car-card">
                        <h3>{car.brand} {car.model}</h3>
                        <div className="car-card-inner">
                            <div className="left-side">
                                <img alt="Car" src={require('../img/' + car.image)} />
                            </div>
                            <div className="right-side">
                                <div>
                                    <div>
                                        <img alt="Fuel" src={require('../img/fuelTypeIcon.png')} title="Fuel Type"/>
                                        <p className="icon-text">{car.fuelType}</p>
                                    </div>
                                    <div>
                                        <img alt="Seats" src={require('../img/numberOfSeatsIcon.png')} title="Number Of Seats"/>
                                        <p className="icon-text">{car.capacitySeats}</p>
                                    </div>
                                </div>
                                
                                <div>
                                    <div>
                                        <img alt="Transmission" src={require('../img/transmissionIcon.png')} title="Transmission Type"/>
                                        <p className="icon-text">{car.transmission}</p>
                                    </div>
                                    <div>
                                        <img alt="GPS" src={require('../img/gpsIcon.png')} title="GPS"/>
                                        <p className="icon-text">{car.gps ? 'own' : '-'}</p>
                                    </div>
                                </div>
                                
                                <div>
                                    <div>
                                        <img alt="USB" src={require('../img/usbIcon.png')} title="USB"/>
                                        <p className="icon-text">{car.usb ? 'own' : '-'}</p>
                                    </div>
                                    <div>
                                        <img alt="Doors" src={require('../img/doorsIcon.png')} title="Number Of Doors"/>
                                        <p className="icon-text">{car.doors}</p>
                                    </div>
                                </div>
                                <div>
                                    <button className="btn" style={{width:'150px', textAlign:'center'}}
                                    onClick={(event) => {this.addOccupancyHandler(event, car)}}>
                                        Add Occupancy
                                    </button>
                                    <DatePicker className="date"
                                        minDate={new Date(new Date().setTime(new Date().getTime() + 2 * 86400000))}
                                        selected={this.state.startDate}
                                        onChange={date => this.startDateHandler(date)}
                                        showTimeSelect
                                        timeFormat="HH:mm"
                                        timeIntervals={15}
                                        timeCaption="time"
                                        dateFormat="MMMM d, yyyy h:mm aa"
                                    />
                                    <DatePicker className="date"
                                        minDate={new Date(new Date().setTime(new Date().getTime() + 2 * 86400000))}
                                        selected={this.state.endDate}
                                        onChange={date => this.endDateHandler(date)}
                                        showTimeSelect
                                        timeFormat="HH:mm"
                                        timeIntervals={15}
                                        timeCaption="time"
                                        dateFormat="MMMM d, yyyy h:mm aa"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                );
            })
        );
    }

    render() {
        return (
            <div>
                <HamburgerMenu/>
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch" style={{overflowY:'scroll', maxWidth:'90%'}}>
                        {this.renderAllCarsLoggedUser()}
                    </div>
                </header>
            </div>
        );
    }
}

export default OccupancyCar;