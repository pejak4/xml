import React from 'react';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import axios from '../axios-objects';

class Overdraft extends React.PureComponent {

    state = {
        userId: '',
        overdrafts: [],
    }

    componentDidMount = async() => {
        let userEmail = sessionStorage.getItem('userEmail'); 
        const data = {userEmail};
        const response = await axios.post('/authentication-service/getLoggedUser', data);
        if(response){
            this.setState({userId: response.data.id});
        }
        let userId = response.data.id;
        const data01 = {userId};
        const response01 = await axios.post('/car-service/getAllOverdraft', data01);
        if(response01) {
            this.setState({overdrafts: response01.data})
        }

    }

    acceptHandler = async(event, overdraft) => {
        let overdraftId = overdraft.id;
        const data = {overdraftId};
        await axios.post('/car-service/acceptOverdraft', data)
        window.location.reload();
    }

    renderRatingRequest() {
        return(
            this.state.overdrafts.map((overdraft, i) => {
                return (
                    <div className="card" key={i}>
                        <div className='containerr'>
                            <h4><b>Overdraft kilometer(ID): {overdraft.id}</b></h4>
                            <br/><hr/>
                            <br/>
                            <h5><b>Price: {overdraft.price}</b></h5>
                            <br/><hr/>
                        </div>
                        <button className="acceptButton" onClick={(event) => {this.acceptHandler(event, overdraft)}}>Pay</button>
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

export default Overdraft;