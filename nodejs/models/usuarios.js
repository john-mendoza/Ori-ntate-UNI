
var admin = require("firebase-admin");

var serviceAccount = require("../serviceAccount.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://orientate-uni-e277e.firebaseio.com"
});

var usuarios = {};

usuarios.createUser = function(idToken, user, callback){
  // Verify the ID token first.
  admin.auth().verifyIdToken(idToken).then((claims) => {
    if (claims.admin === true) {
      // Allow access to requested admin resource.
      admin.auth().createUser(user)
      .then(function(userRecord) {
        // See the UserRecord reference doc for the contents of userRecord.
        //console.log("Successfully created new user:", userRecord.uid);
        callback({ message : "Se creo nuevo usuario exitosamente"});
      })
      .catch(function(error) {
        //console.log("Error creating new user:", error);
        callback({ message : "Error en crear usuario: "+error.message});
      });
    }else{
      // Error, no es el admin
      callback({ message : "Error en crear usuario: No tiene permisos para hacerlo"});
    }
  });
}

// get all users
usuarios.listAllUsers = function(idToken, callback) {
  // Verify the ID token first.
  admin.auth().verifyIdToken(idToken).then((claims) => {
    if (claims.admin === true) {
      // Allow access to requested admin resource.
      // List batch of users, 1000 at a time.
      admin.auth().listUsers()
        .then(function(listUsersResult) {
          var users = [];
          for(var i = 0; i < listUsersResult.users.length; i++){
            var user = {
              uid: listUsersResult.users[i].uid,
              email: listUsersResult.users[i].email,
              customClaims: listUsersResult.users[i].customClaims
            };
            users[i] = user;
          }
          //console.log(users);
          callback({
            message : "Se obtienen "+users.length+" usuarios",
            users: users
          });
        })
        .catch(function(error) {
          //console.log("Error listing users:", error.message);
          callback({ message : "Error al obtener usuarios: "+error.message});
        });
    }else{
      // Error, no es el admin
      callback({ message : "Error en crear usuario: No tiene permisos para hacerlo"});
    }
  });
}

// Delete user
usuarios.deleteUser = function(idToken, uid, callback){
  // Verify the ID token first.
  admin.auth().verifyIdToken(idToken).then((claims) => {
    if (claims.admin === true) {
      // Allow access to requested admin resource.
      admin.auth().deleteUser(uid)
      .then(function() {
        //console.log("Successfully deleted user");
        callback({ message : "Usuario eliminado exitosamente"});
      })
      .catch(function(error) {
        //console.log("Error deleting user:", error.message);
        callback({ message : "Error al eliminar usuario: "+error.message})
      });
    }else{
      // Error, no es el admin
      callback({ message : "Error en crear usuario: No tiene permisos para hacerlo"});
    }
  });
}

usuarios.setAdmin = function(idToken, uid, adminval, callback){
  // Verify the ID token first.
  admin.auth().verifyIdToken(idToken).then((claims) => {
    if (claims.admin === true) {
      // Allow access to requested admin resource.
      // Set admin privilege on the user corresponding to uid.
      var newClaim = {};
      if(adminval == 'true'){
        newClaim.admin = true;
      }else{
        newClaim = null;
      }
      admin.auth().setCustomUserClaims(uid, newClaim)
      .then(() => {
        callback({ message : "Modo admin: "+adminval });
      });
    }else{
      // Error, no es el admin
      callback({ message : "Error en crear usuario: No tiene permisos para hacerlo"});
    }
  });
}

module.exports = usuarios;
