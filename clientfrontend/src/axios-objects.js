import axios from 'axios';

const instance = axios.create({
    baseURL: 'https://localhost:8762'
});

export default instance;