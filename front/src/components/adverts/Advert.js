import React, {Component} from 'react';
import AdsAxios from '../../apis/AdsAxios'
import {Table} from 'react-bootstrap';

class Advert extends Component {

    constructor(props) {
        super(props);

        let advert={
            title: "",
            description: "",
            updated: "",
            location: "",
            adminUsername: ""
        };

        this.state={
            advertId: this.props.match.params.id,
            advert: advert
        }
    }

    componentDidMount(){
        this.getAdvert();
    }

    getAdvert(){
        AdsAxios.get("/advertisements/"+this.state.advertId)
        .then((res)=>{
            this.setState({advert: res.data});
        })
        .catch((res)=>{alert("Error");
        });
    }

    render(){
        return(
            <div>
                <h3>{this.state.advert.title}</h3>
                <Table bordered striped>
                    <thead>
                        <tr>
                            <td>Posted: {this.state.advert.updated}</td>
                            <td>Location: {this.state.advert.location}</td>
                            <td>Username: {this.state.advert.adminUsername}</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colSpan="3">{this.state.advert.description}</td>
                        </tr>
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default Advert;