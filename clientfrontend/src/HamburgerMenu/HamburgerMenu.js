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
                                {sessionStorage.getItem('token') !== null ? <li><a href="/" onClick={() => this.logoutHandler()}>Logout</a></li> : null}
                                {sessionStorage.getItem('role') === 'USER' ? <li><a href="/addAdvertisement">Add advertisement</a></li> : null}
                                {sessionStorage.getItem('role') === 'ADMIN' ? <li><a href="/codebook">Codebook</a></li> : null}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default HamburgerMenu;