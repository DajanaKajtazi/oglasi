import React, {Component} from 'react';
import AdsAxios from '../../apis/AdsAxios';
import {Form, Button} from 'react-bootstrap'

class EditAdvert extends Component {
    constructor(props) {
      super(props);
  
      let advert = {
        title: "",
        description: "",
        location: "",
        updated: "",
        adminId: -1,
      };
  
      this.state = {
        advertId: this.props.match.params.id,
        advert: advert
      };
    }
  
    componentDidMount() {
      this.getAdvert();
    }
  
    getAdvert() {
      AdsAxios.get("/advertisements/" + this.state.advertId)
      .then((res) => {
        this.setState({ advert: res.data });
      })
      .catch((res) => {
        alert("Error!");
      });
    }
  
    valueInputChanged(e) {
      let input = e.target;
  
      let name = input.name;
      let value = input.value;
  
      let advert = this.state.advert;
      advert[name] = value;
  
      this.setState({ advert: advert });
    }
  
    doEdit(userId) {
      AdsAxios.put("/advertisements/" + this.state.advertId, this.state.advert)
      .then(() => {
        this.props.history.push("/users/"+userId);
      })
      .catch(() => alert("Something went wrong!"));
    }
  
    render() {
      if (this.state.advertId && this.state.advertId !== -1) {
        return (
          <div>
            <h1>Edit advert</h1>
            <Form>
              <Form.Group>
                <Form.Label>Title</Form.Label>
                <Form.Control name="title" as="input" value={this.state.advert.title} onChange={(e) => {this.valueInputChanged(e);}}/>
              </Form.Group>
              <Form.Group>
                <Form.Label>Location</Form.Label>
                <Form.Control name="location" as="input" value={this.state.advert.location} onChange={(e) => this.valueInputChanged(e)} />
              </Form.Group>
              <Form.Group>
                <Form.Label>Updated</Form.Label>
                <Form.Control name="updated" as="input" value={this.state.advert.updated} onChange={(e) => this.valueInputChanged(e)}/>
              </Form.Group>
              <Form.Group>
                <Form.Label>Description</Form.Label>
                <Form.Control name="description" as="input" value={this.state.advert.description} onChange={(e) => this.valueInputChanged(e)}/>
              </Form.Group>
              <Button onClick={() => this.doEdit(this.state.advert.adminId)}>Edit</Button>
            </Form>
          </div>
        );
      } else {
        return <></>;
      }
    }
  }
  
  export default EditAdvert;
  