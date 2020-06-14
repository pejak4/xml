import React from 'react';
import './HamburgerMenu.css';

class HamburgerMenu extends React.PureComponent {

    logoutHandler = () => {
        sessionStorage.clear();
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
                                {sessionStorage.getItem('token') !== null ? <li><a href="/login" onClick={() => this.logoutHandler()}>Logout</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/addAdvertisement">Add advertisement</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/codebook">Codebook</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/setStateUser">Active/block users</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/rentalRequest">Rental requests for me</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/rentalRequestsFromMe">Rental requests from me</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/listCars">List your cars</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/ratingRequest">Rating request</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/commentRequest">Comment request</a></li> : null}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default HamburgerMenu;