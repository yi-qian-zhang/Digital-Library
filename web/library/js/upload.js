let timeoutContent

window.onload = () => {

    listenBackToTop(document.getElementById("back_to_top"))
    listenSearch(document.getElementById("search_bar"), document.getElementById("search_btn"), document.getElementById("search_close"))
    listenLogout(document.getElementById("logout"))
    listenSnackbarCloseBtn(document.getElementById("snackbar"), document.getElementById("snackbar_close"))
    initMajorList(document.getElementById("selectable_majors"))
    setFooter(document.getElementById("footer"))

    let title = document.getElementById("title")
    title.onclick = () => resetStyle(title)

    let author = document.getElementById("author")
    author.onclick = () => resetStyle(author)

    let keyword = document.getElementById("keyword")
    keyword.onclick = () => resetStyle(keyword)

    let major = document.getElementById("major")
    major.onclick = () => resetStyle(major)

    let outline = document.getElementById("outline")
    outline.onclick = () => autoTextarea(outline, document.getElementById("footer"))
    autoTextarea(outline, document.getElementById("footer"))

    let fileBtn = document.getElementById("file_btn")
    fileBtn.onclick = () => fileBtn.className = "dl4csr-button"

}


// This function uses AJAX to upload new paper
function checkUpload() {
    let form = document.getElementById("form")
    let title = document.getElementById("title")
    let author = document.getElementById("author")
    let keyword = document.getElementById("keyword")
    let major = document.getElementById("major")
    let outline = document.getElementById("outline")
    let file = document.getElementById("file")
    let fileBtn = document.getElementById("file_btn")
    // Make sure the title, author, keyword, major, outline, and file are non-empty
    if (checkTitle(title) && checkAuthor(author) && checkKeyword(keyword) && checkMajor(major) && checkOutline(outline) && checkFile(file, fileBtn)) {
        let xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
        xmlHttp.open("POST", "/servlet/AjaxUploadPaperServlet", false) // Synchronous
        // Use FormData() to collect data and send to the server
        xmlHttp.send(new FormData(form))
        // Wait until the last stage
        while (xmlHttp.readyState !== 4) {
        }
        // Get to the last stage and received HTTP code 200
        if (xmlHttp.status === 200) {
            // Take different actions according to the response text
            switch (xmlHttp.responseText) {
                // "succeed" means the paper has been uploaded and added into database successfully
                case "succeed": {
                    // so show the information and reset the form
                    timeoutContent = snackbarAlert("Paper uploaded successfully", timeoutContent)
                    form.reset()
                    break
                }
                // "reviewerError" means the reviewers are not enough for new papers
                case "reviewerError": {
                    // so alert
                    timeoutContent = snackbarAlert("Cannot find available reviewer, please upload later", timeoutContent)
                    break
                }
                // "majorError" means the major is not available
                case "majorError": {
                    // so alert
                    timeoutContent = snackbarAlert("Major " + major.value + " is not available", timeoutContent)
                    major.style.color = "red"
                    major.style.borderColor = "red"
                    break
                }
                // "uploadError" means there are errors on uploading file
                case "uploadError": {
                    timeoutContent = snackbarAlert("An error occurred when uploading file", timeoutContent)
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


// This function returns true iff the title is non-empty, else alert
function checkTitle(title) {
    if (title.value === "") {
        timeoutContent = snackbarAlert("Please enter the title", timeoutContent)
        title.style.color = "red"
        title.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true iff the author is non-empty, else alert
function checkAuthor(author) {
    if (author.value === "") {
        timeoutContent = snackbarAlert("Please enter the author", timeoutContent)
        author.style.color = "red"
        author.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true iff the keyword is non-empty, else alert
function checkKeyword(keyword) {
    if (keyword.value === "") {
        timeoutContent = snackbarAlert("Please enter the keyword", timeoutContent)
        keyword.style.color = "red"
        keyword.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true iff the major is non-empty, else alert
function checkMajor(major) {
    if (major.value === "") {
        timeoutContent = snackbarAlert("Please enter the major", timeoutContent)
        major.style.color = "red"
        major.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true iff the outline is non-empty, else alert
function checkOutline(outline) {
    if (outline.value === "") {
        timeoutContent = snackbarAlert("Please enter the outline", timeoutContent)
        outline.style.color = "red"
        outline.style.borderColor = "red"
        return false
    }
    return true
}


// This function returns true iff the file has been selected, else alert
function checkFile(file, fileBtn) {
    if (file.value === "") {
        timeoutContent = snackbarAlert("Please select a pdf file", timeoutContent)
        fileBtn.className = "dl4csr-button err"
        return false
    }
    return true
}
