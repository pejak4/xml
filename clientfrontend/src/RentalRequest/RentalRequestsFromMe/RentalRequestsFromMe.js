import React from 'react';
import HamburgerMenu from '../../HamburgerMenu/HamburgerMenu';
import axios from '../../axios-objects';
import Navbar from '../Navbar/Navbar';

class RentalRequestsFromMe extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            rentalRequestFromMeReserved: [],
            rentalRequestFromMePaid: [],
            rentalRequestFromMePending: [],
            rentalRequestFromMeCanceled: [],
            renderType: '1'
        }
    }

    componentDidMount = async() => {
        let userEmail = sessionStorage.getItem('userEmail');
        const data = {userEmail};
        const response = await axios.post('/authentication-service/getLoggedUser', data);

        let userId = response.data.id;
        const data01 = {userId};
        const response01 = await axios.post('/car-service/getAllRentalRequestsLoggedUserReserved', data01);
        if(response01) {
            this.setState({rentalRequestFromMeReserved: response01.data})
        }

        const response02 = await axios.post('/car-service/getAllRentalRequestsLoggedUserPaid', data01);
        if(response02) {
            this.setState({rentalRequestFromMePaid: response02.data})
        }

        const response03= await axios.post('/car-service/getAllRentalRequestsLoggedUserPending', data01);
        if(response03) {
            this.setState({rentalRequestFromMePending: response03.data})
        }

        const response04= await axios.post('/car-service/getAllRentalRequestsLoggedUserCanceled', data01);
        if(response04) {
            this.setState({rentalRequestFromMeCanceled: response04.data})
        }
    }

    payHandler = async(event, rentalRequest) => {
        event.preventDefault();

        let rentalRequestId = rentalRequest.id;
        const data00 = {rentalRequestId};
        await axios.post('/car-service/paidRentalRequest', data00);

        //Mislim da mi ovaj deo sa tabelom Rental uopste ne treba
        //Jer u zahtevima za rentanje imam stanja, auta se rentaju ili su zavrsena sa rentanjem ako je stanje Paid
        //Po stanjim mogu sve da pratim
        //await axios.post('/car-service/addRental', data00);

        let carId = rentalRequest.rentalRequestCar.id;
        let startData= rentalRequest.startDate;
        let endData = rentalRequest.endDate;
        let userId = rentalRequest.forUserId;

        const data = {carId, startData, endData, userId};
        await axios.post('/car-service/declineRentalRequestWhenPaid', data);

        window.location.reload();
    }

    declineHandler = async(event, rentalRequest) => {
        let rentalRequestId = rentalRequest.id;
        const data = {rentalRequestId};
        await axios.post('/car-service/declineRentalRequest', data);
        window.location.reload();
    }

    renderRentalRequestPending() {
        if(this.state.renderType === '1') {
            return(
                this.state.rentalRequestFromMePending.map((rentalRequest, i) => {
                    return (
                        <div className="card" key={i}>
                            <div className='containerr'>
                                <h4><b>Rental request: {rentalRequest.id}</b></h4>
                                {rentalRequest.rentalRequestCar.brand} {rentalRequest.rentalRequestCar.model}
                                <br/><hr/>
                                <br/>
                                <h5><b>Start date: {new Date(rentalRequest.startDate).toString().split("GMT")[0]}</b></h5>
                                <h5><b>End date: {new Date(rentalRequest.endDate).toString().split("GMT")[0]}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                            <button onClick={(event) => {this.declineHandler(event, rentalRequest)}}>Decline</button>
                        </div>
                    );
                })
            );
        }
    }

    renderRentalRequestReserved() {
        if(this.state.renderType === '2') {
            return(
                this.state.rentalRequestFromMeReserved.map((rentalRequest, i) => {
                    return (
                        <div className="card" key={i}>
                            <div className='containerr'>
                                <h4><b>Rental request: {rentalRequest.id}</b></h4>
                                {rentalRequest.rentalRequestCar.brand} {rentalRequest.rentalRequestCar.model}
                                <br/><hr/>
                                <br/>
                                <h5><b>Start date: {new Date(rentalRequest.startDate).toString().split("GMT")[0]}</b></h5>
                                <h5><b>End date: {new Date(rentalRequest.endDate).toString().split("GMT")[0]}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                            <button onClick={(event) => {this.payHandler(event, rentalRequest)}}>Pay</button>
                            <button onClick={(event) => {this.declineHandler(event, rentalRequest)}}>Decline</button>
                        </div>
                    );
                })
            );
        }
    }

    renderRentalRequestPaid() {
        if(this.state.renderType === '3') {
            return(
                this.state.rentalRequestFromMePaid.map((rentalRequest, i) => {
                    return (
                        <div className="card" key={i}>
                            <div className='containerr'>
                                <h4><b>Rental request: {rentalRequest.id}</b></h4>
                                {rentalRequest.rentalRequestCar.brand} {rentalRequest.rentalRequestCar.model}
                                <br/><hr/>
                                <br/>
                                <h5><b>Start date: {new Date(rentalRequest.startDate).toString().split("GMT")[0]}</b></h5>
                                <h5><b>End date: {new Date(rentalRequest.endDate).toString().split("GMT")[0]}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                        </div>
                    );
                })
            );
        }
    }

    renderRentalRequestCanceled() {
        if(this.state.renderType === '4') {
            return(
                this.state.rentalRequestFromMeCanceled.map((rentalRequest, i) => {
                    return (
                        <div className="card" key={i}>
                            <div className='containerr'>
                                <h4><b>Rental request: {rentalRequest.id}</b></h4>
                                {rentalRequest.rentalRequestCar.brand} {rentalRequest.rentalRequestCar.model}
                                <br/><hr/>
                                <br/>
                                <h5><b>Start date: {new Date(rentalRequest.startDate).toString().split("GMT")[0]}</b></h5>
                                <h5><b>End date: {new Date(rentalRequest.endDate).toString().split("GMT")[0]}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                        </div>
                    );
                })
            );
        }
    }

    renderHandler = (type) => {
        this.setState({renderType: type})
    }
    
    render() {
        return (
            <div>
                <HamburgerMenu />
                <Navbar renderHandler={this.renderHandler} />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderRentalRequestReserved()}
                            {this.renderRentalRequestPaid()}
                            {this.renderRentalRequestPending()}
                            {this.renderRentalRequestCanceled()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default RentalRequestsFromMe;