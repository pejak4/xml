import React from 'react';
import './Advertisement.css';
import axios from '../axios-objects';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Popup from "reactjs-popup";
import Navbar from '../Navbar/Navbar';

class Advertisement extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            renderNumber: 1,
            renderNumberPricelist: 1,

            pricelists: [],
            
            pricelist: '',
            pricelistId: '',

            monday: 0,
            tuesday: 0,
            wednesday: 0,
            thursday: 0,
            friday: 0,
            saturday: 0,
            sunday: 0,
            cwd01: 0,
            kilometer: 0,

            brand: '',
            model: '',
            fuelType: '',
            transmission: '',
            classCar: '',
            mileage: '',
            doors: '',
            capacitySeats: '',
            capacitySeatsForKids: '',
            price: '',
            cubicCapacity: '',
            horsePower: '',
            fullTankCapacity: '',
            cdw: '',
            gps: '',
            usb: '',
            description: '',
            cityLocation: '',
            plannedMilage: '',
            brands: [],
            models: [],
            fuelTypes: [],
            transmissions: [],
            userAddNumber: null
        }
    }

    componentDidMount = async() => {
        const response = await axios.get('/getAllPricelist');
        if(response){
            console.log(response.data);
            this.setState({pricelists: response.data})
        }
    }

    // componentDidMount = async() => {
    //     const response = await axios.get('/codebook-service/getAllBrand');
    //     if(response){
    //         this.setState({brands: response.data})
    //     }

    //     const response1 = await axios.get('/codebook-service/getAllModels');
    //     if(response1){
    //         this.setState({models: response1.data})
    //     }

    //     const response2 = await axios.get('/codebook-service/getAllTransmissions');
    //     if(response2){
    //         this.setState({transmissions: response2.data})
    //     }

    //     const response3 = await axios.get('/codebook-service/getAllFuelTypes');
    //     if(response3){
    //         this.setState({fuelTypes: response3.data})
    //     }

    //     let userEmail = sessionStorage.getItem('userEmail');
    //     const data = {userEmail};
    //     const response4 = await axios.post('/authentication-service/getLoggedUser', data);
    //     if(response4) {
    //         this.setState({userAddNumber: response4.data.addNumber});
    //     }
    // }

    selectHandler = (event, type) => {
        if(type === 'brand') {
            for(let i=0; i<this.state.brands.length; i++) {
                if(this.state.brands[i].brand === event.target.value) {
                    this.setState({models: this.state.brands[i].modelList})
                }
            }
        }

        if(type === 'brand') {
           this.setState({brand: event.target.value})
        } else if(type === 'model') {
            this.setState({model: event.target.value})
        } else if(type === 'fuelType') {
            this.setState({fuelType: event.target.value})
        } else if(type === 'transmission') {
            this.setState({transmission: event.target.value})
        } else if(type === 'classCar') {
            this.setState({classCar: event.target.value})
        } else if(type === 'mileage') {
            this.setState({mileage: event.target.value})
        } else if(type === 'doors') {
            this.setState({doors: event.target.value})
        } else if(type === 'seats') {
            this.setState({capacitySeats: event.target.value})
        } else if(type === 'seatsForKids') {
            this.setState({capacitySeatsForKids: event.target.value})
        } else if(type === 'price') {
            this.setState({price: event.target.value})
        } else if(type === 'cubicCapacity') {
            this.setState({cubicCapacity: event.target.value})
        } else if(type === 'horsePower') {
            this.setState({horsePower: event.target.value})
        } else if(type === 'fullTankCapacity') {
            this.setState({fullTankCapacity: event.target.value})
        } else if(type === 'cdw') {
            this.setState({cdw: event.target.checked})
        } else if(type === 'gps') {
            this.setState({gps: event.target.checked})
        } else if(type === 'usb') {
            this.setState({usb: event.target.checked})
        } else if(type === 'description') {
            this.setState({description: event.target.value})
        } else if(type === 'cityLocation') {
            this.setState({cityLocation: event.target.value})
        } else if(type === 'plannedMilage') {
            this.setState({plannedMilage: event.target.value})
        } else if(type === 'monday') {
            this.setState({monday: event.target.value})
        } else if(type === 'tuesday') {
            this.setState({tuesday: event.target.value})
        } else if(type === 'wednesday') {
            this.setState({wednesday: event.target.value})
        } else if(type === 'thursday') {
            this.setState({thursday: event.target.value})
        } else if(type === 'friday') {
            this.setState({friday: event.target.value})
        } else if(type === 'saturday') {
            this.setState({saturday: event.target.value})
        } else if(type === 'sunday') {
            this.setState({sunday: event.target.value})
        } else if(type === 'cwd01') {
            this.setState({cwd01: event.target.value}) 
        } else if(type === 'kilometer') {
            this.setState({kilometer: event.target.value})
        } else if(type === 'pricelist') {
            this.setState({pricelistId: event.target.value})
        }
    }

    addHandler = async (event) => {
        event.preventDefault();
            let brand = this.state.brand;
            let model = this.state.model;
            let fuelType = this.state.fuelType;
            let transmission = this.state.transmission;
            let classCar = this.state.classCar;
            let mileage = this.state.mileage;
            let doors = this.state.doors;
            let capacitySeats = this.state.capacitySeats;
            let capacitySeatsForKids = this.state.capacitySeatsForKids;
            let price = 0;
            let cubicCapacity = this.state.cubicCapacity;
            let horsePower = this.state.horsePower;
            let fuelTankCapacity = this.state.fullTankCapacity;
            let cdw = this.state.cdw;
            let gps = this.state.gps;
            let usb = this.state.usb;
            let description = this.state.description;
            let cityLocation = this.state.cityLocation;
            let plannedMileage = this.state.plannedMilage;
            let userId;


            let d = new Date();
            let weekday = new Array(7);
            weekday[0] = "Sunday";
            weekday[1] = "Monday";
            weekday[2] = "Tuesday";
            weekday[3] = "Wednesday";
            weekday[4] = "Thursday";
            weekday[5] = "Friday";
            weekday[6] = "Saturday";

            let n = weekday[d.getDay()];
            console.log(n);

            if(this.state.renderNumberPricelist === -1) {
                if(!this.state.cdw) {
                    if(n === 'Sunday') {
                        price = this.state.sunday;
                    } else if(n === 'Monday') {
                        price = this.state.monday;
                    }else if(n === 'Tuesday') {
                        price = this.state.tuesday;
                    }else if(n === 'Wednesday') {
                        price = this.state.wednesday;
                    }else if(n === 'Thursday') {
                        price = this.state.thursday;
                    }else if(n === 'Friday') {
                        price = this.state.friday;
                    }else if(n === 'Saturday') {
                        price = this.state.saturday;
                    }
                } else {
                    if(n === 'Sunday') {
                        price = this.state.sunday;
                    } else if(n === 'Monday') {
                        price = parseInt(this.state.monday) + parseInt(this.state.cwd01);
                    }else if(n === 'Tuesday') {
                        price = parseInt(this.state.tuesday) + parseInt(this.state.cwd01);
                    }else if(n === 'Wednesday') {
                        price = parseInt(this.state.wednesday) + parseInt(this.state.cwd01);
                    }else if(n === 'Thursday') {
                        price = parseInt(this.state.thursday) + parseInt(this.state.cwd01);
                    }else if(n === 'Friday') {
                        price = parseInt(this.state.friday) + parseInt(this.state.cwd01);
                    }else if(n === 'Saturday') {
                        price = parseInt(this.state.saturday) + parseInt(this.state.cwd01);
                    }
                }
            } else {
                const id = this.state.pricelistId;
                const response = await axios.get('/getPricelistById', {
                    params: {
                        id: id
                    }
                });
                if(response){
                    console.log(response.data);
                    this.setState({pricelist: response.data});
                }

                if(!this.state.cdw) {
                    if(n === 'Sunday') {
                        price = response.data.sunday;
                    } else if(n === 'Monday') {
                        price = response.data.monday;
                    }else if(n === 'Tuesday') {
                        price = response.data.tuesday;
                    }else if(n === 'Wednesday') {
                        price = response.data.wednesday;
                    }else if(n === 'Thursday') {
                        price = response.data.thursday;
                    }else if(n === 'Friday') {
                        price = response.data.friday;
                    }else if(n === 'Saturday') {
                        price = response.data.saturday;
                    }
                } else {
                    if(n === 'Sunday') {
                        price = response.data.sunday;
                    } else if(n === 'Monday') {
                        price = parseInt(response.data.monday) + parseInt(response.data.cdw);
                    }else if(n === 'Tuesday') {
                        price = parseInt(response.data.tuesday) + parseInt(response.data.cdw);
                    }else if(n === 'Wednesday') {
                        price = parseInt(response.data.wednesday) + parseInt(response.data.cdw);
                    }else if(n === 'Thursday') {
                        price = parseInt(response.data.thursday) + parseInt(response.data.cdw);
                    }else if(n === 'Friday') {
                        price = parseInt(response.data.friday) + parseInt(response.data.cdw);
                    }else if(n === 'Saturday') {
                        price = parseInt(response.data.saturday) + parseInt(response.data.cdw);
                    }
                }
            }

            const token = sessionStorage.getItem('token');
            const response00 = await axios.get('/getLoggedUser', {
                    headers: {
                        'Authorization' : 'Bearer ' + token
                    }
                });
            if(response00) {
                userId = response00.data.id;
            }

            const data = {brand, model, fuelType, transmission, classCar, mileage, doors, capacitySeats, capacitySeatsForKids, price, 
                cubicCapacity, horsePower, fuelTankCapacity, cdw, gps, usb, description, cityLocation, plannedMileage, userId};

            const response = await axios.post('/addAdvertisement', data, {
                headers: {
                    'Authorization' : 'Bearer ' + token
                }
            });

            if(response.data) {
                console.log(response.data);
            }

            if(this.state.renderNumberPricelist === -1) {
                const monday = this.state.monday;
                const tuesday = this.state.tuesday;
                const wednesday = this.state.wednesday;
                const thursday = this.state.thursday;
                const friday = this.state.friday;
                const saturday = this.state.saturday;
                const sunday = this.state.sunday;
                const cdw = this.state.cwd01;
                const kilometer = this.state.kilometer;
                const carId = response.data.id;

                const data = {monday, tuesday, wednesday, thursday, friday, saturday, sunday, cdw, kilometer, carId}
                const response01 = await axios.post('/addPricelist', data);

                if(response01) {
                    console.log(response01.data);
                }
            }
            
        
    }

    render01() {
        if(this.state.renderNumber === 1){
        return(
            <div className="wrapper">
                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Brand</InputLabel>
                    <Select
                    value={this.state.brandCars}
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    onChange={(event) => this.selectHandler(event, 'brand')}
                    >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {/* {this.state.brands.map((brand, i) => {
                        return (
                            <MenuItem key={i} value={brand.brand}>{brand.brand}</MenuItem>
                        );
                    })} */}
                    <MenuItem value='BMW'>BMW</MenuItem>

                    </Select>
                    <FormHelperText>Brand of car</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Model</InputLabel>
                    <Select
                    value={this.state.modelCars}
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    onChange={(event) => this.selectHandler(event, 'model')}
                    >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {/* {this.state.models.map((model, i) => {
                        return (
                            <MenuItem key={i} value={model.model}>{model.model}</MenuItem>
                        );
                    })} */}
                    <MenuItem value='X5'>X5</MenuItem>
                    
                    </Select>
                    <FormHelperText>Model of car</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Fuel Type</InputLabel>
                    <Select
                    value={this.state.fuelTypeCars}
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    onChange={(event) => this.selectHandler(event, 'fuelType')}
                    >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {/* {this.state.fuelTypes.map((fuelType, i) => {
                        return (
                            <MenuItem key={i} value={fuelType.fuelType}>{fuelType.fuelType}</MenuItem>
                        );
                    })} */}
                    <MenuItem value='Gasoline'>Gasoline</MenuItem>
                    
                    </Select>
                    <FormHelperText>Fuel type of car</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Transmission</InputLabel>
                    <Select
                        value={this.state.transmissionCars}
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'transmission')}
                        >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        {/* {this.state.transmissions.map((transmission, i) => {
                        return (
                            <MenuItem key={i} value={transmission.transmission}>{transmission.transmission}</MenuItem>
                        ); */}
                        <MenuItem value='Automatic'>Automatic</MenuItem>
                    {/* })} */}
                        
                    </Select>
                    <FormHelperText>Transmission of car</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Class car</InputLabel>
                    <Select
                    value={this.state.classCarCars}
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    onChange={(event) => this.selectHandler(event, 'classCar')}
                    >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    <MenuItem value='SUV'>SUV</MenuItem>
                    <MenuItem value='classic'>Classic</MenuItem>
                    
                    </Select>
                    <FormHelperText>Class of car</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Mileage</InputLabel>
                    <Select
                    value={this.state.mileageCars}
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
                    <FormHelperText>Mileage of car</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Planned mileage</InputLabel>
                    <Select
                    value={this.state.plannedMilage}
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    onChange={(event) => this.selectHandler(event, 'plannedMilage')}
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
                    <FormHelperText>Available distance to travel</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                    <InputLabel id="demo-simple-select-helper-label">Start location</InputLabel>
                    <Select
                    value={this.state.cityLocation}
                    labelId="demo-simple-select-helper-label"
                    id="demo-simple-select-helper"
                    onChange={(event) => this.selectHandler(event, 'cityLocation')}
                    >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    <MenuItem value='Novi Sad'>Novi Sad</MenuItem>
                    <MenuItem value='Beograd'>Beograd</MenuItem>
                    <MenuItem value='Kula'>Kula</MenuItem>
                    <MenuItem value='Zajecar'>Zajecar</MenuItem>
                    <MenuItem value='Nis'>Nis</MenuItem>

                    
                    </Select>
                    <FormHelperText>Start location</FormHelperText>
                </FormControl>

                <FormControl className="select-brand">
                        <InputLabel id="demo-simple-select-helper-label">Fuel tank capacity</InputLabel>
                        <Select
                        value={this.state.fullTankCapacity}
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'fullTankCapacity')}
                        >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value='40'>40</MenuItem>
                        <MenuItem value='45'>45</MenuItem>
                        <MenuItem value='50'>50</MenuItem>
                        <MenuItem value='55'>55</MenuItem>
                        <MenuItem value='60'>60</MenuItem>
                        <MenuItem value='65'>65</MenuItem>
                        <MenuItem value='70'>70</MenuItem>
                        <MenuItem value='75'>75</MenuItem>
                        <MenuItem value='80'>80</MenuItem>
                        <MenuItem value='85'>85</MenuItem>
                        <MenuItem value='90'>90</MenuItem>
                        <MenuItem value='95'>95</MenuItem>
                        
                        </Select>
                        <FormHelperText>Fuel tank capacity</FormHelperText>
                    </FormControl>
            </div>
        );
        }
    }

    render02() {
        if(this.state.renderNumber === 2){
            return(   
                <div className="wrapper">
                    <FormControl className="select-brand">
                        <InputLabel id="demo-simple-select-helper-label">Doors</InputLabel>
                        <Select
                        value={this.state.doors}
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'doors')}
                        >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value='1'>1</MenuItem>
                        <MenuItem value='2'>2</MenuItem>
                        <MenuItem value='3'>3</MenuItem>
                        <MenuItem value='4'>4</MenuItem>
                        <MenuItem value='5'>5</MenuItem>
                        <MenuItem value='6'>6</MenuItem>
                        <MenuItem value='7'>7</MenuItem>
                        <MenuItem value='8'>8</MenuItem>
                        <MenuItem value='9'>9</MenuItem>
                        <MenuItem value='10'>10</MenuItem>
                        
                        </Select>
                        <FormHelperText>Number of doors</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <InputLabel id="demo-simple-select-helper-label">Seats</InputLabel>
                        <Select
                        value={this.state.seats}
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'seats')}
                        >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value='1'>1</MenuItem>
                        <MenuItem value='2'>2</MenuItem>
                        <MenuItem value='3'>3</MenuItem>
                        <MenuItem value='4'>4</MenuItem>
                        <MenuItem value='5'>5</MenuItem>
                        <MenuItem value='6'>6</MenuItem>
                        <MenuItem value='7'>7</MenuItem>
                        <MenuItem value='8'>8</MenuItem>
                        <MenuItem value='9'>9</MenuItem>
                        <MenuItem value='10'>10</MenuItem>
                        </Select>
                        <FormHelperText>Number of seats</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <InputLabel id="demo-simple-select-helper-label">Seats for kids</InputLabel>
                        <Select
                        value={this.state.seatsForKids}
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'seatsForKids')}
                        >
                        <MenuItem value="0">
                            <em>None</em>
                        </MenuItem>
                        <MenuItem value='1'>1</MenuItem>
                        <MenuItem value='2'>2</MenuItem>
                        <MenuItem value='3'>3</MenuItem>
                        
                        </Select>
                        <FormHelperText>Number of seats for kids</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input 
                        value={this.state.price}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'price')}
                        ></input>
                        <FormHelperText>Car price</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.cubicCapacity}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'cubicCapacity')}></input>
                        <FormHelperText>Cubic capacity of car</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.horsePower}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'horsePower')}></input>
                        <FormHelperText>Horse power of car</FormHelperText>
                    </FormControl>

                    <form style={{gridColumn:'1/4'}}>
                        <textarea value={this.state.description} className="textarea" placeholder="Description ..."
                        onChange={(event) => this.selectHandler(event, 'description')} ></textarea>
                    </form>
                </div>
        );
        }
    }

    render03() {
        if(this.state.renderNumber === 3){
            return(
                <div className="wrapper">
                    <FormControl className="select-brand">
                        <label style={{color:'rgba(0, 0, 0, 0.54)', margin:'1rem 1rem'}}>Collision Damage Waiver</label>
                        <input type="checkbox" style={{alignSelf:'center'}} 
                            onChange={(event) => this.selectHandler(event, 'cdw')} 
                            checked={this.state.cdw}/>
                    </FormControl>

                    <FormControl className="select-brand">
                        <label style={{color:'rgba(0, 0, 0, 0.54)', margin:'1rem 1rem'}}>GPS</label>
                        <input type="checkbox" style={{alignSelf:'center'}} 
                            onChange={(event) => this.selectHandler(event, 'gps')} 
                            checked={this.state.gps}/>
                    </FormControl>

                    <FormControl className="select-brand">
                        <label style={{color:'rgba(0, 0, 0, 0.54)', margin:'1rem 1rem'}}>USB</label>
                        <input type="checkbox" style={{alignSelf:'center'}} 
                            onChange={(event) => this.selectHandler(event, 'usb')} 
                            checked={this.state.usb}/>
                    </FormControl>
                </div>
            );
        }
    }

    render04() {
        if(this.state.renderNumber === 4 && this.state.renderNumberPricelist === -1){
            return(   
                <div className="wrapper">
                    <FormControl className="select-brand">
                        <input 
                        value={this.state.monday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'monday')}
                        ></input>
                        <FormHelperText>Monday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.tuesday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'tuesday')}></input>
                        <FormHelperText>Tuesday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.wednesday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'wednesday')}></input>
                        <FormHelperText>Wednesday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input 
                        value={this.state.thursday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'thursday')}
                        ></input>
                        <FormHelperText>Thursday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.friday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'friday')}></input>
                        <FormHelperText>Friday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.saturday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'saturday')}></input>
                        <FormHelperText>Saturday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.sunday}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'sunday')}></input>
                        <FormHelperText>Sunday</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.cwd01}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'cwd01')}></input>
                        <FormHelperText>CWD</FormHelperText>
                    </FormControl>

                    <FormControl className="select-brand">
                        <input
                        value={this.state.kilometer}
                        type="number"
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'kilometer')}></input>
                        <FormHelperText>Kilometer</FormHelperText>
                    </FormControl>
                </div>
        );
        }
    }


    render05() {
        if(this.state.renderNumber === 4 && this.state.renderNumberPricelist === 1){
            return(   
                <div>
                    <FormControl className="select-brand">
                        <InputLabel id="demo-simple-select-helper-label">Pricelists</InputLabel>
                        <Select
                        value={this.state.pricelistId}
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        onChange={(event) => this.selectHandler(event, 'pricelist')}
                        >
                        <MenuItem value="">
                            <em>None</em>
                        </MenuItem>
                        {this.state.pricelists.map((pricelist, i) => {
                            return (
                                <MenuItem key={i} value={pricelist.id}>{pricelist.id}</MenuItem>
                            );
                        })}

                        </Select>
                        <FormHelperText>Pricelists</FormHelperText>
                    </FormControl>
                </div>
        );
        }
    }


    render() {
        return (
            <div>
                <Navbar />
                <br/>
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="filterDiv">

                            {this.state.renderNumber !== 4 ? <p style={{color:'red'}}>Add new advertisement <br/>Step {this.state.renderNumber}</p> : <p style={{color:'red'}}>Add pricelist <br/>Step {this.state.renderNumber}</p> }
                            <hr/>
                                {this.state.renderNumber === 4 ? <div><button className="button" onClick={(event) => {this.setState({renderNumberPricelist: 1})}}>Add exist</button><button className="button" onClick={(event) => {this.setState({renderNumberPricelist: -1})}}>Add new</button></div> : null }

                            <hr/>
                            <div style={{marginLeft: '0px'}}>
                                {this.render01()}
                                {this.render02()}
                                {this.render03()}
                                {this.render04()}
                                {this.render05()}
                            </div>
                        </div>
                        <div>
                            {this.state.renderNumber > 1 ? <button className="button" onClick={(event) => {this.setState({renderNumber: this.state.renderNumber-1})}}>Back</button> : <button disabled={true} style={{cursor: 'not-allowed', opacity: 0.6}} className="button" onClick={(event) => {this.setState({renderNumber: this.state.renderNumber-1})}}>Back</button>}
                            {this.state.renderNumber < 4 ? <button className="button" onClick={(event)=>{ this.setState({renderNumber: this.state.renderNumber+1});}}>Next</button> : <button disabled={true} style={{cursor: 'not-allowed', opacity: 0.6}} className="button" onClick={(event)=>{ this.setState({renderNumber: this.state.renderNumber+1});}}>Next</button>}
                        </div>

                        {this.state.userAddNumber < 3 ? 
                            (this.state.renderNumber === 4 ? <button className="button" onClick = {(event) => this.addHandler(event)}>Finish</button> : null) 
                            : (this.state.renderNumber === 4 ? <Popup trigger={<button className="button" onClick = {(event) => this.addHandler(event)}>Finish</button>} position="top center">
                                <div style={{background:'red', marginLeft:'0px'}}>You can't add more then 3 advertisement!</div>
                            </Popup> : null)}
                    </div>
                </header>
            </div>
        );
    }
}

export default Advertisement;