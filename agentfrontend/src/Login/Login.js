import React from 'react';
import './Login.css';
import {updateObject} from '../utility';
import Navbar from '../Navbar/Navbar';
import axios from '../axios-objects';
import jwt_decode from 'jwt-decode';


class Login extends React.PureComponent {
    state = {
        auth: {
            email: '',
            password: ''
        },
        validation: {
            email: true,
            password: true
        }
    }

    inputChangdeHandler = (event, type) => {
        let auth = updateObject(this.state.auth, {
        [type]: event.target.value
    });
        this.setState({auth});

        if(event.target.value.length === 0) {
            let validation = updateObject(this.state.validation, {
                [type]: false
            });
            this.setState({validation});
        } else {
            let validation = updateObject(this.state.validation, {
                [type]: true
            });
            this.setState({validation});
        }
    }

    loginHandler = async(event) => {
        event.preventDefault();
        if(this.state.validation.email && this.state.validation.password) {
            const email = this.state.auth.email;
            const password = this.state.auth.password;
            const data = {email, password};
            const response = await axios.post('/login', data);
            if(response) {
                const expirationDate = new Date(new Date().getTime() + response.data.expiresIn * 1000);
                sessionStorage.setItem('token', response.data.accessToken);
                const jwtToken = jwt_decode(response.data.accessToken);
                sessionStorage.setItem('role', jwtToken.role);
                sessionStorage.setItem('expirationDate', expirationDate);
                window.location.href = '/';
            }

            const token = sessionStorage.getItem('token');
            await axios.get('/aclSecurity', {
                    headers: {
                        'Authorization' : 'Bearer ' + token
                    }
            });
        }
    }

    render() {
        return (
            <div className="container_1">
            <Navbar />
                <div className="container-content">
                    <h3>Login</h3>
                    <div className="login">
                        <div className="box">
                            <label>Email</label>
                            <input type="email" placeholder="Email"  className="inputRegAg"
                            onChange={(event) => this.inputChangdeHandler(event, 'email')}
                            />
                        </div>
                        <div className="box">
                            <label>Password</label>
                            <input type="password" placeholder="Password" className="inputRegAg"
                            onChange={(event) => this.inputChangdeHandler(event, 'password')}
                            />
                        </div>
                    <a href="/" className="btn_1" onClick={(event) => {this.loginHandler(event); } }>Login</a>
                        <p>
                            Click <a className="a_a" href="/registration" target="_blank" >here</a> to register
                        </p>
                    </div>
                    <div className="invalid_1">
                        {(!this.state.validation.email || !this.state.validation.password) ? 
                            <p>Enter all fields</p> : null}
                    </div>
                </div>
            </div>
        );
    }
};



export default Login;