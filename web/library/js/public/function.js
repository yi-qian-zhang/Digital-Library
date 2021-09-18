// This function resets the style of an element
function resetStyle(item) {
    item.style = null
}


// This function returns true if the search input area is not empty
function checkSearch() {
    let input = document.getElementById("search_input")
    return input.value !== ""
}


// This function controls the display of snackbar
function snackbarAlert(content, timeoutContent) {
    clearTimeout(timeoutContent)
    let snackbar = document.getElementById("snackbar")
    let snackbarInfo = document.getElementById("snackbar_info")
    // Display the alert content
    snackbarInfo.innerHTML = content
    snackbar.style.bottom = "0"
    // Hide the snackbar after 4 seconds
    return setTimeout(() => {
        snackbar.style.bottom = -snackbar.offsetHeight + "px"
    }, 4000)
}
