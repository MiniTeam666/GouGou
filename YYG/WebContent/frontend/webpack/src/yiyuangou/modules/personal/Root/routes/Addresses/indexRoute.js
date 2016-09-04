module.exports = {
  path: 'addresses',

  getComponent(nextState, cb) {
    require.ensure([], (require) => {
      cb(null, require('./index.js'))
    })
  },
  getChildRoutes(location, callback) {
    require.ensure([], function (require) {
      callback(null, [
        require('./routes/AddressDetail/indexRoute'),
      ])
    })
  },
}
