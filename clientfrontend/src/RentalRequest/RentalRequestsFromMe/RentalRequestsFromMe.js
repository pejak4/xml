import React from 'react';
import HamburgerMenu from '../../HamburgerMenu/HamburgerMenu';
import axios from '../../axios-objects';


class RentalRequestsFromMe extends React.PureComponent {

    state = {
        rentalRequestFromMe: [],
    }

    componentDidMount = async() => {
        let userEmail = sessionStorage.getItem('userEmail');
        const data = {userEmail};
        const response = await axios.post('/authentication-service/getLoggedUser', data);

        let userId = response.data.id;
        const data1 = {userId};
        const response1 = await axios.post('/car-service/getAllRentalRequestsLoggedUser', data1);
        if(response1) {
            console.log(response1.data)
            this.setState({rentalRequestFromMe: response1.data})
        }
    }

    payHandler = async(event, rentalRequest) => {
        event.preventDefault();

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
    }

    renderRentalRequest() {
        return(
            this.state.rentalRequestFromMe.map((rentalRequest, i) => {
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

    
    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderRentalRequest()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default RentalRequestsFromMe;