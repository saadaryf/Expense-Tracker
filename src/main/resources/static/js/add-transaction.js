
const cashInCategories = document.querySelector('.categories.cash-in');
const cashOutCategories = document.querySelector('.categories.cash-out');
const hideCashInBtutton = document.getElementById('hide-categories-button');
const hideCashOutBtutton = document.getElementById('hide-categories-button2');

let isOpen = true;

document.addEventListener('DOMContentLoaded', setCashType);

function setCashType() {
    const cashType = localStorage.getItem('cashType');
    if (cashType === 'cashIn') {
        cashOutCategories.classList.add('hidden');
        cashInCategories.classList.remove('hidden');
        hideCashInBtutton.addEventListener('click',
            (event) => toggleCategories(event, hideCashInBtutton, cashInCategories));
    } else {
        cashInCategories.classList.add('hidden');
        cashOutCategories.classList.remove('hidden');
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