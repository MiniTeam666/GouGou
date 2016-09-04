module.exports = {
	  path: 'shows/:id',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  }
}
