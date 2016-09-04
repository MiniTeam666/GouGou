module.exports = {
	  path: 'shopping_cart',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./Root.js'))
    })
  },
  getChildRoutes(location, callback) {
    require.ensure([], function (require) {
      callback(null, [
        require('./routes/PayInfo/indexRoute')
      ])
    });
  }
}
