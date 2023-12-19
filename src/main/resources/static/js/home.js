
const listButton = document.getElementById('list-button');
const overviewButton = document.getElementById('overview-button');
const listDiv = document.querySelector('.list');
const overviewDiv = document.querySelector('.overview');
const mainButtons = document.querySelector('.main-buttons');
const cashInButton = document.getElementById('cash-in-button');
const cashOutButton = document.getElementById('cash-out-button');
const rating = document.querySelector(".rating");
const chartBars = document.querySelectorAll('.bar-chart div');
const analyticsDiv = document.querySelector('.analytics');
const cashInItems = document.querySelectorAll('.list-item.cash-in');
const cashOutItems = document.querySelectorAll('.list-item.cash-out');
const profileDiv = document.querySelector('.profile');
const updateForm = document.getElementById('update-form');
const openProfileButton = document.getElementById('profile-button');
const editProfileButton = document.getElementById('edit-profile-button');
const deleteAccountButton = document.getElementById('delete-account-button');
const closeProfileButton = document.getElementById('close-profile-button');

let profileIsNotEditable = true;
let listDispayed = true;
let overviewDisplayed = false;


deleteAccountButton.addEventListener('click', confirmDelete);
openProfileButton.addEventListener('click', openProfile);
editProfileButton.addEventListener('click', makeProfileEditable);
listButton.addEventListener('click', showList);
overviewButton.addEventListener('click', showOverview);
cashInButton.addEventListener('click', setCashInToLocalStorage);
cashInButton.addEventListener('click', setTransactionTypeToSave);
cashOutButton.addEventListener('click', setCashOutToLocalStorage);
cashOutButton.addEventListener('click', setTransactionTypeToSave);
closeProfileButton.addEventListener('click', function () {
  profileDiv.classList.remove('active');
})
cashInItems.forEach((item) => {
  item.addEventListener('click', setCashInToLocalStorage);
  item.addEventListener('click', setTransactionTypeToUpdate);
})
cashOutItems.forEach((item) => {
  item.addEventListener('click', setCashOutToLocalStorage);
  item.addEventListener('click', setTransactionTypeToUpdate);
})

document.addEventListener('DOMContentLoaded', setRating);
document.addEventListener('DOMContentLoaded', updateDate);
document.addEventListener('DOMContentLoaded', calculateChartBarsHeight);
if (isInViewport(analyticsDiv) && window.innerWidth <= 767) {
  analyticsDiv.classList.add("slide");
}


function showList() {
  if (overviewDisplayed) {
    overviewDiv.classList.add("hidden");
    listDiv.classList.remove("hidden");
    overviewButton.classList.remove("active");
    listButton.classList.add("active");
    mainButtons.classList.remove("hidden");
    overviewDisplayed = false;
    listDispayed = true;
  }
}
function showOverview() {
  if (listDispayed) {
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
  const previousDate = document.getElementById("start-date");
  const todayDate = document.getElementById("end-date");
  const currentDate = new Date();

  // Date of the same day in the next month
  const prevMonthDate = new Date(currentDate);
  prevMonthDate.setMonth(currentDate.getMonth() - 1);
  const prevMonthDay = prevMonthDate.toLocaleDateString('en-US', { day: '2-digit' });
  const prevMonthMonth = prevMonthDate.toLocaleDateString('en-US', { month: '2-digit' });
  const prevMonthYear = prevMonthDate.toLocaleDateString('en-US', { year: 'numeric' });
  const prevMonthDateString = `${prevMonthYear}-${prevMonthMonth}-${prevMonthDay}`;

  // Current date
  const currentDay = currentDate.toLocaleDateString('en-US', { day: '2-digit' });
  const currentMonth = currentDate.toLocaleDateString('en-US', { month: '2-digit' });
  const currentYear = currentDate.toLocaleDateString('en-US', { year: 'numeric' });
  const currentDateString = `${currentYear}-${currentMonth}-${currentDay}`;

  previousDate.value = `${prevMonthDateString}`;
  todayDate.value = `${currentDateString}`;
}

function setRating() {
  const ratingContent = rating.innerHTML;
  const ratingScore = parseInt(ratingContent, 10);
  const scoreClass =
    ratingScore < 60 ? "good" : ratingScore < 80 ? "medium" : "bad";
  rating.classList.add(scoreClass);
  const ratingColor = window.getComputedStyle(rating).backgroundColor;
  const gradient = `background: conic-gradient(${ratingColor} ${ratingScore}%, transparent 0 100%)`;
  rating.setAttribute("style", gradient);
  rating.innerHTML = `<span>${isNaN(ratingScore) ? 0 : ratingScore} ${ratingContent.indexOf("%") >= 0 ? "<small>%</small>" : ""
    }</span>`;
}
function setCashInToLocalStorage() {
  const cashType = "cashIn";
  localStorage.setItem('cashType', cashType);
}
function setCashOutToLocalStorage() {
  const cashType = "cashOut";
  localStorage.setItem('cashType', cashType);
}

function calculateChartBarsHeight() {
  chartBars.forEach((bar) => {
    const barHeight = parseFloat(bar.querySelector('h6').innerText);
    const barName = bar.querySelector('p').innerText;
    if (barName == 'Remaining' && barHeight <= 35) {
      bar.style.backgroundColor = 'orange';
    }
    if (barName == 'Spent' && barHeight >= 87) {
      bar.style.backgroundColor = 'red';
    }
    if (barHeight <= 12) {
      bar.style.display = 'none';
    } else {
      const barTop = 100 - barHeight;
      bar.style.display = 'flex';
      bar.style.height = `${barHeight}%`;
      bar.style.top = `${barTop}%`;
    }
  })
}

function isInViewport(element) {
  const rect = element.getBoundingClientRect();
  return (
    rect.top >= 0 &&
    rect.left >= 0 &&
    rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
    rect.right <= (window.innerWidth || document.documentElement.clientWidth)
  );
}
function setTransactionTypeToSave() {
  const transactionType = "save";
  localStorage.setItem('transactionType', transactionType);
}
function setTransactionTypeToUpdate() {
  const transactionType = "update";
  localStorage.setItem('transactionType', transactionType);
}
function openProfile(event) {
  event.stopPropagation();
  profileDiv.classList.add('active');
  document.addEventListener('click', closeProfile);
}

function closeProfile(event) {
  if (!profileDiv.contains(event.target)) {
    profileDiv.classList.remove('active');
    document.removeEventListener('click', closeProfile);
  }
}
function makeProfileEditable(event) {
  event.preventDefault();
  const nameField = document.getElementById('name');
  const usernameField = document.getElementById('username');

  if (profileIsNotEditable) {
    editProfileButton.innerText = "Update Profile";
    nameField.readOnly = false;
    nameField.classList.add('editable');
    usernameField.readOnly = false;
    usernameField.classList.add('editable');
    profileIsNotEditable = false;
  } else {
    $.ajax({
      url: '/user/update',
      method: 'POST',
      data: $('#update-form').serialize(),
      success: function (data) {
        $('#update-user-popup').removeClass("active");
        editProfileButton.innerText = "Edit Profile";
        nameField.readOnly = true;
        nameField.classList.remove('editable');
        usernameField.readOnly = true;
        usernameField.classList.remove('editable');
        profileIsNotEditable = true;
      },
      error: function (jqXHR, textStatus, errorThrown) {
        var errorMessage = "Error fetching Data!";

        if (textStatus === 'error' && jqXHR.status === 500) {
          $('body').addClass('unclickable');
          var counter = 3;
          var intervalId = setInterval(function () {
            $('#update-user-popup').text('After updating username, Login again in ' + counter + ' seconds..').addClass("active");
            counter--;
            if (counter < 0) {
              $('#logout-form').submit();
              clearInterval(intervalId);
            }
          }, 1000);
        } else {
          try {
            var errorResponse = JSON.parse(jqXHR.responseText);
            if (errorResponse.details) {
              errorMessage = errorResponse.details;
            }
          } catch (parseError) {
            console.log('Error parsing JSON response:', parseError);
          }
          $('#update-user-popup').text(errorMessage).addClass("active");
        }
      }
    });
    event.preventDefault();
  }
}
function confirmDelete(event) {
  var isConfirmed = confirm("Are you sure you want to delete your account?");
  if (!isConfirmed) {
    event.preventDefault();
  }
}


