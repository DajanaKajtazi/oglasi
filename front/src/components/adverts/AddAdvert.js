import React, {Component} from "react";
import { Form, Button } from "react-bootstrap";
import AdsAxios from "../../apis/AdsAxios";

class AddAdvert extends Component {
  constructor(props) {
    super(props);

    let advert = {
      title: "",
      description: "",
      location: "",
      updated: "",
      adminId: this.props.match.params.id
    };

    this.state = {
      advert: advert
                
    };
    
  }


  valueInputChange(event){
    let control = event.target;
    let name = control.name;
    let value = control.value;

    let advert = this.state.advert;
    advert[name] = value;

    this.setState({advert: advert});
  }

  doAdd(){
    try{
      AdsAxios.post("/advertisements", this.state.advert);
      this.props.history.push("/users/"+this.state.advert.adminId);
    }
    catch(error){
      alert("Couldn't save the advert.")
    }
  }

  render() {
    return (
      <div>
        <h1>Add Advert</h1>

        <Form>
          <Form.Group>
            <Form.Label>Title</Form.Label>
            <Form.Control onChange={(event) => this.valueInputChange(event)} name="title" value={this.state.advert.title} as="input"></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Location</Form.Label>
            <Form.Control onChange={(event) => this.valueInputChange(event)} name="location" value={this.state.advert.location} as="input"></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Date</Form.Label>
            <Form.Control onChange={(event) => this.valueInputChange(event)} name="updated" value={this.state.advert.updated} as="input"></Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Description</Form.Label>
            <Form.Control onChange={(event) => this.valueInputChange(event)} name="description" value={this.state.advert.description} as="input"></Form.Control>
          </Form.Group>
          <Button variant="primary" onClick={()=>this.doAdd()}>Add</Button>
        </Form>

      </div>
    );
  }
}

export default AddAdvert;
