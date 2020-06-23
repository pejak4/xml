import React from 'react';
import Navbar from '../Navbar/Navbar';
import './HomePage.css';

class HomePage extends React.PureComponent {

    render() {
        return (
            <div>
                <Navbar />
                <div className="main-container">
                    <div className="container-content">
                        <h1>Dobrodosli</h1>
                        <p>Ovaj deo je namenjen iskljucivo za agentski deo korisnika</p>
                    </div>
                </div>
            </div>
        )
    }
}

export default HomePage;