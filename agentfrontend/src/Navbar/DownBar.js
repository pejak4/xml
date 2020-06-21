import React from 'react';
import './DownBar.css';

class DownBar extends React.PureComponent {

    render() {
        return (
            <nav className="navbar_1">
                <h1 className="title_1">
                    <span className="logo_1">Info</span>Certificate
                </h1>

                <ul>
                    <li onClick={(e)=>{ this.props.renderHandler("2");}}>Reserved</li>
                    <li onClick={(e)=>{ this.props.renderHandler("1");}}>Pending</li>
                    <li onClick={(e)=>{ this.props.renderHandler("3");}}>Paid</li>
                    <li onClick={(e)=>{ this.props.renderHandler("4");}}>Canceled</li>
                </ul>
            </nav>
        );
    }
}

export default DownBar;