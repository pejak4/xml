import React from 'react';
import './Registration.css';
import axios from '../axios-objects';
import {updateObject} from '../utility';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';

class Registration extends React.PureComponent {
    constructor(props) {
        super(props);
        
        this.state = {
            registration: {
                firstName: '',
                lastName: '',
                email: '',
                password: ''
            },
            validation: {
                firstName: true,
                lastName: true,
                email: true,
                password: true
            }
        }
    }

    validateEmail = (email) => {
        const expression = /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+[A-Za-z]+$/;
    
        return expression.test(String(email).toLowerCase())
    }

    inputChangeHandler = async (event, type) => {
        let registration = updateObject(this.state.registration, {
            [type]: event.target.value
        });
        this.setState({registration});

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
                    const response = await axios.post('/authentication-service/email-check', data, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });
                    if (response.data) {
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

    registrationHandler = async (event) => {
        event.preventDefault();
        if (this.state.registration.firstName !== '' && this.state.registration.lastName !== ''
        && this.state.registration.email !== '' && this.state.registration.password !== '' &&
        this.state.validation.firstName && this.state.validation.lastName && this.state.validation.email
        && this.state.validation.password) {
            const {firstName, lastName, email, password} = this.state.registration;
            const data = {firstName, lastName, email, password};

            try {
                const response = await axios.post('/authentication-service/registration', data);
                if (response) {
                    this.props.history.push('/login');
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
                        <label>First Name</label>
                        {this.state.validation.firstName ?
                        <input placeholder="First Name" type="text" className="validate"
                        onChange={(event) => this.inputChangeHandler(event, 'firstName')} /> :
                        <input placeholder="First Name" type="text" className="invalidate"
                        onChange={(event) => this.inputChangeHandler(event, 'firstName')} />}

                        <label>Last Name</label>
                        {this.state.validation.lastName ?
                        <input placeholder="Last Name" type="text" className="validate"
                        onChange={(event) => this.inputChangeHandler(event, 'lastName')} /> :
                        <input placeholder="Last Name" type="text" className="invalidate"
                        onChange={(event) => this.inputChangeHandler(event, 'lastName')} />}

                        <label>Email</label>
                        {this.state.validation.email ?
                        <input placeholder="Email" type="email" className="validate"
                        onChange={(event) => this.inputChangeHandler(event, 'email')} /> :
                        <input placeholder="Email" type="email" className="invalidate"
                        onChange={(event) => this.inputChangeHandler(event, 'email')} />}

                        <label>Password</label>
                        {this.state.validation.password ?
                        <input placeholder="Password" type="password" className="validate"
                        onChange={(event) => this.inputChangeHandler(event, 'password')} /> :
                        <input placeholder="Password" type="password" className="invalidate"
                        onChange={(event) => this.inputChangeHandler(event, 'password')} />}

                        <a href="/login" className="btn"
                        onClick={(event) => this.registrationHandler(event)}>Sign up</a>
                    </div>
                </header>
            </div>
        );
    }
}

export default Registration;