import React, {Component} from 'react';
import AdsAxios from '../../apis/AdsAxios';
import {Table, Button, ButtonGroup} from 'react-bootstrap';

class Users extends Component{
    constructor(props){
        super(props);

        this.state={
            users: [],
            pageNum: 0,
            totalPages: 1
        }
    }

    componentDidMount(){
        this.getUsers();
    }

    getUsers(page=null){
        let config={params:{}}

        if(page!=null){
            config.params.pageNum=page;
        }else{
            config.params.pageNum=this.state.pageNum;
        }

        AdsAxios.get("/users",config)
        .then((res)=>{
            this.setState({users: res.data, totalPages: res.headers["total-pages"]});
        })
        .catch((res)=>{alert("Error!")});
    }

    changePage(direction){
        let page = this.state.pageNum + direction;  
        this.getUsers(page);
        this.setState({pageNum:page});
    }

    goToUser(userId){
      this.props.history.push("users/"+userId);
    }

    render() {
        return (
          <div>
            <h1>Users</h1>
    
            <ButtonGroup className="float-right">
              <Button disabled={this.state.pageNum == 0} onClick={()=>this.changePage(-1)}>Previous</Button>
              <Button disabled={this.state.pageNum + 1 == this.state.totalPages} onClick={()=>this.changePage(1)}>Next</Button>
            </ButtonGroup>
    
            <Table bordered striped>
              <thead>
                <tr>
                  <th>First name</th>
                  <th>Last name</th>
                  <th>E-mail</th>
                  <th>Date of birth</th>
                  <th>Username</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {this.state.users.map((user) => {
                  return (
                    <tr key={user.id}>
                      <td>{user.firstName}</td>
                      <td>{user.lastName}</td>
                      <td>{user.email}</td>
                      <td>{user.dateOfBirth}</td>
                      <td>{user.username}</td>
                      <td><Button onClick={()=>this.goToUser(user.id)}>User profile</Button></td>
                    </tr>
                  );
                })}
              </tbody>
            </Table>
          </div>
        );
    }
}

export default Users;