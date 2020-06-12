import React from 'react';
import HamburgerMenu from '../../HamburgerMenu/HamburgerMenu';
import axios from '../../axios-objects';
import Navbar from '../Navbar/Navbar';


class RentalRequestsFromMe extends React.PureComponent {

    state = {
        rentalRequestFromMeReserved: [],
        rentalRequestFromMePaid: [],
        rentalRequestFromMePending: [],
        rentalRequestFromMeCanceled: [],


        renderType: '1',
    }

    componentDidMount = async() => {
        let userEmail = sessionStorage.getItem('userEmail');
        const data = {userEmail};
        const response = await axios.post('/authentication-service/getLoggedUser', data);

        let userId = response.data.id;
        const data01 = {userId};
        const response01 = await axios.post('/car-service/getAllRentalRequestsLoggedUserReserved', data01);
        if(response01) {
            console.log(response01.data)
            this.setState({rentalRequestFromMeReserved: response01.data})
        }

        const response02 = await axios.post('/car-service/getAllRentalRequestsLoggedUserPaid', data01);
        if(response02) {
            console.log(response02.data)
            this.setState({rentalRequestFromMePaid: response02.data})
        }

        const response03= await axios.post('/car-service/getAllRentalRequestsLoggedUserPending', data01);
        if(response03) {
            console.log(response03.data)
            this.setState({rentalRequestFromMePending: response03.data})
        }

        const response04= await axios.post('/car-service/getAllRentalRequestsLoggedUserCanceled', data01);
        if(response04) {
            console.log(response04.data)
            this.setState({rentalRequestFromMeCanceled: response04.data})
        }
    }

    payHandler = async(event, rentalRequest) => {
        event.preventDefault();

        let rentalRequestId = rentalRequest.id;
        const data00 = {rentalRequestId};
        const response00 = await axios.post('/car-service/paidRentalRequest', data00);
        if(response00){
            console.log(response00.data);
        }

        const response01 = await axios.post('/car-service/addRental', data00);
        if(response01) {
            console.log(response01.data);
        }

        let carId = rentalRequest.rentalRequestCar.id;
        let startData= rentalRequest.startDate;
        let endData = rentalRequest.endDate;
        let userId = rentalRequest.forUserId;

        const data = {carId, startData, endData, userId};
        console.log(data);
        const response = await axios.post('/car-service/declineRentalRequestWhenPaid', data);
        if(response) {
            console.log(response.data);
        }

        window.location.reload();
    }

    declineHandler = async(event, rentalRequest) => {
        console.log(rentalRequest);
        let rentalRequestId = rentalRequest.id;
        const data = {rentalRequestId};
        const response = await axios.post('/car-service/declineRentalRequest', data);
        if(response) {
            console.log(response);
        }
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
                                <h5><b>Start date: {rentalRequest.startDate}</b></h5>
                                <h5><b>End date: {rentalRequest.endDate}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                            {/* <button onClick={(event) => {this.payHandler(event, rentalRequest)}}>Pay</button> */}
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
                                <h5><b>Start date: {rentalRequest.startDate}</b></h5>
                                <h5><b>End date: {rentalRequest.endDate}</b></h5>
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
                                <h5><b>Start date: {rentalRequest.startDate}</b></h5>
                                <h5><b>End date: {rentalRequest.endDate}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                            {/* <button onClick={(event) => {this.payHandler(event, rentalRequest)}}>Pay</button>
                            <button onClick={(event) => {this.declineHandler(event, rentalRequest)}}>Decline</button> */}
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
                                <h5><b>Start date: {rentalRequest.startDate}</b></h5>
                                <h5><b>End date: {rentalRequest.endDate}</b></h5>
                                <br/><hr/>
                                <p><b>{rentalRequest.rentalRequestCar.price}$</b></p>
                            </div>
                            {/* <button onClick={(event) => {this.payHandler(event, rentalRequest)}}>Pay</button>
                            <button onClick={(event) => {this.declineHandler(event, rentalRequest)}}>Decline</button> */}
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