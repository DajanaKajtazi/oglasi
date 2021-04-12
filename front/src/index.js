import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import {Route, Switch, HashRouter as Router, Link, Redirect} from 'react-router-dom'
import {Navbar, Nav, Button, Container} from 'react-bootstrap'
import Adverts from './components/adverts/Adverts'
import Login from './components/authentication/Login'
import Advert from './components/adverts/Advert'
import Users from './components/users/Users'
import User from './components/users/User'
import EditAdvert from './components/adverts/EditAdvert';
import AddAdvert from './components/adverts/AddAdvert';
import {logout} from './services/auth'

class App extends Component {

    render() {
      let token=window.localStorage.getItem("token");
      if(token){
        return (
        <div>
          <Router>
          <Navbar bg="dark" variant="dark" expand>
              <Nav>
                <Nav.Link as={Link} to="/advertisements">
                  Adverts
                </Nav.Link>
                <Nav.Link as={Link} to="/users">
                  Users
                </Nav.Link>
                <Button onClick={() => {logout();}} variant="outline-info">Log Out</Button>
              </Nav>
            </Navbar>
              <Switch>
                <Route exact path="/" component={Adverts} />
                <Route exact path="/advertisements" component={Adverts} />
                <Route exact path="/advertisements/:id" component={Advert} />
                <Route exact path="/users/advertisements/:id" component={Advert} />
                <Route exact path="/users" component={Users} />
                <Route exact path="/users/:id" component={User} />
                <Route exact path="/advertisements/edit/:id" component={EditAdvert} />
                <Route exact path="/advertisements/add/:id" component={AddAdvert} />
                <Route exact path="/login"><Redirect to="/"></Redirect></Route>
              </Switch>
          </Router>
        </div>
        );
      }else{
        return(
          <Router>
            <Navbar bg="dark" variant="dark" expand>
              <Nav>
                <Nav.Link as={Link} to="/advertisements">
                  Adverts
                </Nav.Link>
                <Nav.Link as={Link} to="/login">
                  Log In
                </Nav.Link>
              </Nav>
            </Navbar>
            <Container>
              <Switch>
              <Route exact path="/advertisements" component={Adverts} />
                <Route exact path="/login" component={Login}></Route>
                <Route path="/"><Redirect to="/advertisements"></Redirect></Route>
              </Switch>
            </Container>
          </Router>
        );
      }
    }
}

ReactDOM.render(
    <App/>,
    document.querySelector('#root')
);