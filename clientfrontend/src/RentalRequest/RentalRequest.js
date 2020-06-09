import React from 'react';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import axios from '../axios-objects';
import './RentalRequest.css';

class RentalRequest extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            rentalRequests: [],
            renderDetail: false,
            currentRentalRequest: [],
            carsLogedUser: [],
            user: null
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
        const response = await axios.post('/car-service/getCarsLogedUser', data01);
        if(response) {
            if(response.data !== "") {
                for(let i=0; i<response.data.length; i++) {
                    allRentalRequestsLogedUser = allRentalRequestsLogedUser.concat(response.data[i].rentalRequestsList);
                }
            }
            this.setState({carsLogedUser: response.data, rentalRequests: allRentalRequestsLogedUser});
        }       
    }

    getUserById = async (id) => {
        const response = await axios.get('/authentication-service/getUserById', {
            params: {
                id: id
            }
        });
        if(response) {
            this.setState({user: response.data});
        }
    }

    renderRentalRequest = () => {
        if(!this.state.renderDetail) {
            return this.state.rentalRequests.map((rentalRequest, i) => {
                return(
                    <div className="card" key={i} id={i} onClick={(event) => {
                        this.getUserById(rentalRequest.userId);
                        this.setState({renderDetail: true, currentRentalRequest: rentalRequest})}}>
                        <div className='containerr'>
                            <h4><b>Rental request: ID {rentalRequest.id}</b></h4>
                        </div>
                    </div>
                );
            });
        }
    }

    acceptHandler = async() => {
        let rentalRequestId = this.state.currentRentalRequest.id;

        const data = {rentalRequestId};

        await axios.post('/car-service/acceptRentalRequest', data);
        window.location.reload();
    }

    declineHandler = async() => {
        let rentalRequestId = this.state.currentRentalRequest.id;

        const data = {rentalRequestId};

        await axios.post('/car-service/declineRentalRequest', data);
        window.location.reload();
    }
    
    renderRentalRequestDetail() {
        if(this.state.renderDetail) {
            return(
                <div className="card">
                    <div className='containerr'>
                        <h4><b>Rental request: </b></h4>
                        <br/><hr/>
                        {this.state.user !== null ? <h5><b>User: {this.state.user.firstName} {this.state.user.lastName}</b></h5> : null}
                        <h5><b>Start date: {this.state.currentRentalRequest.startDate.split("T")[0]} {this.state.currentRentalRequest.startDate.split("T")[1].split(".")[0]}</b></h5>
                        <h5><b>End date: {this.state.currentRentalRequest.endDate.split("T")[0]} {this.state.currentRentalRequest.endDate.split("T")[1].split(".")[0]}</b></h5>
                    </div>
                    <button className="btn" onClick={(event) => {this.setState({renderDetail: false})}}>Back</button>
                    <button className="btn" style={{margin:'0 1rem'}} onClick={(event) => {this.acceptHandler()}}>Accept</button>
                    <button className="btn" onClick={(event) => {this.declineHandler()}}>Decline</button>
                </div>
            );
        }
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