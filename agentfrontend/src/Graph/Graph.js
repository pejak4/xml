import React from 'react';
import Navbar from '../Navbar/Navbar';
import { BarChart } from "reaviz";
import axios from '../axios-objects';

class Graph extends React.PureComponent {

    state = {
        allCarsLoggedUser: [],

        countComments: [],
        keys: [],
        values: [],
    }

    componentDidMount = async() => {
        const token = sessionStorage.getItem('token');
        const response = await axios.get('/getLoggedUser', {
            headers: {
                'Authorization' : 'Bearer ' + token
            }
        });

        const userId = response.data.id;
        const data1 = {userId};
        const response1 = await axios.post('/getCarsLogedUser', data1);
        if(response1) {
            this.setState({allCarsLoggedUser: response1.data});
        }

        const response2 = await axios.post('/getCountComments', data1);
        if(response2) {
            this.setState({countComments: response2.data, keys: Object.keys(response2.data), values: Object.values(response2.data)});
        }
    }

    renderGraphComment() {
        let preparedData = [];
        for(let i=0; i<this.state.keys.length; i++) {
            let novi = {key: this.state.keys[i], data: this.state.values[i]}
            preparedData = [...preparedData, novi];
        }
        return <BarChart width={550} height={450} data={preparedData} />; 
    }

    renderGraphRating() {
        const {allCarsLoggedUser = []} = this.state;
        const preparedData = allCarsLoggedUser.map(({id, rating}) => ({key: id, data: rating}));
          return <BarChart width={550} height={450} data={preparedData} />; 
    }

    renderGraphMileage() {
        const { allCarsLoggedUser = []} = this.state;
        const preparedData = allCarsLoggedUser.map(({id, mileage}) => ({ key: id, data: mileage})); 
          return <BarChart width={550} height={450} data={preparedData} />; 
    }

    renderCars = () => {
        const { allCarsLoggedUser = [] }  = this.state;
        const preparedData = allCarsLoggedUser.map(({ id, capacitySeats }) => ({ key: id, data: capacitySeats }));
        return <BarChart width={550} height={450} data={preparedData} />; 
    };

    render() {
        return(
            <div>
                <Navbar />
                <header id="showcase">
                    <br/><br/>
                    <div className="containerSearch showcase-containerSearch" >
                        <div style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderGraphMileage()}
                            {this.renderGraphRating()}
                            {this.renderGraphComment()}
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}

export default Graph;