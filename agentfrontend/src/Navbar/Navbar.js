import React from 'react';
import './Navbar.css';

class Navbar extends React.PureComponent {

    logoutHandler = (event) => {
        sessionStorage.clear();
        this.props.history.push("/");
    }

    render() {
        return (
            <nav className="navbar">
                <h1 className="title">
                    <span className="logo">Agent</span>app
                </h1>

                <ul>
                    {sessionStorage.getItem('token') === null ? <li><a href="/login">Login</a></li> : null}
                    {sessionStorage.getItem('token') === null ? <li><a href="/registration">Registration</a></li> : null}
                    {sessionStorage.getItem('token') !== null ? <li><a href="/login" onClick={(event) => this.logoutHandler(event)}>Logout</a></li> : null}
                    {sessionStorage.getItem('token') !== null ? <li><a href="/advertisement">Add advertisement</a></li> : null}
                    {sessionStorage.getItem('token') !== null ? <li><a href="/occupancy">Occupancy</a></li> : null}
                    {sessionStorage.getItem('token') !== null ? <li><a href="/rentalRequest">Rental request for me</a></li> : null}
                    {sessionStorage.getItem('token') !== null ? <li><a href="/graph">Graph/Statistic</a></li> : null}
                    {sessionStorage.getItem('token') !== null ? <li><a href="/endRental">End rental</a></li> : null}
                </ul>
            </nav>
        );
    }
}

export default Navbar;