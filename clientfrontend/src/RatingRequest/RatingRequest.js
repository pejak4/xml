import React from 'react';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import axios from '../axios-objects';



class RatingRequest extends React.PureComponent {

    state = {
        ratingRequests: [],
    }

    componentDidMount = async() => {
        const response = await axios.get('/car-service/getAllRatingRequest');
        if(response) {
            this.setState({ratingRequests: response.data})
        }
    }

    acceptHandler = async(event, ratingRequest) => {
        let ratingRequestId = ratingRequest.id;
        const data = {ratingRequestId};
        await axios.post('/car-service/acceptRatingRequest', data)
        window.location.reload();
    }

    declineHandler = async(event, ratingRequest) => {
        let ratingRequestId = ratingRequest.id;
        const data = {ratingRequestId};
        await axios.post('/car-service/declineRatingRequest', data)
        window.location.reload();
    }

    renderRatingRequest() {
        return(
            this.state.ratingRequests.map((ratingRequest, i) => {
                return (
                    <div className="card" key={i}>
                        <div className='containerr'>
                            <h4><b>Rating request: {ratingRequest.id}</b></h4>
                            Car id: {ratingRequest.carId    }
                            <br/><hr/>
                            <br/>
                            <h5><b>Rating: {ratingRequest.rating}</b></h5>
                            <br/><hr/>
                        </div>
                        <button onClick={(event) => {this.acceptHandler(event, ratingRequest)}}>Accept</button>
                        <button onClick={(event) => {this.declineHandler(event, ratingRequest)}}>Decline</button>
                    </div>
                );
            })
        );
}

    render() {
        return (
            <div>
                <HamburgerMenu />
                {/* <Navbar renderHandler={this.renderHandler} /> */}
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderRatingRequest()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default RatingRequest;