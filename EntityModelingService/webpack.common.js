const path = require('path');

module.exports = {
  entry: {
    app: './js/definitions.js',
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    clean: true,
    filename: './js/definitions.js',
  },
};
