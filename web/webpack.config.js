const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    newPatient: path.resolve(__dirname, 'src', 'pages', 'newPatient.js'),
    viewPatient: path.resolve(__dirname, 'src', 'pages', 'viewPatient.js'),
    searchPatients: path.resolve(__dirname, 'src', 'pages', 'searchPatients.js'),
    updatePatient: path.resolve(__dirname, 'src', 'pages', 'updatePatient.js'),
    deletePatient: path.resolve(__dirname, 'src', 'pages', 'deletePatient.js'),
    newPrescription: path.resolve(__dirname, 'src', 'pages', 'newPrescription.js'),
    viewPrescription: path.resolve(__dirname, 'src', 'pages', 'viewPrescription.js'),
    searchPrescriptions: path.resolve(__dirname, 'src', 'pages', 'searchPrescriptions.js'),
    updatePrescription: path.resolve(__dirname, 'src', 'pages', 'updatePrescription.js'),
    deletePrescription: path.resolve(__dirname, 'src', 'pages', 'deletePrescription.js'),
    home: path.resolve(__dirname, 'src', 'pages', 'home.js')
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
