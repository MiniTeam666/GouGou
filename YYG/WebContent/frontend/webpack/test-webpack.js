var webpack = require('webpack');
var path = require('path');
var buildPath = path.resolve(__dirname, 'build');
var nodeModulesPath = path.resolve(__dirname, 'node_modules');
var TransferWebpackPlugin = require('transfer-webpack-plugin');
var autoprefixer = require('autoprefixer');
var precss       = require('precss');
var includeModule =  [
          path.resolve(nodeModulesPath,'company'),
          path.resolve(__dirname,'src'),
];
var config = {
  //Entry points to the project
  entry: [
    'webpack/hot/dev-server',
    'webpack/hot/only-dev-server',
        path.join(__dirname, '/src/yiyuangou/background/main/index.js')
    // path.join(__dirname, '/src/waibao/home/index.js')

    // path.join(__dirname, '/src/yiyuangou/modules/main/index.js')

    // path.join(__dirname, '/src/yiyuangou/modules/test/router/app.js')
    // path.join(__dirname, '/src/pages/flow/Server/index.js'),

  ],
  
  //Config options on how to interpret requires imports
  resolve: {
    extensions: ["", ".js", ".jsx",".css",".less"]
    //node_modules: ["web_modules", "node_modules"]  (Default Settings)
  },
  //Server Configuration options
  devServer:{
    contentBase: 'src/temp/pages/flow/html/',  //Relative directory for base of server
    devtool: 'eval',
    hot: true,        //Live-reload
    inline: true,
    port: 3000        //Port Number
  },
  devtool: 'eval',
  output: {
    path: buildPath,    //Pathof output file
    filename: 'app.js'
  },
  plugins: [
    //Enables Hot Modules Replacement
    new webpack.HotModuleReplacementPlugin(),
    //Allows error warnings but does not stop compiling. Will remove when eslint is added
    new webpack.NoErrorsPlugin(),
    //Moves files
    new TransferWebpackPlugin([
      {from: 'src'}
    ], path.resolve(__dirname, "src/temp/pages/flow"))
  ],
  module: {
    //Loaders to interpret non-vanilla javascript code as well as most other extensions including images and text.
    // preLoaders: [
    //   {
    //     //Eslint loader
    //     test: /\.(js|jsx)$/,
    //     loader: 'eslint-loader',
    //     include: includeModule,
    //   },
    // ],
    loaders: [
      {
        test: /\.(jsx|js)$/,
        // exclude:[nodeModulesPath],
        include:includeModule,
        loader: 'babel', // 'babel-loader' is also a legal name to reference
        query: {
          cacheDirectory: true,
          plugins: [
            'transform-runtime',
            'add-module-exports',
            'transform-decorators-legacy',
          ],
          presets: ['es2015', 'react','stage-1','stage-2', 'stage-3'],
        },
      },
      { 
        test: /\.(css|less|style)$/,
        loader: 'style-loader!css-loader!postcss-loader?modules',
        include:includeModule,
      },
      {
        test: /\.(png|jpg|gif)$/, 
        loader: 'url-loader?limit=8192',
        include:includeModule,
      },
      {
        test: /\.(txt)$/, 
        loader: 'raw-loader',
        include:includeModule,
      },
      {
        test: /\.(woff|woff2|eot|ttf|svg)$/i,
        loader: "file-loader?name=fonts/[name]-[hash].[ext]"
      }
    ]
  },
  postcss: function () {
        return [precss, autoprefixer];
  },
  // eslint config options. Part of the eslint-loader package
  // eslint: {
  //   configFile: '.eslintrc'
  // },
};

module.exports = config;
