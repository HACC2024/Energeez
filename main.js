var num = 200;
/*jslint browser: true */
function myFunction() {
    "use strict";
    if (num >= 0) {
        num = num - 20;
    }
    document.getElementById("front").style.width = num + "px";
}