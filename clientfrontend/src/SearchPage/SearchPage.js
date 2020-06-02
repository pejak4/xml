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
            <div style={{width:'200px', display:'inline-block', marginLeft:'20px', marginRight:'70px', marginTop:'-9px'}}>
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

        const response = await axios.post('/search-car-service/filterCars', data);
        if (response) {
            const filterCars = response.data;
            let search = updateObject(this.state.search, {
                cars: filterCars
            });
            this.setState({search});
        }
    }

    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch" style={{overflowY:'scroll', maxWidth:'90%'}}>
                        <div className="filterDiv">
                            <p>Filter your search</p>
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
                                <FormHelperText>Filter by transmission</FormHelperText>
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
                                <FormHelperText>Filter by class car</FormHelperText>
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
                                    <em>None</em>
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
                            
                            <label style={{color:'rgba(0, 0, 0, 0.54)', margin:'1rem 1rem'}}>Collision Damage Waiver</label>
                            <input type="checkbox" style={{marginRight:'1rem', marginBottom:'1rem'}} 
                                onClick={(event) => this.checkboxHandler(event, 'CDW')} />

                            {this.rangeSliderRender()}

                            <a className="btn" href="/" style={{width:'150px', textAlign:'center', float:'right',margin:'1rem 4rem'}}
                            onClick={(event) => this.filterHandler(event)}>Filter</a>
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
                                            </div>

                                            <div className="details-rent">
                                                <div>
                                                    <a href="/">More details </a>
                                                </div>
                                                <div>
                                                    <a className="btn" href="/" style={{width:'150px', textAlign:'center'}}>Rent</a>
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