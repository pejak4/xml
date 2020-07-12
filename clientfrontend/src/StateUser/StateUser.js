import React from 'react';
import Navbar01 from './Navbar01/NavBar01';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import './StateUser.css';
import axios from '../axios-objects';

class StateUser extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            renderNumber: '',
            activeUsers: [],
            blockUsers: []
        }
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
        
        await axios.post('/authentication-service/setStateUser', data);

        window.location.reload();
    }

    clickDelete = async (userId) => {
        var id = userId;
        const data = {id};
        
        await axios.post('/authentication-service/deleteUser', data);
        window.location.reload();
    }

    clickSetRole = async (userId) => {
        var id = userId;
        const data = {id};

        console.log(userId);
        
        await axios.post('/authentication-service/setRole', data);
        window.location.reload();
    }

    renderActiveUsers() {
        if(this.state.renderNumber === 1) {
            return this.state.activeUsers.map((user, i) => {
                return(
                    <div className="card" key={i}>
                        <img src={'http://res.cloudinary.com/pejak/image/upload/v1594556662/file_qrqiiv.jpg'} alt='Car' style={{width:"100%"}}/>
                        <div className="containerr">
                        <h4><b>Active user: ID {user.id}</b></h4>
                            <p>{user.firstName} {user.lastName}</p> 
                        </div>
                        {user.email === sessionStorage.getItem('userEmail') ? <button className="but" disabled={true} style={{cursor:'not-allowed'}}
                            onClick={(event) => {this.clickSetStateUser(user.id)}}>Block this user</button>
                        : <button className="but" onClick={(event) => {this.clickSetStateUser(user.id)}}>Block this user</button> } 
                        {user.email === sessionStorage.getItem('userEmail') ? <button className="but" disabled={true} style={{cursor:'not-allowed'}}
                            onClick={(event) => {this.clickDelete(user.id)}}>Delete this user</button>
                        : <button className="but" onClick={(event) => {this.clickDelete(user.id)}}>Delete this user</button> }
                        <button className="but" onClick={(event) => {this.clickSetRole(user.id)}}>Set role</button>
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
                        <img src={'http://res.cloudinary.com/pejak/image/upload/v1594556701/file_qvyrtx.png'} alt='User' style={{width:"100%"}} />
                        <div className='containerr'>
                            <h4><b>Blocked user: ID {user.id}</b></h4>
                            <p>{user.firstName} {user.lastName}</p>
                        </div>
                        {user.email === sessionStorage.getItem('userEmail') ? <button className="but" disabled={true} style={{cursor:'not-allowed'}}
                            onClick={(event) => {this.clickSetStateUser(user.id)}}>Unblock this user</button>
                        : <button className="but" onClick={(event) => {this.clickSetStateUser(user.id)}}>Unblock this user</button> } 
                        {user.email === sessionStorage.getItem('userEmail') ? <button className="but" disabled={true} style={{cursor:'not-allowed'}}
                            onClick={(event) => {this.clickDelete(user.id)}}>Delete this user</button>
                        : <button className="but" onClick={(event) => {this.clickDelete(user.id)}}>Delete this user</button> }
                        <button className="but" onClick={(event) => {this.clickSetRole(user.id)}}>Set role</button>
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