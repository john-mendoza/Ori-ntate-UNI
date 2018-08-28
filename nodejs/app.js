var express = require("express");
var router=express.Router();
var bodyParser = require("body-parser");
var aplicacion = express();
var usuarios = require("./routes/rutasUsuarios");

//Show the rest API - Oriéntate UNI
router.get('/', function(request, response) {
response.status(200).json({"mensaje":"REST Api de Orientate UNI"});
});

aplicacion.use(bodyParser.json({limit: '50mb'}));
aplicacion.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

aplicacion.use(function(req, res, next){
  // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', '*');
    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

    // Request headers you wish to allow
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    res.setHeader('Access-Control-Allow-Credentials', true);

    next();
})
aplicacion.use(router);
aplicacion.use(usuarios);

aplicacion.listen(8080, function() {
console.log("Servidor iniciado on PORT: 8080");
});
