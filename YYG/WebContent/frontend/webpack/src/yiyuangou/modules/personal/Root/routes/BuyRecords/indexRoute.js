module.exports = {
  path: 'buyrecords',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
