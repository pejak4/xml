import React from 'react';
import './HamburgerMenu.css';
// import axios from '../axios-objects';

class HamburgerMenu extends React.PureComponent {

    logoutHandler = async(event) => {
        event.preventDefault();
        //await axios.get('/car-service/aclSecurityLogout');
        //await axios.get('/authentication-service/aclSecurityLogout');
        //await axios.get('/codebook-service/aclSecurityLogout');
        //await axios.get('/image-service/aclSecurityLogout');
        // await axios.get('/message-service/aclSecurityLogout'); //NE znam zasto ne radi
        sessionStorage.clear();
        window.location.href = "/"
    }

    render() {
        return(
            <div className="menu-wrap">
                <input type="checkbox" className="toggler" />
                <div className="hamburger">
                    <div></div>
                </div>
                <div className="menu">
                    <div>
                        <div>
                            <ul>
                                <li><a href="/">Home</a></li>
                                {sessionStorage.getItem('token') === null ? <li><a href="/login">Login/Registration</a></li> : null}
                                {sessionStorage.getItem('token') !== null ? <li><a href="/" onClick={(event) => this.logoutHandler(event)}>Logout</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/addAdvertisement">Add advertisement</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/codebook">Codebook</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/setStateUser">Active/block users</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/rentalRequest">Rental requests for me</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/rentalRequestsFromMe">Rental requests from me</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/listCars">List your cars</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/ratingRequest">Rating request</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/commentRequest">Comment request</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/messages">Message inbox</a></li> : null}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default HamburgerMenu;