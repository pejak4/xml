import React from 'react';
import axios from '../axios-objects';
import DatePicker from 'react-datepicker';
import './OccupancyCar.css';
import '../Advertisement/Advertisement.css';
import Navbar from '../Navbar/Navbar';
import SingleCarpage from '../SingleCarpage/SingleCarpage';
import ReactStars from 'react-rating-stars-component'

class OccupancyCar extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            allCarsLoggedUser: [],
            startDate: new Date(new Date().setTime(new Date().getTime() + 2 * 86400000)),
            endDate: new Date(new Date().setTime(new Date().getTime() + 3 * 86400000)),

            renderNum: 'jedan',
            currentCar: null,
        }
    }

    componentDidMount = async() =>{
        const token = sessionStorage.getItem('token');
        const response = await axios.get('/getLoggedUser', {
            headers: {
                'Authorization' : 'Bearer ' + token
            }
        });

        const userId = response.data.id;
        const data1 = {userId};
        const response1 = await axios.post('/getCarsLogedUser', data1);
        if(response1) {
            this.setState({allCarsLoggedUser: response1.data});
            console.log(response1.data);
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

        console.log(this.state.startDate);
        console.log(this.state.endDate);
        console.log(car);



        let startDate = this.state.startDate;
        let endDate = this.state.endDate;
        
        let carId = car.id;
        const data = {startDate, endDate, carId};
        await axios.post('/addOccupancy', data);
    }

    setNumOfRender = (num) => {
        this.setState({renderNum: num})
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
                                    <ReactStars
                                        value={car.rating}
                                        count={5}
                                        size={35}
                                        color2={'#ffd700'} />
                                </div>
                                <div>
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
                                    <button className="btn" style={{width:'150px', textAlign:'center'}}
                                    onClick={(event) => {this.addOccupancyHandler(event, car)}}>
                                        Add Occupancy
                                    </button>
                                    
                                    <div>
                                        <button onClick={(event) => {this.setState({renderNum: 'dva', currentCar: car})}}> More details </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                );
            })
        );
    }

    render() {
        if(this.state.renderNum==='jedan') {
            return (
                <div>
                    <Navbar />
                    <header id="showcase">
                        <br/><br/>
                        <div className="containerSearch showcase-containerSearch" style={{overflowY:'scroll', maxWidth:'90%', height:'87%'}}>
                            {this.renderAllCarsLoggedUser()}
                        </div>
                    </header>
                </div>
            );
        } else if (this.state.renderNum === 'dva') {
            return(
                <div>
                    <SingleCarpage car={this.state.currentCar} endDate={this.state.endDate} setNumOfRender={this.setNumOfRender}></SingleCarpage>
                </div>
            );
        }
    }
}

export default OccupancyCar;