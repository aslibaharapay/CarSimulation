import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import {BrowserRouter} from "react-router-dom";
import {WsStompClientUtil} from "./wsStompClient";

const wsUtil = new WsStompClientUtil();
window.wsUtil = wsUtil;
const events = require('events');
window.eventEmit = new events.EventEmitter();

ReactDOM.render(<BrowserRouter><App /></BrowserRouter>, document.getElementById('root'));
registerServiceWorker();
