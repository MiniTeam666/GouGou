module.exports = {
	  path: 'personal',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./User'))
    })
  },
  getChildRoutes(location, callback) {

    require.ensure([], function (require) {
      callback(null, [
        require('./routes/Data/indexRoute'),
        require('./routes/Addresses/indexRoute'),
        require('./routes/BuyRecords/indexRoute'),
      ])
    })
  },
}
