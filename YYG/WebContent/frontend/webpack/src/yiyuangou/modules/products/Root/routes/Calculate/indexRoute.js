module.exports = {
  path: 'calculate/:id',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
