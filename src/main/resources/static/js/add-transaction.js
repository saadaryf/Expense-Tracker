
const cashInCategories = document.querySelector('.categories.cash-in');
const cashOutCategories = document.querySelector('.categories.cash-out');
const hideCashInBtutton = document.getElementById('hide-categories-button');
const hideCashOutBtutton = document.getElementById('hide-categories-button2');
const categoryButtons = document.querySelectorAll('button');
const saveTransactionButton = document.getElementById('save-btn');
const transactionForm = document.getElementById('transaction-form');
const dateField = document.getElementById('date');
const categoryField = document.getElementById('category');
const transactionType = localStorage.getItem('transactionType');

let isOpen = true;

document.addEventListener('DOMContentLoaded', setCashType);
document.addEventListener('DOMContentLoaded', setTodaysDate);

saveTransactionButton.addEventListener('click', ValidateDate);
categoryButtons.forEach((button) => {
    const categoryName = button.querySelector('p').innerText;
    button.addEventListener('click', () => setCategoryType(categoryName));
});

function setCashType() {
    const cashType = localStorage.getItem('cashType');
    if (cashType === 'cashIn') {
        cashOutCategories.classList.add('hidden');
        cashInCategories.classList.remove('hidden');
        saveTransactionButton.value = 'Save Transaction';
        transactionType === 'save' ? (transactionForm.action = '/transaction/save?type=cash-in') : updateFieldsAndButtonName();
        hideCashInBtutton.addEventListener('click',
            (event) => toggleCategories(event, hideCashInBtutton, cashInCategories));
    } else {
        cashInCategories.classList.add('hidden');
        cashOutCategories.classList.remove('hidden');
        saveTransactionButton.value = 'Save Transaction';
        transactionType === 'save' ? (transactionForm.action = '/transaction/save?type=cash-in') : updateFieldsAndButtonName();
        hideCashOutBtutton.addEventListener('click',
            (event) => toggleCategories(event, hideCashOutBtutton, cashOutCategories));
    }
}
function toggleCategories(event, clickedButton, categoriesDiv) {
    event.preventDefault();

    if (isOpen) {
        categoriesDiv.style.height = '0%';
        categoriesDiv.style.minHeight = '30px';
        categoriesDiv.style.overflowY = 'hidden';

        clickedButton.style.color = 'purple';
        clickedButton.style.transform = 'rotate(180deg)';
        clickedButton.style.borderTopRightRadius = '0px'
        clickedButton.style.borderTopLeftRadius = '0px'
        clickedButton.style.borderBottomLeftRadius = '10px';
        clickedButton.style.borderBottomRightRadius = '10px';
        isOpen = false;
    } else {
        categoriesDiv.style.height = '40%';
        categoriesDiv.style.minHeight = '200px';
        categoriesDiv.style.overflowY = 'auto';

        clickedButton.style.color = 'green';
        clickedButton.style.transform = 'rotate(0deg)';
        clickedButton.style.borderTopRightRadius = '10px'
        clickedButton.style.borderTopLeftRadius = '10px'
        clickedButton.style.borderBottomLeftRadius = '0px';
        clickedButton.style.borderBottomRightRadius = '0px';
        isOpen = true;
    }
}

function setCategoryType(categoryName) {
    categoryField.value = categoryName;
}

function ValidateDate(event) {
    const errorPopup = document.getElementById('error-popup');
    const datePattern = /^\d{2}-\d{2}-\d{4}$/;

    if (!datePattern.test(dateField.value)) {
        errorPopup.innerText = 'Please Enter a Valid Date format (dd-mm-yyyy)';
        errorPopup.style.display = 'block';
        event.preventDefault();
    }
}

function setTodaysDate() {
    const currentDate = new Date();

    const day = currentDate.getDate().toString().padStart(2, '0');
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    const year = currentDate.getFullYear();

    const formattedDate = `${day}-${month}-${year}`;

    dateField.value = formattedDate;
}

function updateFieldsAndButtonName(){
    var urlParams = new URLSearchParams(window.location.search);

    categoryField.value = urlParams.get('name');
    document.getElementById('amount').value = urlParams.get('amount');
    document.getElementById('description').value = urlParams.get('description');
    const id = urlParams.get('id');
    const type = urlParams.get('type');
    transactionForm.action = `/transaction/update?id=${id}&type=${type}`;
    saveTransactionButton.value = 'Update Transaction';
    console.log('transaction type  = ' + transactionType);
}
console.log('transaction type  out = ' + transactionType);
