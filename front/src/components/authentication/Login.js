import React, {Component} from 'react';
import { Form, Button, Col, FormGroup } from 'react-bootstrap'
import { login } from '../../services/auth'

class Login extends Component {
    constructor() {
        super();

        this.state={username: "", password: ""};
    }

    valueInputChange(event) {
        let control=event.target;

        let name=control.name;
        let value=control.value;

        let change={};
        change[name]=value;
        this.setState(change);
    }

    doLogin(){
        login(this.state);
    }

    render(){
        return(
            <div>
                <Form>
                    <Form.Row>
                      <FormGroup as={Col}>
                        <Form.Label>Username</Form.Label>
                        <Form.Control onChange={(e) => {this.valueInputChange(e)}} name="username" as="input"></Form.Control>
                      </FormGroup>
                      <FormGroup as={Col}>
                        <Form.Label>Password</Form.Label>
                        <Form.Control onChange={(e) => {this.valueInputChange(e)}} name="password" as="input" type="password"></Form.Control>
                      </FormGroup>
                    </Form.Row>
                    <Button onClick={() => {this.doLogin()}}>Log in</Button>
                </Form>
            </div>
        );
    }
}

export default Login;