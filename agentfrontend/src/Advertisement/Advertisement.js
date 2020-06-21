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
        }else if(type === 'seats') {
            this.setState({capacitySeats: event.target.value})
        }else if(type === 'seatsForKids') {
            this.setState({capacitySeatsForKids: event.target.value})
        } else if(type === 'price') {
            this.setState({price: event.target.value})
        }else if(type === 'cubicCapacity') {
            this.setState({cubicCapacity: event.target.value})
        }else if(type === 'horsePower') {
            this.setState({horsePower: event.target.value})
        } else if(type === 'fullTankCapacity') {
            this.setState({fullTankCapacity: event.target.value})
        }else if(type === 'cdw') {
            this.setState({cdw: event.target.checked})
        }else if(type === 'gps') {
            this.setState({gps: event.target.checked})
        } else if(type === 'usb') {
            this.setState({usb: event.target.checked})
        } else if(type === 'description') {
            this.setState({description: event.target.value})
        } else if(type === 'cityLocation') {
            this.setState({cityLocation: event.target.value})
        } else if(type === 'plannedMilage') {
            this.setState({plannedMilage: event.target.value})
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
            let price = this.state.price;
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

            // const dataUserUpdate = {userEmail};
            // if(response){
            //     const responseUserUpdate = await axios.put('/authentication-service/incrementAddNumber', dataUserUpdate);
            //     if (responseUserUpdate) {
            //         window.location.reload();
            //     }
            // }
        
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

    render() {
        return (
            <div>
                <Navbar />
                <br/>
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="filterDiv">
                            <p style={{color:'red'}}>Add new advertisement <br/>Step {this.state.renderNumber}</p>
                            <hr/>
                            <div style={{marginLeft: '0px'}}>
                                {this.render01()}
                                {this.render02()}
                                {this.render03()}
                            </div>
                        </div>
                        <div>
                            {this.state.renderNumber > 1 ? <button className="button" onClick={(event) => {this.setState({renderNumber: this.state.renderNumber-1})}}>Back</button> : <button disabled={true} style={{cursor: 'not-allowed', opacity: 0.6}} className="button" onClick={(event) => {this.setState({renderNumber: this.state.renderNumber-1})}}>Back</button>}
                            {this.state.renderNumber < 3 ? <button className="button" onClick={(event)=>{ this.setState({renderNumber: this.state.renderNumber+1});}}>Next</button> : <button disabled={true} style={{cursor: 'not-allowed', opacity: 0.6}} className="button" onClick={(event)=>{ this.setState({renderNumber: this.state.renderNumber+1});}}>Next</button>}
                        </div>

                        {this.state.userAddNumber < 3 ? 
                            (this.state.renderNumber === 3 ? <button className="button" onClick = {(event) => this.addHandler(event)}>Finish</button> : null) 
                            : (this.state.renderNumber === 3 ? <Popup trigger={<button className="button" onClick = {(event) => this.addHandler(event)}>Finish</button>} position="top center">
                                <div style={{background:'red', marginLeft:'0px'}}>You can't add more then 3 advertisement!</div>
                            </Popup> : null)}
                    </div>
                </header>
            </div>
        );
    }
}

export default Advertisement;