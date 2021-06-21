import React, { Component } from 'react';
import './Home.css';

export const MERCEDES_AUTH_URL = 'https://id.mercedes-benz.com/as/authorization.oauth2?response_type=code&client_id=05644454-d465-47fb-ad25-140cd45c0792&redirect_uri=http://localhost:3000/oauth2/redirect&scope=mb:vehicle:status:general mb:user:pool:reader offline_access&state=test';

class Home extends Component {


    render() {
        return (
            <div className="home-container">
                <div className="container">
                    <div className="graf-bg-container">
                        <div className="graf-layout">
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                            <div className="graf-circle"></div>
                        </div>
                    </div>
                    <h1 className="home-title">You Need to Sign in Mercedes-Benz ID</h1>
                    <br></br>
                    <a className="btn btn-secondary btn-lg"
                       href={MERCEDES_AUTH_URL}
                       role="button">CONNECT</a>
                </div>
            </div>
        )
    }
}

export default Home;
