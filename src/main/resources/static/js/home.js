
const listButton = document.getElementById('list-button');
const overviewButton = document.getElementById('overview-button');
const listDiv = document.querySelector('.list');
const overviewDiv = document.querySelector('.overview');
const mainButtons = document.querySelector('.main-buttons');
const cashInButton = document.getElementById('cash-in-button');
const cashOutButton = document.getElementById('cash-out-button');
const rating = document.querySelector(".rating");


listButton.addEventListener('click',showList);
overviewButton.addEventListener('click', showOverview);
cashInButton.addEventListener('click',setCashInToLocalStorage);
cashOutButton.addEventListener('click',setCashOutToLocalStorage);
document.addEventListener('DOMContentLoaded', setRating);

document.addEventListener('DOMContentLoaded', updateDate);


let listDispayed = true;
let overviewDisplayed = false;

function showList(){
    if(overviewDisplayed){
        overviewDiv.classList.add("hidden");
        listDiv.classList.remove("hidden");
        overviewButton.classList.remove("active");
        listButton.classList.add("active");
        mainButtons.classList.remove("hidden");
        overviewDisplayed = false;
        listDispayed = true;
    }
}
function showOverview(){
    if(listDispayed){
        listDiv.classList.add("hidden");
        overviewDiv.classList.remove("hidden");
        listButton.classList.remove("active");
        overviewButton.classList.add("active");
        mainButtons.classList.add("hidden");
        listDispayed = false;
        overviewDisplayed = true;
    }
}
function updateDate() {
    const dateField = document.getElementById("date-field");
    const currentDate = new Date(); 
    const day = currentDate.toLocaleDateString('en-US', { day: 'numeric' });
    const month = currentDate.toLocaleDateString('en-US', { month: 'short' });

    dateField.textContent = `${day} ${month}`; 
}
function setRating(){
  const ratingContent = rating.innerHTML;
  const ratingScore = parseInt(ratingContent, 10);
  const scoreClass =
    ratingScore < 40 ? "bad" : ratingScore < 60 ? "meh" : "good";
  rating.classList.add(scoreClass);
  const ratingColor = window.getComputedStyle(rating).backgroundColor;
  const gradient = `background: conic-gradient(${ratingColor} ${ratingScore}%, transparent 0 100%)`;
  rating.setAttribute("style", gradient);
  rating.innerHTML = `<span>${ratingScore} ${
    ratingContent.indexOf("%") >= 0 ? "<small>%</small>" : ""
  }</span>`;
}
function setCashInToLocalStorage(){
  const cashType = "cashIn";
  localStorage.setItem('cashType', cashType);
}
function setCashOutToLocalStorage(){
  const cashType = "cashOut";
  localStorage.setItem('cashType', cashType);
}