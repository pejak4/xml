import React from 'react';
import axios from '../axios-objects';
// import {updateObject} from '../utility';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import './SingleCarPage.css';

class SingleCarPage extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            car: null,
            descriptionComment: 'Comment',

            comments: [],
        }
    }

    componentDidMount = async () => {
        const id = this.props.carId;
        const response = await axios.get('/car-service/getSingleCar', {
            params: {
                id: id
            }
        });
        if (response) {
            this.setState({car: response.data});
        }

        const carId = this.props.carId;
        const data = {carId};

        const response01 = await axios.post('/car-service/getAllCommentByCarId', data);
        if (response01) {
            this.setState({comments: response01.data});
        }


    }

    commentChange = (comment) => {
        this.setState({descriptionComment: comment});
    }

    commentHandler = async(event) => {
        let carId = this.props.carId;

        let fromUserId;
        let userEmail = sessionStorage.getItem('userEmail');
        const data00 = {userEmail};
        const response00 = await axios.post('/authentication-service/getLoggedUser', data00);
        if(response00) {
            fromUserId = response00.data.id;
        }

        let data01 = {fromUserId, carId}
        const response01 = await axios.post('/car-service/checkRentalRating', data01);

        if(response01.data === false) {
            alert('Can not comment this car.');
        } else {
            let descriptionComment = this.state.descriptionComment;
            let data02 = {fromUserId, carId, descriptionComment};
            await axios.post('/car-service/addCommentCarRequest', data02);
        }
    }

    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="container showcase-container">
                        <div className="single-car" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            <h2>{this.state.car !== null ? this.state.car.brand : null} {" "}
                            {this.state.car !== null ? this.state.car.model : null} </h2>
                            <div className="car-wrapper">
                                <div className="description">
                                    <p className="back" onClick={(event) => {this.props.setNumOfRender('jedan')}}>BACK</p>
                                    <p><strong>Fuel type</strong>: {this.state.car !== null ? this.state.car.fuelType : null}</p>
                                    <p><strong>Transmission</strong>: {this.state.car !== null ? this.state.car.transmission : null}</p>
                                    <p><strong>Class</strong>: {this.state.car !== null ? this.state.car.classCar : null}</p>
                                    <p><strong>Price(per day)</strong>: {this.state.car !== null ? this.state.car.price : null}$</p>
                                    <p><strong>Mileage</strong>: {this.state.car !== null ? this.state.car.mileage : null}km</p>
                                    <p><strong>Seats</strong>: {this.state.car !== null ? this.state.car.capacitySeats : null}</p>
                                    <p><strong>Seats for kids</strong>: {this.state.car !== null ? this.state.car.capacitySeatsForKids : null}</p>
                                    <p><strong>Horse power</strong>: {this.state.car !== null ? this.state.car.horsePower : null}</p>
                                    <p><strong>Location</strong>: {this.state.car !== null ? this.state.car.cityLocation : null}</p>
                                    <p><strong>USB</strong>: {this.state.car !== null ? (this.state.car.usb ? 'Yes' : 'No') : null}</p>
                                    <p><strong>GPS</strong>: {this.state.car !== null ? (this.state.car.gps ? 'Yes' : 'No') : null}</p>
                                    <div className='label'>
                                        <label>Comments</label>
                                        <hr/>
                                        {this.state.comments.map((comment, i) => {
                                            return(
                                                <div>
                                                    <p className="test">{comment.descriptionComment}</p>
                                                    <hr/>
                                                </div>
                                            );
                                        }
                                        )}
                                    </div>
                                </div>
                                <div className="image-wrapper">
                                    {this.state.car !== null ? <img alt="Car" src={require('../img/' + this.state.car.image)} /> : null}

                                    <p><strong>Description</strong>: {this.state.car !== null ? this.state.car.description : null} </p>
                                    <label>Write comment:</label>
                                    <textarea value={this.state.descriptionComment} onChange={(event) => {this.commentChange(event.target.value)}} className="comment" name="message" rows="10" cols="30"></textarea>
                                    
                                    <button className='btn' onClick={(event) => {this.commentHandler(event)}}>Add comment</button>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </header>
            </div>
        )
    }
}

export default SingleCarPage;