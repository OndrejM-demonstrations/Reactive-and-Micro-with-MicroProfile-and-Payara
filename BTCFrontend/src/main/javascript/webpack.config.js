var path = require('path');

module.exports = {
    mode: "development",
    entry: './app/app.js',
    output: {
        path: path.resolve(__dirname, '../webapp/js'),
        filename: 'app.bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env', 'react']
                    }
                }
            }
        ]
    }

};

