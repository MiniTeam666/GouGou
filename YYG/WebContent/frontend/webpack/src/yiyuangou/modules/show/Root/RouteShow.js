module.exports = {
	  path: 'show',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./Posts'))
    })
  }
}
