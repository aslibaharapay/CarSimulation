import React, { Component } from "react";
import "./App.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Switch} from 'react-router-dom';
import OAuth2RedirectHandler from "./oauth2/OAuth2RedirectHandler";
import Profile from "./profile/Profile";
import Home from "./home/Home";
class App extends Component {
  state = {
    message: "",
    authenticated: false,
  };

  render() {
    return (
        <React.Fragment>
            <div className="home-container">
                <div className="container">
                    <Switch>
                    <Route exact path="/" component={Home}></Route>
                    <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}/>
                    <Route path="/profile"
                           render={(props) => <Profile authenticated={this.state.authenticated} {...props}/>}/>
                     </Switch>
                </div>
            </div>

        </React.Fragment>

    );
  }
}

export default App;
