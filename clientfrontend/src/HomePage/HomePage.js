import React from 'react';
import './HomePage.css';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import DatePicker from 'react-datepicker';
import {updateObject} from '../utility';
import axios from '../axios-objects';
import {Redirect} from 'react-router-dom';

class HomePage extends React.PureComponent {

    constructor(props) {
        super(props);

        this.state = {
            search: {
                startCity: 'Kula',
                endCity: 'Kula',
                startDate: new Date(new Date().setTime(new Date().getTime() + 2 * 86400000)),
                endDate: new Date(new Date().setTime(new Date().getTime() + 3 * 86400000))
            },
            redirect: false,
            cars: []
        }
    }

    startDateHandler = (date) => {
        let search = updateObject(this.state.search, {
            startDate: date
        });
        this.setState({search});
    }

    endDateHandler = (date) => {
        let search = updateObject(this.state.search, {
            endDate: date
        });
        this.setState({search});
    }

    selectHandlerStart = (event) => {
        let search = updateObject(this.state.search, {
            startCity: event.target.value
        });
        this.setState({search});
    }

    selectHandlerEnd = (event) => {
        let search = updateObject(this.state.search, {
            endCity: event.target.value
        });
        this.setState({search});
    }

    searchHandler = async (event) => {
        event.preventDefault();

        const {startCity, endCity, startDate, endDate} = this.state.search;
        const data = {startCity, endCity, startDate, endDate};
        
        const response = await axios.post('/searchCars', data);
        if (response)
            this.setState({cars: response.data, redirect: true});
    }

    renderRedirect = () => {
        if (this.state.redirect)
            return <Redirect to={{pathname: "/search", state: {cars: this.state.cars}}}/>
    }

    render() {
        return (
            <div>
                <HamburgerMenu />
                <header id="showcase">
                    <div className="container showcase-container">
                        <h1>Welcome</h1>
                        <p>Be free to choose, we are here for you!</p>
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
                            <DatePicker className="date"
                                minDate={new Date(new Date().setTime(new Date().getTime() + 2 * 86400000))}
                                selected={this.state.search.startDate}
                                onChange={date => this.startDateHandler(date)}
                                showTimeSelect
                                timeFormat="HH:mm"
                                timeIntervals={15}
                                timeCaption="time"
                                dateFormat="MMMM d, yyyy h:mm aa"
                            />
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
                            <DatePicker className="date"
                                minDate={new Date(new Date().setTime(new Date().getTime() + 2 * 86400000))}
                                selected={this.state.search.endDate}
                                onChange={date => this.endDateHandler(date)}
                                showTimeSelect
                                timeFormat="HH:mm"
                                timeIntervals={15}
                                timeCaption="time"
                                dateFormat="MMMM d, yyyy h:mm aa"
                            />
                        </div>
                        
                        <a href="/search" className="btn" onClick={(event) => this.searchHandler(event)}>Search for available cars</a>
                        {this.renderRedirect()}
                    </div>
                </header>
            </div>
        );
    }
}

export default HomePage;