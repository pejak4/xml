import React from 'react';
import './Login.css';
import axios from '../axios-objects';
import {updateObject} from '../utility';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import jwt_decode from 'jwt-decode';
import Popup from "reactjs-popup";

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
                password: true,
                blocked: false
            }
        }
    }

    validateEmail = (email) => {
        const expression = /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+[A-Za-z]+$/;
    
        return expression.test(String(email).toLowerCase())
    }

    inputChangeHandler = async (event, type) => {
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
                    const data = {email: event.target.value};
                    const response = await axios.post('/authentication-service/blockedUser', data, {
                        headers: {
                            'Content-Type' : 'application/json'
                        }
                    });
                    let validation = updateObject(this.state.validation, {
                        [type]: true
                    });
                    this.setState({validation});
                    if (response.data) {
                        let validation = updateObject(this.state.validation, {
                            blocked: false
                        });
                        this.setState({validation});
                    } else {
                        let validation = updateObject(this.state.validation, {
                            blocked: true
                        });
                        this.setState({validation});
                    }
                } else {
                    let validation = updateObject(this.state.validation, {
                        [type]: false
                    });
                    this.setState({validation});
                }
            } else if (type === 'password') {
                if (event.target.value.length >= 10) {
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
        
            // let role = sessionStorage.getItem('role');
            // const data001 = {role};
            // await axios.post('/authentication-service/aclSecurity', data001);
            // await axios.post('/car-service/aclSecurity', data001);
            // await axios.post('/codebook-service/aclSecurity', data001);
            // //await axios.post('/image-service/aclSecurity', data001);
            // await axios.post('/message-service/aclSecurity', data001);
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
                        : <p className="text-invalid">Password must be minimum length of 10, with first capital letter, number and special character.</p>}
                        
                        {this.state.validation.blocked ? <Popup trigger={<button className="btn" style={{cursor:'pointer',height:'50px',width:'85px', fontSize:'15px'}}
                        onClick={(event) => this.loginHandler(event)}>
                        Sign in</button>} position="top center">
                            <div style={{background:'red', marginLeft:'0px'}}>Your account has been blocked! Please contact us for more information.</div>
                        </Popup> : <button href="/" className="btn" style={{cursor:'pointer',height:'50px',width:'85px', fontSize:'15px'}}
                        onClick={(event) => this.loginHandler(event)}>Sign in</button>}
                        
                        <p>Don't have an account? Sign up <a href="/registration" className="click-here">here.</a></p>
                    </div>
                </header>
            </div>
        );
    }
}

export default Login;