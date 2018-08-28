firebase.initializeApp({
  apiKey: "AIzaSyDHxUDcjemME87g5c4rH7SnaIskVYDSWJ0",
  authDomain: "orientate-uni-e277e.firebaseapp.com",
  projectId: "orientate-uni-e277e"
});

// Initialize Cloud Firestore through Firebase
var db = firebase.firestore();

var loginLink = document.getElementById('loginLink');
var logoutLink = document.getElementById('logoutLink');
var emailaddress = document.getElementById('emailaddress');
var myModal = document.getElementById('myModal');
var myModal2 = document.getElementById('myModal2');
var tabla = document.getElementById('tabla');

logoutLink.addEventListener('click', e => {
  firebase.auth().signOut();
});

firebase.auth().onAuthStateChanged(function(user){
  if(user){
    var email = user.email;
    user.getIdToken(true)
    .then((idToken) => {
      // Parse the ID token.
      const payload = JSON.parse(b64DecodeUnicode(idToken.split('.')[1]));
      // Confirm the user is an Admin.
      if (!!payload['admin']) {
        showAdminUI(email, true);
        getAllUsers();
      }else{
        console.log('No es admin');
        showAdminUI(email,false);
      }
    })
    .catch((error) => {
      console.log(error);
      showAdminUI(email,false);
    })
  }else {
    console.log('No logueado');
    showAdminUI(email,false);
  }
});

function getAllUsers(){
  var currUid = firebase.auth().currentUser.uid;
  firebase.auth().currentUser.getIdToken(true)
  .then((idToken) => {
    $.getJSON(
      'http://localhost:8080/admin/usuarios?idToken='+idToken,
      (data, status) => {
        if(status == 'success' && data){
          if(data.users){
            tabla.innerHTML = '';
            var count = 0;
            data.users.forEach((user) => {
              var rol = '';
              var isAdmin = false;
              var botones = ``;
              if(user.customClaims && user.customClaims.admin){
                rol += 'admin';
                isAdmin = true;
              }
              if(currUid == user.uid){
                tabla.innerHTML += `
                  <tr>
                    <td>${user.email}</td>
                    <td>${rol}</td>
                    <td></td>
                    <td></td>
                  </tr>
                `;
              }else{
                tabla.innerHTML += `
                  <tr>
                    <td>${user.email}</td>
                    <td>${rol}</td>
                    <td><button class="btn btn-warning" onclick="editarRol('${user.uid}','${user.email}',${isAdmin})">Editar Rol</button></td>
                    <td><button class="btn btn-danger" onclick="eliminarUsuario('${user.uid}','${user.email}')">Eliminar</button></td>
                  </tr>
                `;
              }
            });
          }
        }
    });
  })
  .catch((error) => {
    console.log(error);
  })
}

function agregar(event){
  event.preventDefault();
  var email = $('#emailtxt').val();
  var pass = $('#passtxt').val();

  $("#myModal").modal('hide');

  firebase.auth().currentUser.getIdToken(true)
  .then((idToken) => {
    $.post(
      'http://localhost:8080/admin/usuario',
      {
        idToken: idToken,
        email: email,
        password: pass
      },
      (data, status) => {
        if (status == 'success' && data) {
          console.log(data.message);
          getAllUsers();
        }
      });
  })
  .catch((error) => {
    console.log(error);
  });
}

function editarRol(uid,email,isAdmin){
  $("#myModal3").modal('show');
  $("#emailEditar").html(email);
  $("#checkAdmin").prop('checked',isAdmin);
  var btnEditarRol = document.getElementById('btnEditarRol');
  btnEditarRol.onclick = function(){
    var adminval = $('#checkAdmin').prop('checked');
    $("#myModal3").modal('hide');

    firebase.auth().currentUser.getIdToken(true)
    .then((idToken) => {
      $.post(
        'http://localhost:8080/admin/setAdmin',
        {
          idToken: idToken,
          uid: uid,
          adminval: adminval
        },
        (data, status) => {
          if (status == 'success' && data) {
            console.log(data.message);
            getAllUsers();
          }
        });
    })
    .catch((error) => {
      console.log(error);
    });
  }
}

function eliminarUsuario(uid,email){
  $("#myModal2").modal('show');
  $("#emailEliminar").html(email);
  var btnEliminar = document.getElementById('btnEliminar');
  btnEliminar.onclick = function(){
    $("#myModal2").modal('hide');

    firebase.auth().currentUser.getIdToken(true)
    .then((idToken) => {
      $.post(
        'http://localhost:8080/admin/usuario/remove',
        {
          idToken: idToken,
          uid: uid
        },
        (data, status) => {
          if (status == 'success' && data) {
            console.log(data.message);
            getAllUsers();
          }
        });
    })
    .catch((error) => {
      console.log(error);
    });
  }
}

function nuevo(){
  $("#myModal").modal('show');
  $('#emailtxt').val('');
  $('#passtxt').val('');
  var myForm = document.getElementById('myForm');
  myForm.onsubmit = agregar;
}

function showAdminUI(email, isAdmin){
  if(email){
    loginLink.style.display = 'none';
    emailaddress.innerHTML = email;
    if(isAdmin)
      console.log('Es admin');
    else
      window.location.replace("index.html");
  }else{
    window.location.replace("index.html");
  }
}

function b64DecodeUnicode(str) {
    // Going backwards: from bytestream, to percent-encoding, to original string.
    return decodeURIComponent(atob(str).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}
