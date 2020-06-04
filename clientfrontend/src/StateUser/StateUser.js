import React from 'react';
import Navbar01 from './Navbar01/NavBar01';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import './StateUser.css';
import img from '../img/user.jpg';
import blockImg from '../img/blockUser.png';
import axios from '../axios-objects';

class StateUser extends React.Component {

    state = {
        renderNumber: '',

        activeUsers: [],
        blockUsers: [],
    }

    componentDidMount = async() => {
        let enabled = true;
        const data1 = {enabled};
        const response1= await axios.post('/authentication-service/getAllUsersByEnabled', data1);
        if(response1) {
            this.setState({activeUsers: response1.data});
        }

        enabled = false;
        const data2 = {enabled};
        const response2 = await axios.post('/authentication-service/getAllUsersByEnabled', data2)
        if(response2) {
            this.setState({blockUsers: response2.data});
        }
    }

    clickSetStateUser = async (userId) => {
        var id = userId;
        const data = {id};
        
        const response = await axios.post('/authentication-service/setStateUser', data);
        if(response) {
            console.log(response.data);
        }

        window.location.reload();
    }

    clickDelete = async (userId) => {
        var id = userId;
        const data = {id};
        
        const response = await axios.post('/authentication-service/deleteUser', data);
        if(response) {
            console.log(response.data);
        }
        window.location.reload();
    }

    renderActiveUsers() {
        if(this.state.renderNumber === 1) {
            return this.state.activeUsers.map((user, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={img} alt='Car' style={{width:"100%"}}/>
                        <div className="containerr">
                        <h4><b>Active user: ID {user.id}</b></h4>
                            <p>{user.firstName} {user.lastName}</p> 
                        </div>
                        <button className="but" onClick={(event) => {this.clickSetStateUser(user.id)}}>Block this user</button>
                        <button className="but" onClick={(event) => {this.clickDelete(user.id)}}>Delete this user</button>
                    </div> 
                );
            });
        }
    }

    renderBlockedUsers() {
        if(this.state.renderNumber === 2) {
            return this.state.blockUsers.map((user, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={blockImg} alt='User' style={{width:"100%"}} />
                        <div className='containerr'>
                            <h4><b>Blocked user: ID {user.id}</b></h4>
                            <p>{user.firstName} {user.lastName}</p>
                        </div>
                        <button className="but" onClick={(event) => {this.clickSetStateUser(user.id)}}>Activate this user</button>
                        <button className="but" onClick={(event) => {this.clickDelete(user.id)}}>Delete this user</button>
                    </div>
                );
            });
        }
    }

    renderHandler = (type) => {
        if(type === 'Jedan') {
            this.setState({renderNumber: 1});
        } else if(type === "Dva") {
            this.setState({renderNumber: 2});
        }
    }


    render() {
        return(
            <div>
                <Navbar01 renderHandler={this.renderHandler} />
                <HamburgerMenu />
                <header id="showcase">
                    <div className="containerSearch showcase-containerSearch">
                        <div className="a" style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderActiveUsers()}
                            {this.renderBlockedUsers()}
                        </div>
                    </div>
            </header>

            </div>
        );
    }
}

export default StateUser;