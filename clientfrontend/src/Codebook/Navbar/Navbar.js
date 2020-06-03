import React from 'react';
import './Navbar.css';

class Navbar extends React.PureComponent {

    render() {
        return (
            <nav className="navbar">
                <h1 className="title">
                    <span className="logo">Code</span>book
                </h1>

                <ul>
                    <li><a onClick={(event) => {this.props.renderHandler('pet')}}>Create new {this.props.type} </a></li>
                    <li><a onClick={(event) => {this.props.renderHandler('jedan')}}>Brand</a></li>
                    <li><a onClick={(event) => {this.props.renderHandler('tri')}}>Fuel type</a></li>
                    <li><a onClick={(event) => {this.props.renderHandler('cetiri')}}>Transmission</a></li>
                </ul>
            </nav>
        );
    }
}

export default Navbar;