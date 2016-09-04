module.exports = {
  path: 'account/add',

  name:'添加帐号',
  
  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
