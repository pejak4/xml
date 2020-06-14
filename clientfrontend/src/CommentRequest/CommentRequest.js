import React from 'react';
import axios from '../axios-objects';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';

class CommentRequest extends React.PureComponent {

    state = {
        commentRequests: [],
    }

    componentDidMount = async() => {
        const response = await axios.get('/car-service/getAllCommentRequest');
        if(response) {
            console.log(response.data);
            this.setState({commentRequests: response.data})
        }
    }

    acceptHandler = async(event, commentRequest) => {
        let commentRequestId = commentRequest.id;
        const data = {commentRequestId};
        await axios.post('/car-service/acceptCommentRequest', data)
        window.location.reload();
    }

    declineHandler = async(event, commentRequest) => {
        let commentRequestId = commentRequest.id;
        const data = {commentRequestId};
        await axios.post('/car-service/declineCommentRequest', data)
        window.location.reload();
    }

    renderCommentRequest() {
        return(
            this.state.commentRequests.map((commentRequest, i) => {
                return (
                    <div className="card" key={i}>
                        <div className='containerr'>
                            <h4><b>Rating request: {commentRequest.id}</b></h4>
                            Car id: {commentRequest.carId    }
                            <br/><hr/>
                            <br/>
                            <h5><b>Comment:</b> {commentRequest.descriptionComment}</h5>
                            <br/><hr/>
                        </div>
                        <button onClick={(event) => {this.acceptHandler(event, commentRequest)}}>Accept</button>
                        <button onClick={(event) => {this.declineHandler(event, commentRequest)}}>Decline</button>
                    </div>
                );
            })
        );
    }

    render() {
        return(
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderCommentRequest()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default CommentRequest;