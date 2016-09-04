module.exports = {
	  path: 'products',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./Root.js'))
    })
  },
 
  getChildRoutes(location, callback) {
    require.ensure([], function (require) {
      callback(null, [
        require('./routes/Detail/indexRoute'),
        require('./routes/Records/indexRoute'),
        require('./routes/Shows/indexRoute'),
        require('./routes/Describe/indexRoute'),
        require('./routes/Calculate/indexRoute'),

      ])
    })
  },

  // getIndexRoute(location, callback) {
  //   require.ensure([], function (require) {
  //     callback(null, require('./IndexRoute'))
  //   })
  // }

 
}
