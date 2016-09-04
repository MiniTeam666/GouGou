module.exports = {
	  path: 'records/:id',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
