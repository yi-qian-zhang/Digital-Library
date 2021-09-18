// This function listens the logout button. As the logout button is clicked, sign out by AJAX and refresh page
function listenLogout(logoutBtn) {
    logoutBtn.onclick = () => {
        // Create XMLHTTP object according to the browser
        let xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
        // Only when the withCredentials argument is true, the server can get the session
        xmlHttp.withCredentials = true
        // Actually, using GET method is also available
        xmlHttp.open("POST", "/servlet/AjaxSignOut", true)
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
        xmlHttp.send()
        // Listen the change of readyStatus
        xmlHttp.onreadystatechange = () => {
            // Once received HTTP 200 code and ready status number 4, reload the page
            if (xmlHttp.status === 200 && xmlHttp.readyState === 4) {
                // So far, the session of the client has been reset
                // Thus, the reload request will be redirected to the index page by the server
                location.reload()
                // Or use JavaScript's widow.location.href to redirect to the index page
                // window.location.href = "/library/"
            }
        }
    }
}


// This function listens the search button and its close button to control the display of the search bar
function listenSearch(searchBar, searchBtn, closeBtn) {
    // Initialize the position of the search bar
    searchBar.style.top = -searchBar.offsetHeight + "px"
    // When the search button is clicked
    searchBtn.onclick = () => {
        searchBar.style.top = "0"
        closeBtn.style.transform = "rotate(180deg)"
    }
    // When the close button is clicked
    closeBtn.onclick = () => {
        searchBar.style.top = -searchBar.offsetHeight + "px"
        closeBtn.style.transform = "rotate(0)"
    }
}


// This function listens the display of snack bar
function listenSnackbarCloseBtn(snackbar, snackbarClose) {
    // Initialize the position of the snackbar
    snackbar.style.bottom = -snackbar.offsetHeight + "px"
    // When the close button of the snackbar is clicked
    snackbarClose.onclick = () => {
        clearTimeout(timeoutContent)
        snackbar.style.bottom = -snackbar.offsetHeight + "px"
    }
}


// This function uses AJAX to initialize the list of available majors
function initMajorList(majorList) {
    let xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
    xmlHttp.onreadystatechange = () => {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            majorList.innerHTML = xmlHttp.responseText
        }
    }
    xmlHttp.open("POST", "/servlet/AjaxGetMajors", true)
    xmlHttp.send()
}


// This function listens the back-to-top button
function listenBackToTop(backToTopBtn) {
    // Initialize the position of the back-to-top button
    backToTopBtn.style.bottom = -backToTopBtn.offsetHeight + "px"
    // When the page is being scrolling
    window.onscroll = () => {
        // When the vertical offset is greater than 500, display the button; else hide it
        if (document.body.onscroll > 500 || document.documentElement.scrollTop > 500) {
            backToTopBtn.style.bottom = "3%"
        } else {
            backToTopBtn.style.bottom = -backToTopBtn.offsetHeight + "px"
        }
    }
    // When the back-to-top button is clicked
    backToTopBtn.onclick = () => {
        window.scrollTo({top: 0, behavior: "smooth"})
    }
}


// This function is to make sure that the footer is on the bottom of the page
function setFooter(footer) {
    if (window.innerHeight > document.body.scrollHeight) {
        footer.className = "dl4csr-footer bottom"
    }
}


// This function is to automatically resize the textarea
function autoTextarea(area, footer) {
    resetStyle(area)
    area.style.height = area.scrollHeight + "px"
    area.oninput = area.oncut = () => {
        if (window.innerHeight > document.body.scrollHeight) {
            footer.className = "dl4csr-footer bottom"
        } else {
            footer.className = "dl4csr-footer"
        }
        area.style.height = "auto"
        area.style.height = area.scrollHeight + "px"
    }
}
