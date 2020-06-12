import React from 'react';
import './SearchPage.css';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import {updateObject} from '../utility';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Typography from '@material-ui/core/Typography';
import Slider from '@material-ui/core/Slider';
import axios from '../axios-objects';
import {Link} from 'react-router-dom';
import Popup from "reactjs-popup";
import ReactStars from 'react-rating-stars-component'


class SearchPage extends React.PureComponent {
    
    constructor(props) {
        super(props);

        this.state = {
            price: {
                value: [0, 1000]
            },
            car: {
                brand: '',
                model: '',
                fuelType: '',
                transmission: '',
                classCar: '',
                mileage: '',
                plannedMileage: '',
                CDW: false,
                capacitySeatsForKids: '',
                cityLocation: '',
                startDate: null,
                endDate: null
            },
            search: {
                cars: [],
                brand: [],
                model: [],
                fuelType: [],
                transmission: [],
                classCar: []
            },
            startDateString: '',
            endDateString: '',

            listCarForCart: [],
            valid: {
                rentalRequestExists: false
            }
        }
    }

    componentDidMount = () => {
        //grad i vreme iz pocetne pretrage
        const car = updateObject(this.state.car, {
            cityLocation: this.props.location.state.cityLocation,
            startDate: this.props.location.state.startDate,
            endDate: this.props.location.state.endDate
        });
        this.setState({car});

        //niz svih auta koje smo dobili pocetnom pretragom
        const carArray = [...this.props.location.state.cars];

        //niz svih marki automobila koji su dosli iz pocetne pretrage
        let brandArray = [];
        for(let i=0; i < carArray.length; i++) {
            if(!brandArray.includes(carArray[i].brand))
                brandArray.push(carArray[i].brand);
        }

        //niz svih modela automobila koji su dosli iz pocetne pretrage
        let modelArray = [];
        for(let i=0; i < carArray.length; i++) {
            if(!modelArray.includes(carArray[i].model))
                modelArray.push(carArray[i].model);
        }

        //niz svih tipova goriva automobila koji su dosli iz pocetne pretrage
        let fuelTypeArray = [];
        for(let i=0; i < carArray.length; i++) {
            if(!fuelTypeArray.includes(carArray[i].fuelType))
                fuelTypeArray.push(carArray[i].fuelType);
        }

        //niz svih vrsta menjaca automobila koji su dosli iz pocetne pretrage
        let transmissionArray = [];
        for(let i=0; i < carArray.length; i++) {
            if(!transmissionArray.includes(carArray[i].transmission))
                transmissionArray.push(carArray[i].transmission);
        }

        //niz svih klasi automobila od onih koji su dosli iz pocetne pretrage
        let classCarArray = [];
        for(let i=0; i < carArray.length; i++) {
            if(!classCarArray.includes(carArray[i].classCar))
                classCarArray.push(carArray[i].classCar);
        }

        let search = updateObject(this.state.search, {
            cars: carArray,
            brand: brandArray,
            model: modelArray,
            fuelType: fuelTypeArray,
            transmission: transmissionArray,
            classCar: classCarArray
        })
        this.setState({search});


        //Sredjivanje datuma za korpu
        let startDateOk = this.props.location.state.startDate.toString();
        let startDateSplit = startDateOk.split(' ');
        startDateOk = startDateSplit[1] + '-' + startDateSplit[2] + '-' + startDateSplit[3];

        let endDateOk = this.props.location.state.endDate.toString();
        let endDateSplit = endDateOk.split(' ');
        endDateOk = endDateSplit[1] + '-' + endDateSplit[2] + '-' + endDateSplit[3];
        
        this.setState({
            startDateString: startDateOk,
            endDateString: endDateOk
        })
    }

    selectHandler = (event, type) => {
        let car = updateObject(this.state.car, {
            [type]: event.target.value
        });
        this.setState({car});
    }

    checkboxHandler = (event, type) => {
        let car = updateObject(this.state.car, {
            [type]: event.target.checked
        });
        this.setState({car});
    }

    valuetext = (value) => {
        return `${value}`;
    }

    handleChange = (event, newValue) => {
        let price = updateObject(this.state.price, {
            value: newValue
        });
        this.setState({price});
    }

    rangeSliderRender = () => {
        return(
            <div style={{width:'91%', display:'inline-block', marginLeft:'20px', marginRight:'70px', marginTop:'-15px'}}>
                <Typography id="range-slider" gutterBottom style={{color:'#333'}}>
                    Price range
                </Typography>
                <Slider
                    style={{color: '#333'}}
                    value={this.state.price.value}
                    step={10}
                    max={1000}
                    onChange={this.handleChange}
                    valueLabelDisplay="auto"
                    aria-labelledby="range-slider"
                    getAriaValueText={this.valuetext}
                />
        </div>
        );
    }

    filterHandler = async (event) => {
        event.preventDefault();
        
        const {brand, model, fuelType, transmission, classCar, mileage,
            plannedMileage, CDW, capacitySeatsForKids, cityLocation, startDate, endDate} = this.state.car;
        const price = this.state.price.value;
        const data = {brand, model, fuelType, transmission, classCar, price, mileage,
            plannedMileage, CDW, capacitySeatsForKids, cityLocation, startDate, endDate};

        const response = await axios.post('/car-service/filterCars', data);
        if (response) {
            const filterCars = response.data;
            let search = updateObject(this.state.search, {
                cars: filterCars
            });
            this.setState({search});
        }
    }

    rentCarHandler = async (event, car) =>{
        event.preventDefault();

        let userId;
        let userEmail = sessionStorage.getItem('userEmail');
        const data0 = {userEmail};
        const response00 = await axios.post('/authentication-service/getLoggedUser', data0);
        if(response00) {
            userId = response00.data.id;
        }

        let carId = car.id; //Proveravamo da li je ovaj korisnik vec poslao zahev za ovaj auto
        const data1 = {userId, carId};
        const response1 = await axios.post('/car-service/getRentalRequestByUserIdAndCar', data1);
        let rentalRequestExists = false;
        if(response1) {
            if(response1.data !== "") {
                rentalRequestExists = true;
                let valid = updateObject(this.state.valid, {
                    rentalRequestExists: true
                });
                this.setState({valid});
            } else {
                let valid = updateObject(this.state.valid, {
                    rentalRequestExists: false
                });
                this.setState({valid});
            }
        }


        let startDate = this.state.car.startDate;
        let endDate = this.state.car.endDate;

        console.log(carId);
        carId = car.id;
        
        const data2 = {startDate, endDate, carId};
        const response2 = await axios.post('/car-service/checkOccupancy', data2);
        if(response2) {
            if(response2.data.length != 0)
                rentalRequestExists = true;       
        }

        let startData = this.state.car.startDate;
        let endData = this.state.car.endDate;

        const data = {carId, startData, endData, userId};

        if(rentalRequestExists === true) {
            alert('Rental request exists or car is busy in this time.');
        } else {
            await axios.post('/car-service/addRentalRequest', data);
        }
    }

    addToCartHandler = async (event, car) => {
        event.preventDefault();

        let cars = [...this.state.listCarForCart];

        let existCar = false;

        if(cars.length <= 3) {
            cars.push(car);
            for(let i=0; i<this.state.listCarForCart.length; i++)
            {
                if(this.state.listCarForCart[i].id === car.id)
                    existCar = true;
            }
            if(!existCar)
                this.setState({listCarForCart: cars})
        }
    }

    discardCartHandler = (event, car) =>{
        let elementsFromCart = [...this.state.listCarForCart];

        const index = elementsFromCart.indexOf(car);
        if(index > -1)
            elementsFromCart.splice(index, 1);

        this.setState({listCarForCart: elementsFromCart})
    }

    cartHandler = (event) => {
        console.log(this.state.listCarForCart);
    }

    ratingChangedHandler = (rating) => {
        console.log(rating)
    }

    render() {
        return (
            <div>
                {this.state.listCarForCart.length > 0 ? 
                <div className="positionCart">
                    <h2 className="h4Cart">Cart</h2>
                    <hr/>
                    <br/>
                    <p className="h4Cart"><b>Start date:</b> {this.state.startDateString}</p>
                    <p className="h4Cart"><b>End date:</b> {this.state.endDateString}</p>
                    <br/>
                    <hr/>
                    {this.state.listCarForCart.map((car, i) => {
                        return(
                            <p key={i}>{car.brand} {car.model} 
                            <button key={i} className="butCart" onClick={(event)=>{this.discardCartHandler(event, car)}}
                            >Discard</button> 
                            <hr/>
                            </p>
                        );
                    })}
                    <button className="butCart" onClick={(event) => {this.cartHandler(event)}}>Rent all</button>
                </div>
                : null}
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch" style={{overflowY:'scroll', maxWidth:'90%'}}>
                        <div className="filterDiv">
                            <p>Filter your search</p>
                            <div className="wrapper-search" style={{marginLeft:'0px'}}>
                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Brand</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'brand')}
                                    >
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    {this.state.search.brand.map((brand, i) => {
                                        return (
                                            <MenuItem key={i} value={brand}>{brand}</MenuItem>
                                        );
                                    })}
                                    </Select>
                                    <FormHelperText>Filter by brand</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Model</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'model')}
                                    >
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    {this.state.search.model.map((model, i) => {
                                        return (
                                            <MenuItem key={i} value={model}>{model}</MenuItem>
                                        );
                                    })}
                                    </Select>
                                    <FormHelperText>Filter by model</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Fuel Type</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'fuelType')}
                                    >
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    {this.state.search.fuelType.map((fuelType, i) => {
                                        return (
                                            <MenuItem key={i} value={fuelType}>{fuelType}</MenuItem>
                                        );
                                    })}
                                    </Select>
                                    <FormHelperText>Filter by fuel type</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Transmission</InputLabel>
                                    <Select
                                        labelId="demo-simple-select-helper-label"
                                        id="demo-simple-select-helper"
                                        onChange={(event) => this.selectHandler(event, 'transmission')}
                                        >
                                        <MenuItem value="">
                                            <em>None</em>
                                        </MenuItem>
                                        {this.state.search.transmission.map((transmission, i) => {
                                            return (
                                                <MenuItem key={i} value={transmission}>{transmission}</MenuItem>
                                            );
                                        })}
                                    </Select>
                                    <FormHelperText>Filter by type</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Class car</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'classCar')}
                                    >
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    {this.state.search.classCar.map((classCar, i) => {
                                        return (
                                            <MenuItem key={i} value={classCar}>{classCar}</MenuItem>
                                        );
                                    })}
                                    </Select>
                                    <FormHelperText>Filter by class</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Max mileage</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'mileage')}
                                    >
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    <MenuItem value="20000">20000km</MenuItem>
                                    <MenuItem value="40000">40000km</MenuItem>
                                    <MenuItem value="60000">60000km</MenuItem>
                                    <MenuItem value="80000">80000km</MenuItem>
                                    <MenuItem value="100000">100000km</MenuItem>
                                    <MenuItem value="150000">150000km</MenuItem>
                                    <MenuItem value="200000">200000km</MenuItem>
                                    <MenuItem value="300000">300000km</MenuItem>
                                    </Select>
                                    <FormHelperText>Filter by mileage</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Distance to travel</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'plannedMileage')}
                                    >
                                    <MenuItem value="">
                                        <em>Unlimited</em>
                                    </MenuItem>
                                    <MenuItem value="2000">2000km</MenuItem>
                                    <MenuItem value="4000">4000km</MenuItem>
                                    <MenuItem value="6000">6000km</MenuItem>
                                    <MenuItem value="8000">8000km</MenuItem>
                                    <MenuItem value="10000">10000km</MenuItem>
                                    <MenuItem value="15000">15000km</MenuItem>
                                    <MenuItem value="20000">20000km</MenuItem>
                                    <MenuItem value="30000">30000km</MenuItem>
                                    </Select>
                                    <FormHelperText>Filter by mileage</FormHelperText>
                                </FormControl>

                                <FormControl className="select-brand">
                                    <InputLabel id="demo-simple-select-helper-label">Seats for kids</InputLabel>
                                    <Select
                                    labelId="demo-simple-select-helper-label"
                                    id="demo-simple-select-helper"
                                    onChange={(event) => this.selectHandler(event, 'capacitySeatsForKids')}
                                    >
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    <MenuItem value="0">0</MenuItem>
                                    <MenuItem value="1">1</MenuItem>
                                    <MenuItem value="2">2</MenuItem>
                                    <MenuItem value="3">3</MenuItem>
                                    </Select>
                                    <FormHelperText>Filter by number</FormHelperText>
                                </FormControl>
                                
                                <div style={{alignSelf:'center', marginBottom:'0px'}}>
                                    <label style={{color:'rgba(0, 0, 0, 0.54)', margin:'1rem 1rem'}}>Collision Damage Waiver</label>
                                    <input type="checkbox" style={{alignSelf:'center'}} 
                                        onClick={(event) => this.checkboxHandler(event, 'CDW')} />
                                </div>

                                {this.rangeSliderRender()}
                            </div>
                            <a style={{width:'150px', textAlign:'center', float:'right',margin:'0 4rem 10px 0'}}
                                 className="btn" href="/" onClick={(event) => this.filterHandler(event)}>Filter</a>
                        </div>
                        
                        {this.state.search.cars.map((car, i) => {
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
                                                    value={3}
                                                    count={5}
                                                    onChange={ (event) => {this.ratingChangedHandler(event)}}
                                                    size={35}
                                                    color2={'#ffd700'} />
                                            </div>

                                            <div className="details-rent">
                                                <div>
                                                    <Link to={{pathname:"/singleCarPage/"+car.id}} target="_blank" > More details </Link>
                                                </div>
                                                <div>
                                                    <a href="/" className="btn" style={{width:'250px', textAlign:'center'}}
                                                    onClick={(event) => {this.addToCartHandler(event, car)}}>
                                                        Add to cart
                                                    </a>
                                                    {/* <a href="/" className="btn" style={{width:'150px', textAlign:'center'}} */}
                                                    {this.state.valid.rentalRequestExists ? <Popup trigger={<button className="btn" style={{width:'150px', textAlign:'center'}}
                                                    onClick={(event) => {this.rentCarHandler(event, car)}}>
                                                        Rent
                                                    </button>} position="right center">
                                                    <div style={{background:'red', marginLeft:'0px', color:'#fff'}}>You can't request twice for same car!</div>
                                                    </Popup> : <button className="btn" style={{width:'150px', textAlign:'center'}}
                                                    onClick={(event) => {this.rentCarHandler(event, car)}}>
                                                        Rent
                                                    </button>}  
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </header>
            </div>
        );
    }
}

export default SearchPage;