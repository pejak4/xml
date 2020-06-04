import React from 'react';
import Navbar from './Navbar/Navbar';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import './Codebook.css';
import carImg from '../img/car.jpg';
import axios from '../axios-objects';

class Codebook extends React.PureComponent {

    state = {
        renderNumber: 1,
        type: 'brand',
        brandId: '',
        inputData: '',
        brands: [],
        transmissions: [],
        fuelTypes: [],
    }

    componentDidMount = async () => {
        const response = await axios.get('/codebook-service/getAllBrand');
        if(response){
            this.setState({brands: response.data})
        }

        const response2 = await axios.get('/codebook-service/getAllTransmissions');
        if(response2){
            this.setState({transmissions: response2.data})
        }

        const response3 = await axios.get('/codebook-service/getAllFuelTypes');
        if(response3){
            this.setState({fuelTypes: response3.data})
        }
    }

    clickBrandHandler(brand){
        this.setState({renderNumber: 2, models: brand.modelList, type: 'model', brandId: brand.id})
    }

    clickDelete = async(type, id) => {
        const data = {id};

        if(type==='brand'){
            await axios.post('/codebook-service/deleteBrand', data);
        } else if (type==='model'){
            await axios.post('/codebook-service/deleteModel', data);
        } else if(type==='transmission'){
            await axios.post('/codebook-service/deleteTransmission', data);
        } else if(type==='fuelType') {
            await axios.post('/codebook-service/deleteFuelType', data);
        }

        window.location.reload(false);
    }

    renderHandler = (type) => {
        if(type === 'jedan') {
            this.setState({renderNumber: 1, type:'brand'});
        }
        else if(type === 'dva') {
            this.setState({renderNumber: 2, type:'model'});
        }
        else if(type === 'tri') {
            this.setState({renderNumber: 3, type:'fuel type'});
        }
        else if(type === 'cetiri') {
            this.setState({renderNumber: 4, type:'transmission'});
        } 
        else if(type === 'pet') {
            this.setState({renderNumber: 5});
        }
    }

    renderBrand() {
        if(this.state.renderNumber === 1) {
            return this.state.brands.map((brand, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={carImg} alt='Car' style={{width:"100%"}} onClick={(event) => {this.clickBrandHandler(brand)}}/>
                        <div className="containerr" onClick={(event) => {this.clickBrandHandler(brand)}}>
                            <h4><b>Brand</b></h4> 
                            <p>{brand.brand}</p> 
                        </div>
                        <button className="but" onClick={(event) => {this.clickDelete('brand', brand.id)}}>Delete this brand</button>
                    </div> 
                );
            });
        }
    }

    renderModel() {
        if(this.state.renderNumber === 2) {
            return this.state.models.map((model, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={carImg} alt='Car' style={{width:"100%"}}/>
                        <div className="containerr">
                            <h4><b>Model</b></h4> 
                            <p>{model.model}</p> 
                        </div>
                        <button className="but" onClick={(event) => {this.clickDelete('model', model.id)}}>Delete this model</button>
                    </div> 
                );
            });
        }
    }

    renderTransmission() {
        if(this.state.renderNumber === 4) {
            return this.state.transmissions.map((transmission, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={carImg} alt='Car' style={{width:"100%"}}/>
                        <div className="containerr">
                            <h4><b>Transmision</b></h4> 
                            <p>{transmission.transmission}</p> 
                        </div>
                        <button className="but" onClick={(event) => {this.clickDelete('transmission', transmission.id)}}>Delete this transmission</button>
                    </div> 
                );
            });
        }
    }

    renderFuelType() {
        if(this.state.renderNumber === 3) {
            return this.state.fuelTypes.map((fuelType, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={carImg} alt='Car' style={{width:"100%"}}/>
                        <div className="containerr">
                            <h4><b>Fuel type</b></h4> 
                            <p>{fuelType.fuelType}</p> 
                        </div>
                        <button className="but" onClick={(event) => {this.clickDelete('fuelType', fuelType.id)}}>Delete this fuel type</button>
                    </div> 
                );
            });
        }
    }

    createNew = async(data) => {
        if(this.state.type === 'brand') {
            let id = this.state.inputData;
            const data = {id};
            await axios.post('/codebook-service/createNewBrand', data);
        } else if(this.state.type === 'model') {
            let model = this.state.inputData;
            let brandId = this.state.brandId;
            const data = {model, brandId};            
            await axios.post('/codebook-service/createNewModel', data);
        } else if(this.state.type === 'fuel type') {
            let id = this.state.inputData;
            const data = {id};
            await axios.post('/codebook-service/createNewFuelType', data);
        } else if(this.state.type === 'transmission') {
            let id = this.state.inputData;
            const data = {id};
            await axios.post('/codebook-service/createNewTransmission', data);
        }
        window.location.reload(false);
    }

    inputHandler = (event) => {
        this.setState({inputData: event.target.value});
    }

    renderCreateNew() {
        if(this.state.renderNumber === 5) {
            return(
                <div className="card centered" style={{ justifyContent: 'center', alignItems: 'center'}}>
                    <div className="containerr">
                        <h4><b>Create new {this.state.type}</b></h4> 
                        <input type='text' onChange={(event) => this.inputHandler(event)}></input>
                    </div>
                    <button className="but" onClick={(event) => {this.createNew()}}>Create</button>
                </div> 
            );
        }
    }

    render() {
        return(
            <div>
                <Navbar renderHandler={this.renderHandler} type={this.state.type}/>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderBrand()}
                            {this.renderModel()}
                            {this.renderTransmission()}
                            {this.renderFuelType()}
                            {this.renderCreateNew()}
                            <br/>
                        </div>
                    </div>
            </header>
            </div>
        );
    }
}

export default Codebook;