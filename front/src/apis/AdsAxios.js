import axios from 'axios'

var AdsAxios=axios.create({
    baseURL: 'http://localhost:8080/api'
});

export default AdsAxios;