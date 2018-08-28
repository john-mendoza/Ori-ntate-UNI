var express = require('express');
var router = express.Router(); //create the object for define the routs
var usuariosModel = require('../models/usuarios'); //Import the model that execution the sentences of the database

//Showing all users
router.get('/admin/usuarios',function(req,res){ 
	var idToken = req.query.idToken;
	usuariosModel.listAllUsers(idToken, function(data){
		res.status(200).json(data);
	});
});

//Insertar a user
router.post('/admin/usuario',function(req,res){
	var idToken = req.body.idToken;
	var user = {
		email : req.body.email,
		password : req.body.password
	};
	usuariosModel.createUser(idToken, user, function(data){
		res.status(200).json(data);
	});
});

//Change a user
router.post('/admin/setAdmin',function(req,res){
	var idToken = req.body.idToken;
	var	uid = req.body.uid;
	var	adminval = req.body.adminval;
	usuariosModel.setAdmin(idToken, uid, adminval, function(data){
		res.status(200).json(data);
	});
});

//Delete a user
router.post('/admin/usuario/remove',function(req,res){
	var idToken = req.body.idToken;
	var uid = req.body.uid;
	usuariosModel.deleteUser(idToken, uid, function(data){
		res.status(200).json(data);
	});
});

module.exports = router;
