import React from 'react';
import axios from '../axios-objects';
import './SingleCarpage.css';

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
        console.log(this.props);
        const id = this.props.car.id;
        const response = await axios.get('/getSingleCar', {
            params: {
                id: id
            }
        });
        if (response) {
            this.setState({car: response.data});
        }

        const carId = this.state.car.id;
        const data = {carId};

        const response01 = await axios.post('/getAllCommentByCarId', data);
        if (response01) {
            console.log(response01.data);
            this.setState({comments: response01.data});
        }
    }

    commentChange = (comment) => {
        this.setState({descriptionComment: comment});
    }

    commentHandler = async(event) => {
        let carId = this.state.car.id;

        console.log(carId);

        const token = sessionStorage.getItem('token');
        const response = await axios.get('/getLoggedUser', {
            headers: {
                'Authorization' : 'Bearer ' + token
            }
        });

        if(response) {
            console.log(response.data);
            this.setState({fromUserId: response.data.id});
        }

        const fromUserId = this.state.fromUserId;

        let descriptionComment = this.state.descriptionComment;
        let data02 = {fromUserId, carId, descriptionComment};
        const response02 = await axios.post('/addComment', data02);
        if(response02) {
            console.log(response02);
        }

        window.location.reload();
    }

    render() {
        return (
            <div>
                <header id="showcase">
                    <div className="container showcase-container">
                        <div className="single-car" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            <h2>{this.state.car !== null ? this.state.car.brand : null} {" "}
                            {this.state.car !== null ? this.state.car.model : null} </h2>
                            <div className="car-wrapper">
                                <div className="description">
                                    <p onClick={(event) => {this.props.setNumOfRender('jedan')}}>BACK</p>
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