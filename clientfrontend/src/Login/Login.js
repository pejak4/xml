import React from 'react';
import './Login.css';
import axios from '../axios-objects';
import {updateObject} from '../utility';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import jwt_decode from 'jwt-decode';

class Login extends React.PureComponent {
    constructor(props) {
        super(props);

        this.state = {
            login: {
                email: '',
                password: ''
            },
            validation: {
                email: true,
                password: true
            }
        }
    }

    validateEmail = (email) => {
        const expression = /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+[A-Za-z]+$/;
    
        return expression.test(String(email).toLowerCase())
    }

    inputChangeHandler = (event, type) => {
        let login = updateObject(this.state.login, {
            [type]: event.target.value
        });
        this.setState({login});

        //validacija
        if(event.target.value.length === 0) {
            let validation = updateObject(this.state.validation, {
                [type]: false
            });
            this.setState({validation});
        } else {
            if (type === 'email') {
                let email = this.validateEmail(event.target.value);
                if (email) {
                    let validation = updateObject(this.state.validation, {
                        [type]: true
                    });
                    this.setState({validation});
                } else {
                    let validation = updateObject(this.state.validation, {
                        [type]: false
                    });
                    this.setState({validation});
                }
            } else if (type === 'password') {
                if (event.target.value.length >= 6) {
                    let validation = updateObject(this.state.validation, {
                        [type]: true
                    });
                    this.setState({validation});
                } else {
                    let validation = updateObject(this.state.validation, {
                        [type]: false
                    });
                    this.setState({validation});
                }
            }
        }
    }

    loginHandler = async (event) => {
        event.preventDefault();
        if (this.state.login.email !== '' && this.state.login.password !== '' && this.state.validation.email
        && this.state.validation.password) {
            const {email, password} = this.state.login;
            const data = {email, password};

            try {
                const response = await axios.post('/authentication-service/login', data);
                if (response) {
                    const expirationDate = new Date(new Date().getTime() + response.data.expiresIn * 1000);
                    sessionStorage.setItem('token', response.data.accessToken);
                    const jwtToken = jwt_decode(response.data.accessToken);
                    sessionStorage.setItem('role', jwtToken.role);
                    sessionStorage.setItem('expirationDate', expirationDate);
                    sessionStorage.setItem('userEmail', jwtToken.sub);
                    this.props.history.push('/');
                }
            } catch(err) {
                console.log(err);
            }
        }
    }

    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="container showcase-container">
                        <label>Email</label>
                        {this.state.validation.email ? 
                        <input placeholder="Email" type="email" className="validate"
                        onChange={(event) => this.inputChangeHandler(event, 'email')}/> :
                        <input placeholder="Email" type="email" className="invalidate"
                        onChange={(event) => this.inputChangeHandler(event, 'email')}/>}
                        {this.state.validation.email ? <p className="text-valid">Valid email address.</p>
                        : <p className="text-invalid">Please enter a valid email address.</p>}

                        <label>Password</label>
                        {this.state.validation.password ? 
                        <input placeholder="Password" type="password" className="validate"
                        onChange={(event) => this.inputChangeHandler(event, 'password')}/> :
                        <input placeholder="Password" type="password" className="invalidate"
                        onChange={(event) => this.inputChangeHandler(event, 'password')}/>}
                        {this.state.validation.password ? <p className="text-valid">Valid password.</p>
                        : <p className="text-invalid">Password must be minimum length of 6.</p>}
                        <a href="/" className="btn"
                        onClick={(event) => this.loginHandler(event)}>Sign in</a>
                        <p>Don't have an account? Sign up <a href="/registration" className="click-here">here.</a></p>
                    </div>
                </header>
            </div>
        );
    }
}

export default Login;