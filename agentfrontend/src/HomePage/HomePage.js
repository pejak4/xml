import React from 'react';
import Navbar from '../Navbar/Navbar';
import './HomePage.css';
import axios from '../axios-objects';


class HomePage extends React.PureComponent {

    // appHandler = async(event) => {
    //     const model = "BMW";
    //     const data = {model};
    //     const response = await axios.post('/api', data);
    //     if(response){
    //         console.log(response);
    //     }
    // }

    render() {
        return (
            <div>
                <Navbar />
                <div className="main-container">
                    <div className="container-content">
                        <h1>Dobrodosli</h1>
                        <p>Ovaj deo je namenjen iskljucivo za agentski deo korisnika</p>
                        {/* <button onClick={(event) => {this.appHandler(event)}}>Ajde</button> */}
                    </div>
                </div>
            </div>
        )
    }

}

export default HomePage;