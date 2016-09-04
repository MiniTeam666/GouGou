module.exports = {
  path: 'product/add',

  name:'添加商品',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
