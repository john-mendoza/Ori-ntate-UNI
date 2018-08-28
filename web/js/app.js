// Initialize Firebase
var config = {
  apiKey: "AIzaSyDHxUDcjemME87g5c4rH7SnaIskVYDSWJ0",
  authDomain: "orientate-uni-e277e.firebaseapp.com",
  projectId: "orientate-uni-e277e"
};
firebase.initializeApp(config);

var eventosLink = document.getElementById('eventosLink');
var usuariosLink = document.getElementById('usuariosLink');
var loginLink = document.getElementById('loginLink');
var logoutLink = document.getElementById('logoutLink');
var emailLogged = document.getElementById('emailLogged');
var emailaddress = document.getElementById('emailaddress');

logoutLink.addEventListener('click', e => {
  firebase.auth().signOut();
});

function showUI(email, isAdmin){
  if(email){
    loginLink.style.display = 'none';
    emailaddress.innerHTML = email;
    if(!isAdmin)
      usuariosLink.style.display = 'none';
  }else{
    loginLink.style.display = 'block';
    eventosLink.style.display = 'none';
    usuariosLink.style.display = 'none';
    logoutLink.style.display = 'none';
    emailLogged.style.display = 'none';
  }
}

firebase.auth().onAuthStateChanged(function(user){
  if(user){
    var email = user.email;
    user.getIdToken(true)
    .then((idToken) => {
      // Parse the ID token.
      const payload = JSON.parse(b64DecodeUnicode(idToken.split('.')[1]));
      // Confirm the user is an Admin.
      if (!!payload['admin']) {
        showUI(email, true);
      }else{
        showUI(email, false);
      }
    })
    .catch((error) => {
      console.log(error);
      showUI(email, false);
    })
  }else{
    // User is signed out
    showUI();
  }
});

function b64DecodeUnicode(str) {
    // Going backwards: from bytestream, to percent-encoding, to original string.
    return decodeURIComponent(atob(str).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}
