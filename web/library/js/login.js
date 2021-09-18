let timeoutContent

window.onload = () => {

    // Controls the hide of the snackbar
    listenSnackbarCloseBtn(document.getElementById("snackbar"), document.getElementById("snackbar_close"))

    // Initialize the list of available majors using AJAX
    initMajorList(document.getElementById("selectable_majors"))

    // Controls the selector buttons
    let uniSelector = document.getElementById("university_selector")
    let revSelector = document.getElementById("reviewer_selector")
    let reaSelector = document.getElementById("reader_selector")
    let identity = document.getElementById("identity")
    let inputMajor = document.getElementById("input_major")
    uniSelector.onclick = () => {
        identity.value = "university"
        uniSelector.style.borderBottomColor = "#1E88E5"
        revSelector.style.borderBottomColor = reaSelector.style.borderBottomColor = "white"
        inputMajor.style.display = "none"
    }
    revSelector.onclick = () => {
        identity.value = "reviewer"
        revSelector.style.borderBottomColor = "#1E88E5"
        uniSelector.style.borderBottomColor = reaSelector.style.borderBottomColor = "white"
        if (action.value === "sign_up") {
            inputMajor.style.display = "flex"
        }
    }
    reaSelector.onclick = () => {
        identity.value = "reader"
        reaSelector.style.borderBottomColor = "#1E88E5"
        uniSelector.style.borderBottomColor = revSelector.style.borderBottomColor = "white"
        inputMajor.style.display = "none"
    }

    // Controls the actions of sign in or sign up
    let action = document.getElementById("action")
    let switchSign = document.getElementById("switch_sign")
    let submit = document.getElementById("submit")
    let title = document.getElementById("title")
    let inputName = document.getElementById("input_name")
    switchSign.onclick = () => {
        if (action.value === "sign_in") {
            switchSign.innerHTML = "Already have an account? Click to sign in."
            action.value = "sign_up"
            submit.innerHTML = "SIGN UP"
            title.innerHTML = "Sign up"
            inputName.style.display = "flex"
            if (identity.value === "reviewer") {
                inputMajor.style.display = "flex"
            } else {
                inputMajor.style.display = "none"
            }
        } else {
            switchSign.innerHTML = "Don't have account? Click to sign up."
            action.value = "sign_in"
            submit.innerHTML = "SIGN IN"
            title.innerHTML = "Sign in"
            inputName.style.display = inputMajor.style.display = "none"
        }
    }

    // Controls the visibility of entered password
    let showPassword = document.getElementById("show_password")
    let password = document.getElementById("password")
    showPassword.onclick = () => {
        if (password.getAttribute("type") === "password") {
            password.setAttribute("type", "text")
            showPassword.innerHTML = "visibility"
        } else {
            password.setAttribute("type", "password")
            showPassword.innerHTML = "visibility_off"
        }
    }

    // Resets the border color when the corresponding input item is clicked
    let email = document.getElementById("email")
    let major = document.getElementById("major")
    let name = document.getElementById("name")
    email.onclick = () => resetStyle(email)
    password.onclick = () => resetStyle(password)
    name.onclick = () => resetStyle(name)
    major.onclick = () => resetStyle(major)

}


// Sign in or sign up by AJAX, and use AJAX to check or insert user information
function handleSubmit() {
    let action = document.getElementById("action")
    let identity = document.getElementById("identity")
    let name = document.getElementById("name")
    let password = document.getElementById("password")
    let email = document.getElementById("email")
    let major = document.getElementById("major")
    // When the action is sign in
    if (action.value === "sign_in") {
        // Chek email and password to be non-empty
        if (checkEmail(email) && checkPassword(password)) {
            // Try to sign in
            signIn(identity, email, password)
        }
    } else { // When the acton is sign up
        // Check that the email, name, password (and major if sign up as reviewer) are non-empty
        if (checkEmail(email) && checkName(name) && checkPassword(password) && checkMajor(identity, major)) {
            // Try to sign up
            signUp(identity, email, name, password, major)
        }
    }
}


// This function returns true iff the email is non-empty, else alert
function checkEmail(email) {
    if (email.value === "") {
        timeoutContent = snackbarAlert("Please enter the email", timeoutContent)
        email.style.color = "red"
        email.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true iff the name is non-empty, else alert
function checkName(name) {
    if (name.value === "") {
        timeoutContent = snackbarAlert("Please enter the user name", timeoutContent)
        name.style.color = "red"
        name.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true when the identity is not reviewer, or the identity and the major is non-empty, else alert
function checkMajor(identity, major) {
    if (identity.value === "reviewer" && major.value === "") {
        timeoutContent = snackbarAlert("Please select a major", timeoutContent)
        major.style.color = "red"
        major.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true when the password is non-empty, else alert
function checkPassword(password) {
    if (password.value === "") {
        timeoutContent = snackbarAlert("Please enter the password", timeoutContent)
        password.style.color = "red"
        password.style.borderColor = "red"
        return false
    }
    return true
}


// THis function is to sign in using AJAX
function signIn(identity, email, password) {
    let xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
    // IMPORTANT: Only when the withCredentials argument is true, the server can operates the session
    xmlHttp.withCredentials = true
    xmlHttp.open("POST", "/servlet/AjaxSignIn", true) // Asynchronous
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
    // Send necessary login information, and use MD5 to encrypt password
    xmlHttp.send("identity=" + identity.value + "&email=" + email.value + "&password=" + md5(password.value))
    xmlHttp.onreadystatechange = () => {
        // At the last stage
        if (xmlHttp.readyState === 4) {
            // WHen the server returns HTTP code of 200
            if (xmlHttp.status === 200) {
                // According to the response body to do actions
                switch (xmlHttp.responseText) {
                    // "succeed" means that the user has login and session has been created
                    case "succeed": {
                        // so redirect to the index page
                        window.location.href = "/servlet/findPaperByPageServlet"
                        break
                    }
                    // "passwordError" means that the password or user not exsit
                    case "passwordError": {
                        // so alert
                        snackbarAlert("Wrong password or user not exist", timeoutContent)
                        password.style.color = "red"
                        password.style.borderColor = "red"
                        break
                    }
                    // The other situations are unexpected error
                    default: {
                        timeoutContent = snackbarAlert("An unexpected error occurred", timeoutContent)
                        break
                    }
                }
            } else { // The response is not 200, it means that there are some problems in server probably
                timeoutContent = snackbarAlert("Error on connecting to server", timeoutContent)
            }
        }
    }
}


// This function is to sign up using AJAX
function signUp(identity, email, name, password, major) {
    let xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
    xmlHttp.open("POST", "/servlet/AjaxSignUp", true) // Asynchronous
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
    // Send necessary sign up information, and use MD5 to encrypt password
    xmlHttp.send("identity=" + identity.value + "&email=" + email.value + "&name=" + name.value + "&password=" + md5(password.value) + "&major=" + major.value)
    xmlHttp.onreadystatechange = () => {
        // At the last stage
        if (xmlHttp.readyState === 4) {
            // Received HTTP code 200
            if (xmlHttp.status === 200) {
                // Take actions according to the response text
                switch (xmlHttp.responseText) {
                    // "succeed" means user information has been registered successfully
                    case "succeed": {
                        // so show success information and switch to sign in
                        snackbarAlert("User has been created successfully", timeoutContent)
                        document.getElementById("switch_sign").click()
                        break
                    }
                    // "emailError" means that the email is duplicated in the server
                    case "emailError": {
                        // so alert
                        timeoutContent = snackbarAlert("Email " + email.value + " has been registered", timeoutContent)
                        email.style.color = "red"
                        email.style.borderColor = "red"
                        break
                    }
                    // ""majorError" means that the major is not contained in the database
                    case "majorError": {
                        // so alert
                        timeoutContent = snackbarAlert("The major \"" + major.value + "\" is invalid", timeoutContent)
                        major.style.color = "red"
                        major.style.borderColor = "red"
                        break
                    }
                    // The other cases are unexpected errors
                    default: {
                        timeoutContent = snackbarAlert("An unexpected error occurred", timeoutContent)
                        break
                    }
                }
            } else { // The response is not 200, it means that there are some problems in server probably
                timeoutContent = snackbarAlert("Error on connecting to server", timeoutContent)
            }
        }
    }
}
