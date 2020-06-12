import React from 'react';
import './Navbar.css';

class Navbar extends React.PureComponent {

    render() {
        return (
            <nav className="navbar">
                <h1 className="title">
                    <span className="logo">Rental</span>request for me
                </h1>
                <ul>
                    <li><button disabled={this.props.blockButton} onClick={(event) => {this.props.renderHandler('1')}}>Pending{this.props.type} </button></li>
                    <li><button disabled={this.props.blockButton} onClick={(event) => {this.props.renderHandler('2')}}>Reserved</button></li>
                    <li><button disabled={this.props.blockButton} onClick={(event) => {this.props.renderHandler('3')}}>Paid</button></li>
                    <li><button disabled={this.props.blockButton} onClick={(event) => {this.props.renderHandler('4')}}>Canceled</button></li>
                </ul>
            </nav>
        );
    }
}

export default Navbar;