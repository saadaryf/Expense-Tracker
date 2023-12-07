
const cashInCategories = document.querySelector('.categories.cash-in');
const cashOutCategories = document.querySelector('.categories.cash-out');


document.addEventListener('DOMContentLoaded', setCashType);



function setCashType(){
    const cashType = localStorage.getItem('cashType');
    if(cashType === 'cashIn'){
        cashOutCategories.classList.add('hidden');
        cashInCategories.classList.remove('hidden');
    }else{
        cashInCategories.classList.add('hidden');
        cashOutCategories.classList.remove('hidden');
    }
}

