var path = require('path');

module.exports = {
	entry: path.join(process.cwd(), "app", "app.jsx"),
	output: {
		path: path.join(process.cwd(), "public"),
		filename: "bundle.js",
		publicPath: "/"
	},
	devServer: {
		inline: true,
		contentBase: path.join(process.cwd(), "public"),
		port: 3000
	},
	module: {
		loaders: [{
			test: /\.jsx$/,
			exclude: /node_modules/,
			loaders: ['babel-loader?presets[]=react'],
		}, {
			test: /\.css$/,
			loader: 'style-loader!css-loader'
		}, {
			test: /\.woff$|\.eot$|\.ttf$|\.woff2$|\.svg$/,
			loaders: ['url-loader?name=/'],
		}]
	}
}