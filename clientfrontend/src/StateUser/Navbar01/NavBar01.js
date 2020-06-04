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
                    <li><a onClick={(event) => {this.props.renderHandler('Jedan')}}>Active users</a></li>
                    <li><a onClick={(event) => {this.props.renderHandler('Dva')}}>Blocked users {this.props.type} </a></li>
                </ul>
            </nav>
        );
    }
}

export default Navbar01;