import React from 'react';
import './Cart.css';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import axios from '../axios-objects';

class Cart extends React.PureComponent{

    test = async(event) => {
        event.preventDefault();
        const data = {pom: 'velkom'};
        console.log('u fjiji!!');
        const response = await axios.post('/cart-service/put', data);
        if(response) console.log('MORE');
        
    }


    render(){
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="container showcase-container">
                        <h1>Cart</h1>
                        <p>Your orders are all here</p>
                        <label className="point">Start point</label>
                        <div>
                            <select onChange={(event) => this.selectHandlerStart(event)}>
                                <option>Kula</option>
                                <option>Novi Sad</option>
                                <option>Beograd</option>
                                <option>Negotin</option>
                                <option>Nis</option>
                                <option>Zajecar</option>
                                <option>Prizren</option>
                            </select>
                            
                        </div>

                        <label className="point">End point</label>
                        <div>
                            <select onChange={(event) => this.selectHandlerEnd(event)}>
                                <option>Kula</option>
                                <option>Novi Sad</option>
                                <option>Beograd</option>
                                <option>Negotin</option>
                                <option>Nis</option>
                                <option>Zajecar</option>
                                <option>Prizren</option>
                            </select>
                            
                        </div>
                        
                        <a href="/" className="btn" onClick={(e) => this.test(e)}>Search for available cars</a>
                      
                    </div>
                </header>
            </div>
        );
    }
}

export default Cart;