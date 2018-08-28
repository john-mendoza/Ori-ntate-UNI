// Initialize Firebase
var config = {
  apiKey: "AIzaSyDHxUDcjemME87g5c4rH7SnaIskVYDSWJ0",
  authDomain: "orientate-uni-e277e.firebaseapp.com",
  projectId: "orientate-uni-e277e"
};
firebase.initializeApp(config);

var emailtxt = document.getElementById('emailtxt');
var passtxt = document.getElementById('passtxt');
var errortxt = document.getElementById('errortxt');
var btnAceptar = document.getElementById('btnAceptar');

// Añadir Evento login
btnAceptar.addEventListener('click', e => {
  //Obtener email y pass
  const email = emailtxt.value;
  const pass = passtxt.value;
  const auth = firebase.auth();
  // Sign in
  const promise = auth.signInWithEmailAndPassword(email, pass);
  promise.catch(e => {errortxt.innerHTML = e.message;});
});

firebase.auth().onAuthStateChanged(function(user){
  if(user)
    window.location.replace("index.html");
});
