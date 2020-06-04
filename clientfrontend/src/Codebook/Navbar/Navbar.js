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
                    <li><button onClick={(event) => {this.props.renderHandler('pet')}}>Create new {this.props.type} </button></li>
                    <li><button onClick={(event) => {this.props.renderHandler('jedan')}}>Brand</button></li>
                    <li><button onClick={(event) => {this.props.renderHandler('tri')}}>Fuel type</button></li>
                    <li><button onClick={(event) => {this.props.renderHandler('cetiri')}}>Transmission</button></li>
                </ul>
            </nav>
        );
    }
}

export default Navbar;