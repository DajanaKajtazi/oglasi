import React, {Component} from 'react'
import AdsAxios from '../../apis/AdsAxios'
import {Table, ButtonGroup, Button, FormLabel, FormGroup, Form} from 'react-bootstrap'

class User extends Component{
    constructor(props){
        super(props);

        let user={
          firstName: "",
          lastName: "",
          email: "",
          dateOfBirth:""
        }

        this.state={
          userId: this.props.match.params.id,
          user: user,
          advertisements: [],
          selectedAdvertId: -1,
          pageNum: 0,
          totalPages: 1
        }
    }

    componentDidMount(){
        if(this.state.userId>0){
          this.getUser();
        }
    }

    getUser(){
      AdsAxios.get("/users/"+this.state.userId)
      .then((res)=>{this.setState({user: res.data});
      this.getUsersAds();
      })
      .catch((res)=>{alert("Error!");
      });
    }

    getUsersAds(page=null){
      let config={params:{}};

      if(page!=null){
        config.params.pageNum=page;
      }else{
        config.params.pageNum=this.state.pageNum;
      }

      AdsAxios.get("/users/"+this.state.userId+"/advertisements", config)
      .then((res)=>{this.setState({advertisements: res.data, totalPages: res.headers["total-pages"]});
        //this.getUser();
      })
      .catch((res)=>{alert("Error!");
      });
    }


    changePage(direction){
      let page = this.state.pageNum + direction;  
      this.getUsersAds(page);
      this.setState({pageNum:page});
    }

    goToAdd(userId) {
      this.props.history.push("/advertisements/add/"+userId);
    }
    
    goToAdvert(adId){
      this.props.history.push("/users/advertisements/"+adId);
    }

    goToEdit(adId) {
      this.props.history.push("/advertisements/edit/"+adId);
    }
    
    async doDelete(adId){
      try{
        await AdsAxios.delete("/advertisements/"+adId);
        this.getUsersAds();
      }
      catch(error){
        alert("Couldn't delete the advert");
      }
    }

    valueInputChanged(e) {
      let input = e.target;
  
      let name = input.name;
      let value = input.value;
  
      let user = this.state.user;
      user[name] = value;
  
      this.setState({ user: user });
    }

    async doEdit(userId){
      await AdsAxios.put("/users/"+userId,this.state.user)
      .then((res)=> {window.location.reload();
      })
      .catch((res)=>{alert("Error!")});
    }
    
    render() {
        return (
          <div>
            <h2>Profile</h2>
            <Form>
              <FormGroup>
                <FormLabel>First name:</FormLabel>
                <Form.Control name="firstName" as="input" value={this.state.user.firstName} onChange={(e) => this.valueInputChanged(e)}></Form.Control>
              </FormGroup>
              <FormGroup>
                <FormLabel>Last name:</FormLabel>
                <Form.Control name="lastName" as="input" value={this.state.user.lastName} onChange={(e) => this.valueInputChanged(e)}></Form.Control>
              </FormGroup>
              <FormGroup>
                <FormLabel>E-mail:</FormLabel>
                <Form.Control name="email" as="input" value={this.state.user.email} onChange={(e) => this.valueInputChanged(e)}></Form.Control>
              </FormGroup>
              <FormGroup>
                <FormLabel>Date of birth:</FormLabel>
                <Form.Control name="dateOfBirth" as="input" value={this.state.user.dateOfBirth} onChange={(e) => this.valueInputChanged(e)}></Form.Control>
              </FormGroup>
              <Button variant="warning" onClick={() => {this.doEdit(this.state.userId)}}>Edit</Button>
            </Form>
            <h3>Adverts</h3>
            <Button onClick={() => {this.goToAdd(this.state.userId);}}>Add New Advert</Button>
    
            <Table bordered striped>
              <thead>
                <tr>
                  <th>Title</th>
                  <th colSpan={3}></th>
                </tr>
              </thead>
              <tbody>
                {this.state.advertisements.map((advertisement) => {
                  return (
                    <tr onClick={() => { this.setState({ selectedAdvertId: advertisement.id });}} key={advertisement.id} >
                      <td>{advertisement.title}</td>
                      <td>
                        <Button variant="info" onClick={() => this.goToAdvert(advertisement.id)}>More</Button>
                        <Button variant="warning" onClick={() => this.goToEdit(advertisement.id)}>Edit</Button>
                        <Button variant="danger" onClick={() => this.doDelete(advertisement.id)}>Delete</Button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </Table>

            <ButtonGroup className="float-right">
              <Button disabled={this.state.pageNum == 0} onClick={()=>this.changePage(-1)}>Previous</Button>
              <Button disabled={this.state.pageNum + 1 == this.state.totalPages} onClick={()=>this.changePage(1)}>Next</Button>
            </ButtonGroup>
          </div>
        );
    }
}

export default User;