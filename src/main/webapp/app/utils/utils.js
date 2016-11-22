/**
 * Safe quyerySelector shortcut
 * @param {String} selector - html element valid css selector
 * @returns {HTMLElement} - first element found by specific css selector
 */
var select = function (selector) {
    if (selector && document.querySelector) {
        return document.querySelector.bind(document)(selector);
    }
    return null;
};

/**
 * Safe quyerySelectorAll shortcut
 * @param {String} selector - html element valid css selector
 * @returns {NodeList} - all elements by specific selector
 */
var selectAll = function (selector) {
    if (selector && document.querySelectorAll) {
        return document.querySelectorAll(selector);
    }
    return null;
};

if (!HTMLElement.prototype.select) {
    HTMLElement.prototype.select = HTMLElement.prototype.querySelector;
}

if (!HTMLElement.prototype.selectAll) {
    HTMLElement.prototype.selectAll = HTMLElement.prototype.querySelectorAll;
}

function showToast(isSuccess, message) {
    var toastElem = isSuccess ? select('.toast-success') : select('.toast-failure');
    
    if (isSuccess) {
        select('.toast-failure').classList.remove('appear-slow');
    } else {
        select('.toast-success').classList.remove('appear-slow');
    }

    toastElem.classList.add('appear-slow');
    toastElem.select('div').innerText = message;

    toastElem.addEventListener('click', function (ev) {

        if (toastElem.classList.contains('appear-slow')) {
            toastElem.classList.remove('appear-slow');
        }
    });
    
    setTimeout(function() {
        toastElem.classList.remove('appear-slow');
    }, 4000);
}
