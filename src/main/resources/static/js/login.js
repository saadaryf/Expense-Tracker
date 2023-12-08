const loginButton = document.getElementById('login-button');
const signUpButton = document.getElementById('signup-button');
const loginForm = document.getElementById('login-form');
const signUpForm = document.getElementById('signup-form');

signUpButton.addEventListener('click', function(){
    loginForm.classList.add('hidden');
    signUpButton.classList.add('hidden');
    loginButton.classList.remove('hidden');
    signUpForm.classList.remove('hidden');
})

loginButton.addEventListener('click', function(){
    loginButton.classList.add('hidden');
    signUpForm.classList.add('hidden');
    loginForm.classList.remove('hidden');
    signUpButton.classList.remove('hidden');
})