import React from 'react';
import axios from '../axios-objects';
import './RentalRequest.css';
import Navbar from '../Navbar/Navbar';
import Downbar from '../Navbar/DownBar';
import './RentalRequest.css';

class RentalRequest extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            rentalRequestsReserved: [],
            rentalRequestsPaid: [],
            rentalRequestsCanceled: [],
            rentalRequestsPending: [],
            renderDetail: false,
            currentRentalRequest: [],
            carsLogedUser: [],
            renderType: '1',
            blockButton: false
        }
    }

    componentDidMount = async() => {
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

        const data01 = {userId}; 
        const response01 = await axios.post('/getAllRentalRequestsForUserReserved', data01);
        if(response01) {
            console.log(response01.data);
            this.setState({rentalRequestsReserved: response01.data});
        }       

        const response02 = await axios.post('/getAllRentalRequestsForUserPaid', data01);
        if(response02) {
            console.log(response02.data);
            this.setState({rentalRequestsPaid: response02.data});
        }

        const response03 = await axios.post('/getAllRentalRequestsForUserCanceled', data01);
        if(response03) {
            console.log(response03.data);
            this.setState({rentalRequestsCanceled: response03.data});
        }

        const response04 = await axios.post('/getAllRentalRequestsForUserPending', data01);
        if(response04) {
            console.log(response04.data);
            this.setState({rentalRequestsPending: response04.data});
        }
    }

    renderRentalRequestReserved = () => {
        if(!this.state.renderDetail && this.state.renderType==='2') {
            return this.state.rentalRequestsReserved.map((rentalRequest, i) => {
                return(
                    <div className="card" key={i} id={i} onClick={(event) => {
                        this.setState({renderDetail: true, currentRentalRequest: rentalRequest, blockButton: true})}}>
                        <div className='containerr'>
                            <h4><b>Rental request: ID {rentalRequest.id}</b></h4>
                        </div>
                    </div>
                );
            });
        }
    }

    renderRentalRequestPending = () => {
        if(!this.state.renderDetail && this.state.renderType==='1') {
            return this.state.rentalRequestsPending.map((rentalRequest, i) => {
                return(
                    <div className="card" key={i} id={i} onClick={(event) => {
                        this.setState({renderDetail: true, currentRentalRequest: rentalRequest, blockButton: true})}}>
                        <div className='containerr'>
                            <h4><b>Rental request: ID {rentalRequest.id}</b></h4>
                        </div>
                    </div>
                );
            });
        }
    }

    renderRentalRequestPaid = () => {
        if(!this.state.renderDetail && this.state.renderType==='3') {
            return this.state.rentalRequestsPaid.map((rentalRequest, i) => {
                return(
                    <div className="card" key={i} id={i} onClick={(event) => {
                        this.setState({renderDetail: true, currentRentalRequest: rentalRequest, blockButton: true})}}>
                        <div className='containerr'>
                            <h4><b>Rental request: ID {rentalRequest.id}</b></h4>
                        </div>
                    </div>
                );
            });
        }
    }

    renderRentalRequestCanceled = () => {
        if(!this.state.renderDetail && this.state.renderType==='4') {
            return this.state.rentalRequestsCanceled.map((rentalRequest, i) => {
                return(
                    <div className="card" key={i} id={i} onClick={(event) => {
                        this.setState({renderDetail: true, currentRentalRequest: rentalRequest, blockButton: true})}}>
                        <div className='containerr'>
                            <h4><b>Rental request: ID {rentalRequest.id}</b></h4>
                        </div>
                    </div>
                );
            });
        }
    }

    acceptHandler = async() => {
        let forUserId = this.state.currentRentalRequest.forUserId;
        let startData = this.state.currentRentalRequest.startDate;
        let endData = this.state.currentRentalRequest.endDate;
        let userId = this.state.currentRentalRequest.userId;
        let rentalRequestId = this.state.currentRentalRequest.id;

        let haveReserved = false;
        const data = {forUserId, startData, endData, userId, rentalRequestId}
        const response = await axios.post('/ifHaveReservedRentalRequest', data);
        if(response) {
            console.log(response.data);
            haveReserved = response.data;
        }

        const data1 = {rentalRequestId};
        if(haveReserved) {
            alert('This car is reserved');
        }
        else {
            const response1 = await axios.post('/acceptRentalRequest', data1);
            if(response1) {
                console.log(response1.data);
            }
            window.location.reload();
        }
    }

    declineHandler = async() => {
        let rentalRequestId = this.state.currentRentalRequest.id;

        const data = {rentalRequestId};

        await axios.post('/declineRentalRequest', data);
        window.location.reload();
    }
    
    renderRentalRequestDetail() {
        if(this.state.renderDetail) {
            return(
                <div className="card">
                    <div className='containerr'>
                        <h4><b>Rental request: </b></h4>
                        <br/><hr/>
                        <h5><b>Start date: {this.state.currentRentalRequest.startDate} </b></h5>
                        <h5><b>End date: {this.state.currentRentalRequest.endDate} </b></h5>
                    </div>
                    <button className="btn" onClick={(event) => {this.setState({renderDetail: false, blockButton: false})}}>Back</button>
                    {this.state.renderType === '1' ? <button className="btn" style={{margin:'0 1rem'}} onClick={(event) => {this.acceptHandler()}}>Accept</button> : null}
                    {this.state.renderType === '1' ? <button className="btn" onClick={(event) => {this.declineHandler()}}>Decline</button> : null}
                </div>
            );
        }
    }

    renderHandler = (type) => {
        this.setState({renderType: type});
    }

    render() {
        return (
            <div>
                <Navbar/>
                <Downbar  renderHandler={this.renderHandler} blockButton={this.state.blockButton}/>
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a">
                            {this.renderRentalRequestReserved()}
                            {this.renderRentalRequestCanceled()}
                            {this.renderRentalRequestPaid()}
                            {this.renderRentalRequestPending()}

                            {this.renderRentalRequestDetail()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default RentalRequest;