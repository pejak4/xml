import React from 'react';
import './Navbar01.css';

class Navbar01 extends React.PureComponent {

    render() {
        return (
            <nav className="navbar">
                <h1 className="title">
                    <span className="logo">Code</span>book
                </h1>

                <ul>
                    <li><button onClick={(event) => {this.props.renderHandler('Jedan')}}>Active users</button></li>
                    <li><button onClick={(event) => {this.props.renderHandler('Dva')}}>Blocked users {this.props.type} </button></li>
                </ul>
            </nav>
        );
    }
}

export default Navbar01;