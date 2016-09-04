var webpack = require('webpack');
var path = require('path');
var buildPath = path.resolve(__dirname, 'build');
var nodeModulesPath = path.resolve(__dirname, 'node_modules');
var TransferWebpackPlugin = require('transfer-webpack-plugin');
var autoprefixer = require('autoprefixer');
var precss       = require('precss');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var includeModule =  [
          path.resolve(nodeModulesPath,'company'),
          path.resolve(__dirname,'src'),
];
var config = {
  entry: {
           home: path.join(__dirname, '/src/yiyuangou/background/main/index.js')

       // home :path.join(__dirname, '/src/yiyuangou/modules/main/index.js'),
    // home:path.join(__dirname, '/src/yiyuangou/modules/home/index.js'),
    // personal:path.join(__dirname, '/src/yiyuangou/modules/personal/index.js'),
    // products:path.join(__dirname, '/src/yiyuangou/modules/products/index.js'),
    // shopping_cart:path.join(__dirname, '/src/yiyuangou/modules/shopping_cart/index.js'),
    // show:path.join(__dirname, '/src/yiyuangou/modules/show/index.js'),
    // vendor: ["react",'material-ui']
  },
  resolve: {
    //When require, do not have to add these extensions to file's name
    extensions: ["", ".js", ".jsx",".css",".less"]
    //node_modules: ["web_modules", "node_modules"]  (Default Settings)
  },
  //Render source-map file for final build
  // devtool: 'source-map',
  //output config
  output: {
    path: buildPath,    //Path of output file
    filename: '[name].app.js'  //Name of output file
  },
  plugins: [
      new webpack.optimize.CommonsChunkPlugin("vendor.bundle.js"),

    //Minify the bundle
    
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        //supresses warnings, usually from module minification
        warnings: false
      }
    }),
    // new ExtractTextPlugin("[name].css"),
    //Allows error warnings but does not stop compiling. Will remove when eslint is added
    new webpack.NoErrorsPlugin(),
    //Transfer Files
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
    //     include: [path.resolve(__dirname, "/src/pages/flow/login-components/")],
    //     exclude: [nodeModulesPath]
    //   },
    // ],
    loaders: [
      {
        test: /\.(jsx|js)$/,
        include:includeModule,
        loader: 'babel', // 'babel-loader' is also a legal name to reference
        query: {
          cacheDirectory: true,
          plugins: [
            'transform-runtime',
            'add-module-exports',
            'transform-decorators-legacy',
          ],
          presets: ['es2015', 'react', 'stage-1','stage-2','stage-3'],
        },
      },
      { 
        test: /\.(css|less|style)$/,
        // loader:ExtractTextPlugin.extract('style-loader','css-loader','postcss-loader?modules'),
        loader: 'style-loader!css-loader!postcss-loader?modules',
         include:includeModule,
      },
      {
        test: /\.(png|jpg)$/, 
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
  // Use the plugin to specify the resulting filename (and add needed behavior to the compiler)
    // plugins: [
    //     new ExtractTextPlugin("[name].css")
    // ],
  //Eslint config
  eslint: {
    configFile: '.eslintrc' //Rules for eslint
  },
};

module.exports = config;
