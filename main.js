var num = 0;
/*jslint browser: true */
function myFunction() {
    "use strict";
    if (num >= 0 && num < 100) {
        num = num + 20;
        document.getElementById("front").style.width = num + "%";
    }
}