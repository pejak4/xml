import React from 'react';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import axios from '../axios-objects';
import './RentalRequest.css';

class RentalRequest extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            rentalRequests: [],
            renderDetail: false,
            curretnRentalRequest: [],
        }
    }

    componentDidMount = async() => {
        let userId;
        let userEmail = sessionStorage.getItem('userEmail');
        const data00 = {userEmail};
        const response00 = await axios.post('/authentication-service/getLoggedUser', data00);
        if(response00) {
            userId = response00.data.id;
        }

        let allRentalRequestsLogedUser = [] ;
        const data01 = {userId}; //Vratice auto koji sadrzi id ulogovanog korisnika.
        //A Car u sebi sadrzi listu zahteva za auto koji je stavio ulogovani korisnik
        //Zbor svih zahteva je su zahtevi ka ulogovanom korisniku
        const response = await axios.post('/car-service/getAllRentalRequestsForUser', data01);
        if(response) {
            console.log(response.data);
            this.setState({rentalRequests: response.data});
        }       
    }

    renderRentalRequest() {
        if(!this.state.renderDetail) {
            return this.state.rentalRequests.map((rentalRequest, i) => {
                return(
                    <div className="card" key={i} id={i} onClick={(event) => {this.setState({renderDetail: true, curretnRentalRequest: rentalRequest})}}>
                        <div className='containerr'>
                            <h4><b>Rental request: ID {rentalRequest.id}</b></h4>
                        </div>
                    </div>
                );
            });
        }
    }

    acceptHandler = async() => {
        let forUserId = this.state.curretnRentalRequest.forUserId;
        let startData = this.state.curretnRentalRequest.startDate;
        let endData = this.state.curretnRentalRequest.endDate;
        let userId = this.state.curretnRentalRequest.userId;
        let rentalRequestId = this.state.curretnRentalRequest.id;

        let haveReserved = false;
        const data = {forUserId, startData, endData, userId, rentalRequestId}
        const response = await axios.post('/car-service/ifHaveReservedRentalRequest', data);
        if(response) {
            console.log(response.data);
            haveReserved = response.data;
        }

        const data1 = {rentalRequestId};
        if(haveReserved) {
            alert('This car is reserved');
        }
        else {
            const response1 = await axios.post('/car-service/acceptRentalRequest', data1);
            if(response1) {
                console.log(response1.data);
            }
            window.location.reload();
        }
    }

    declineHandler = async() => {
        console.log(this.state.curretnRentalRequest);

        let rentalRequestId = this.state.curretnRentalRequest.id;

        const data = {rentalRequestId};

        const response = await axios.post('/car-service/declineRentalRequest', data);
        if(response) {
            console.log(response.data);
        }
        window.location.reload();
    }
    
    renderRentalRequestDetail() {
        if(this.state.renderDetail)
            return(
                    <div className="card">
                        <div className='containerr'>
                            <h4><b>Rental request: {this.state.curretnRentalRequest.id}</b></h4>
                            <br/><hr/>
                            <h5><b>User: ID {this.state.curretnRentalRequest.userId}</b></h5>
                            <h5><b>Start date: {this.state.curretnRentalRequest.startDate}</b></h5>
                            <h5><b>End date: {this.state.curretnRentalRequest.endDate}</b></h5>
                        </div>
                        <button onClick={(event) => {this.setState({renderDetail: false})}}>Back</button>
                        <button onClick={(event) => {this.acceptHandler()}}>Accept</button>
                        <button onClick={(event) => {this.declineHandler()}}>Decline</button>
                    </div>
            );
        }

    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderRentalRequest()}
                            {this.renderRentalRequestDetail()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default RentalRequest;