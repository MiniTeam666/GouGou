var  koa = require( 'koa' );
var app = koa();

app.use(function *(next){
  var start = new Date;
  yield next;
  var ms = new Date - start;
  this.set('X-Response-Time', ms + 'ms');
  console.log(123);
  this.body='123';
});

app.listen(3000);