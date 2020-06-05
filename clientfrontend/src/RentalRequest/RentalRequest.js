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
            carsLogedUser: []
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