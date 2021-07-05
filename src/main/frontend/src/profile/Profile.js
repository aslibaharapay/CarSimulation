import React, { Component } from 'react';
import './Profile.css';
import getWsConnection from "../wsConnection";
import {Redirect} from "react-router-dom";
import getEvent from "../event"
class Profile extends Component {

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            doorsStatusInfo: undefined
        }
    }

    async componentDidMount() {
        getWsConnection().connect().then(r =>{
            console.log("ws connected!")
            getWsConnection().sendMessage("/app/login.mercedes.me",
                {code : localStorage.getItem('code')} ).then(res => {
                this.processMessage(res);
            });
        });
        getEvent().on("broadcast-message", (message) => {
            console.log("message " + message)
            this.processMessage(message);
        });

    }

    processMessage(res){
        console.log("Resolved Message is ---> " +res.content);

        if(res.content==='ERROR'){
            localStorage.clear();
            return <Redirect to={{
                pathname: "/",
                state: {
                    from: this.props.location,
                    error: ''
                }
            }}/>
        }
        this.setState({
            doorsStatusInfo: res.content
        })
    }
    fetchDoorLockStatus(lockStatus){
        getWsConnection().sendMessage("/app/lock.vehicle",
            {content :lockStatus}).then(res => {
            console.log("Post message send")
        });
    }

    getValue(s){
        return s? s.toLowerCase().charAt(0).toUpperCase() +s.toLowerCase().slice(1):'';
    }

    render() {
        let doorsStatusInfo = this.state.doorsStatusInfo;
        return (
            <div className="profile-container">
            <form className="form-container" >
                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="staticEmail" className="col-sm-2 col-form-label">Mercedes Car ID</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="staticEmail"
                               value={ doorsStatusInfo && doorsStatusInfo.carID } />
                    </div>
                </div>


                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorStatus Front-Right</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorstatusfrontright ?
                                   this.getValue(doorsStatusInfo.doorstatusfrontright.value):''}/>
                    </div>
                </div>

                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorStatus Front-Left</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                        value={doorsStatusInfo && doorsStatusInfo.doorstatusfrontleft ?
                            this.getValue(doorsStatusInfo.doorstatusfrontleft.value):''}/>
                    </div>
                </div>


                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorLock Front-Left</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorlockstatusfrontleft ?
                                   this.getValue(doorsStatusInfo.doorlockstatusfrontleft.value):''}/>
                    </div>
                </div>


                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorLock Front-Right</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorlockstatusfrontright ?
                                   this.getValue(doorsStatusInfo.doorlockstatusfrontright.value):''}/>
                    </div>
                </div>

                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorStatus Rear-Right</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorstatusrearright ?
                                   this.getValue(doorsStatusInfo.doorstatusrearright.value):''}/>
                    </div>
                </div>

                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorStatus Rear-Left</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorstatusrearleft ?
                                   this.getValue(doorsStatusInfo.doorstatusrearleft.value):''}/>
                    </div>
                </div>

                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">DoorLock Rear-Left</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorlockstatusrearleft ?
                                   this.getValue(doorsStatusInfo.doorlockstatusrearleft.value):''}/>
                    </div>
                </div>

                <div className="form-group row" style={{background:"white"}}>
                    <label htmlFor="a" className="col-sm-2 col-form-label">Door Status Rear-Right</label>
                    <div className="col-sm-10">
                        <input type="text" readOnly className="form-control-plaintext" id="a"
                               value={doorsStatusInfo && doorsStatusInfo.doorlockstatusrearright ?
                                   this.getValue(doorsStatusInfo.doorlockstatusrearright.value):''}/>
                    </div>
                </div>

                <div className="form-group row" style={{background:"white"}}>
                  <button type="button" style={{width: "40%" , margin: "1%" }}
                          className="btn btn-secondary"
                          onClick={()=>this.fetchDoorLockStatus("LOCK")}
                  >Locks All Doors</button>

                    <button type="button" style={{width: "40%" , margin: "1%" }}
                            className="btn btn-secondary"
                            onClick={()=>this.fetchDoorLockStatus("UNLOCK")}
                    >Unlocks All Doors</button>
                </div>
            </form>
            </div>
        );
    }
}
//  return s.charAt(0).toUpperCase() + s.slice(1)

export default Profile
