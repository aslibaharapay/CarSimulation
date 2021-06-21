import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import getEvent from "./event"

export const URL = 'http://localhost:8082/ws';

export class WsStompClientUtil {

  _connectPromise;
  clientSessionId= this.generateGUID();
  _subscriptions = {};
  _isStompClientConnected;
  _clientSessionId;
  _stompClient;
  _reconnectTimeout = 1;
  _disableAutoReconnect;
  _pendingRequests = {};
  _url;
  _header;
  _topicList = [];
  _socket;
  _topicErrorsSubscription;

  generateGUID(i) {
    return i
        ? (i ^ ((Math.random() * 16) >> (i / 4))).toString(16)
        : ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(
            /[018]/g,
            this.generateGUID
        );
  }
  onResponse() {}

  connect() {
    this.createStompClient();

    let header = {};
    header._clientSessionId = this.clientSessionId;

    return new Promise((resolve, reject) => {
      this._connectPromise = { resolve, reject };
      this.stompClient.connect(
          header,
          this.connected.bind(this),
          this.disconnected.bind(this)
      );
    });
  }

  createStompClient() {
    debugger;
    this._socket = new SockJS(URL);

    this.stompClient = StompJs.Stomp.over(this._socket);
    this._socket._urlInfo.sameOrigin = true;

    this.stompClient.maxWebSocketFrameSize = 1024 * 1024;
    this.stompClient.maxWebSocketChunkSize = 1024 * 1024;
    this.stompClient.splitLargeFrames = true;
    this.stompClient.onWebSocketClose = this.disconnected.bind(this);
  }

  disconnected(response) {

    this._connectPromise.reject(response);
    console.warn(`Already unsubscribed from topic`);
  }

  connected() {
    this._isStompClientConnected = true;
    this._topicErrorsSubscription = this.stompClient.subscribe(
        `/topic/errors/${this.clientSessionId}`,
        (message) => {
          this.topicErrorsHandler(message);
        }
    );

    this.subscribe();

    this._connectPromise.resolve();
  }

  topicErrorsHandler(error) {
    console.error("Error Topic Message: ", error);
  }
  subscribe() {
    this._subscriptions = [
      this.stompClient.subscribe(`/topic/public`, (message) => {
        console.log(message.body);
        let response = JSON.parse(message.body);
        let requestId = response.requestId;

        if (this._pendingRequests[requestId] === undefined) {

          getEvent().emit("broadcast-message", response);
          console.log("There is no pending request with ", requestId);
          return;
        }

        const { resolve, reject } = this._pendingRequests[requestId];

        delete this._pendingRequests[requestId];

        resolve(response);

      })
    ];
  }

  sendMessage(path, payload) {
    let requestId = this.generateGUID();

    if (!this.stompClient || !this._isStompClientConnected) {
      return new Promise((resolve, reject) => {
        //WebSocketEmitter.emit("ws-error", "Bağlantı gerçekleştirilemedi.");
        console.log("Bağlantı yok")
        reject("Stomp Client connection error.");
      });
    }

    payload = JSON.stringify({...payload,requestId});

    const options = {
      clientSessionId: this.clientSessionId,
      publicKey: 'secret-key'
    };

    this.stompClient.send(path, options ,payload);

    return new Promise((resolve, reject) => {
      this._pendingRequests[requestId] = { resolve, reject };
    });
  }


}
