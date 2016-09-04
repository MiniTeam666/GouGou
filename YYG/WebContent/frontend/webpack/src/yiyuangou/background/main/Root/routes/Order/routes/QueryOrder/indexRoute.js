module.exports = {
  path: 'order/query',

  name:'查询订单',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
