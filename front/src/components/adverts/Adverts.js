import React, {Component} from 'react';
import AdsAxios from '../../apis/AdsAxios';
import { Table, Button ,ButtonGroup, Form, FormLabel } from 'react-bootstrap';


class Adverts extends Component {
    constructor(props){
        super(props);

        this.state={
          advertisements:[],
          selectedAdvertId: -1,
          search:{title:"",location:"",username:""},
          pageNum: 0,
          totalPages: 1,
          show: false
        }
    }

    componentDidMount(){
      this.getAdvertisements();
    }

    getAdvertisements(page=null){
      let config={params:{}};

      if(this.state.search.title !== ""){
          config.params.title=this.state.search.title;
      }
      if(this.state.search.location !== ""){
          config.params.location=this.state.search.location;
      }
      if(this.state.search.username !== ""){
          config.params.username=this.state.search.username;
      }

      if(page != null){
          config.params.pageNum=page;
      }else{
          config.params.pageNum=this.state.pageNum;
      }
        

      AdsAxios.get("/advertisements", config)
      .then((res)=>{
          this.setState({advertisements: res.data, totalPages: res.headers["total-pages"]});
      })
      .catch((res)=>{
          alert("Error!");
      });
    }

    goToAdvert(advertId){
      this.props.history.push("advertisements/"+advertId);
    }

    valueInputChange(event){
      let control = event.target;
    
      let name = control.name;
      let value = control.value;
    
      let search = this.state.search;
      search[name] = value;
    
      this.setState({search: search});
    }
    
    doSearch(){
      this.setState({totalPages:1, pageNum: 0});
      this.getAdvertisements(0);
    }

    
    changePage(direction){
      let page = this.state.pageNum + direction;  
      this.getAdvertisements(page);
      this.setState({pageNum:page});
    }

    handleChange(event) {
      const name = event.target.name;
      const value = event.target.type === "checkbox" ? event.target.checked : event.target.value;

      this.setState({
        [name]: value
      })
  }

    render() {
        return (
          <div>
            <h1>Adverts</h1>
            <FormLabel>Filter&nbsp;</FormLabel>
            <FormLabel><Form.Check name="show" type="Checkbox" checked={this.state.show} onChange={(e)=>this.handleChange(e)}></Form.Check></FormLabel>
      
           {this.state.show && <Form>
              <Form.Group>
                <FormLabel>Title</FormLabel>
                <Form.Control value={this.state.search.title} name="title" as="input" onChange={(e)=>this.valueInputChange(e)}></Form.Control>
              </Form.Group>
              <Form.Group>
                <FormLabel>Location</FormLabel>
                <Form.Control value={this.state.search.location} name="location" as="input" onChange={(e)=>this.valueInputChange(e)}></Form.Control>
              </Form.Group>
              <Form.Group>
                <FormLabel>Username</FormLabel>
                <Form.Control value={this.state.search.username} name="username" as="input" onChange={(e)=>this.valueInputChange(e)}></Form.Control>
              </Form.Group>
              <Button onClick={()=>this.doSearch()}>Search</Button>
            </Form> }
    
            <Table bordered striped>
              <thead>
                <tr>
                  <th>Title</th>
                  <th>Location</th>
                  <th>Username</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {this.state.advertisements.map((advertisement) => {
                  return (
                    <tr onClick={() => {
                        this.setState({ selectedAdvertId: advertisement.id });
                      }} key={advertisement.id}>
                      <td>{advertisement.title}</td>
                      <td>{advertisement.location}</td>
                      <td>{advertisement.adminUsername}</td>
                      <td>
                          <Button onClick={()=>this.goToAdvert(advertisement.id)}>More</Button>
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

export default Adverts;
