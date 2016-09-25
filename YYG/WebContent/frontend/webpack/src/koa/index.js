const koa = require('koa'),
  route = require('koa-route'),
  websockify = require('koa-websocket');
 
const app = websockify(koa());
 
// Note it's app.ws.use and not app.use 
app.ws.use(route.all('/test/:id', function* (next) {
  // `this` is the regular koa context created from the `ws` onConnection `socket.upgradeReq` object. 
  // the websocket is added to the context on `this.websocket`. 
  this.websocket.send('Hello World');
  var that = this;
  this.websocket.on('message', function(message) {
    // do something with the message from client 
    console.log(message);
    that.websocket.send(message);
  });
  // yielding `next` will pass the context (this) on to the next ws middleware 
  yield next;
}));
 
app.listen(3030);


// function onMessage(data) {
//     console.log(data);}


// function onOpen(data) {
//     console.log(data);}


// function onError(data) {
//     console.log(data);}


// function onClose(data) {
//     console.log(data);}
// var wsServer = 'ws://localhost:3030/test/1';
// var websocket = new WebSocket(wsServer);
// websocket.binaryType = "arraybuffer";
// websocket.onopen = onOpen;
// websocket.onclose = onClose;
// websocket.onmessage = onMessage;
// websocket.onerror = onError;
// websocket.send('test');