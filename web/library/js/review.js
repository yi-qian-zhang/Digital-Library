let timeoutContent

window.onload = () => {

    listenBackToTop(document.getElementById("back_to_top"))
    listenSearch(document.getElementById("search_bar"), document.getElementById("search_btn"), document.getElementById("search_close"))
    listenLogout(document.getElementById("logout"))
    listenSnackbarCloseBtn(document.getElementById("snackbar"), document.getElementById("snackbar_close"))
    setFooter(document.getElementById("footer"))

    for (let i = 1; i <= document.getElementById("count_item").value; i++) {
        let comment = document.getElementById("comment_" + i)
        comment.onclick = () => autoTextarea(comment, document.getElementById("footer"))
        autoTextarea(comment, document.getElementById("footer"))
    }

}

function checkComment(i) {
    let comment = document.getElementById("comment_" + i)
    if (comment.value === "") {
        snackbarAlert("Please enter the comment", timeoutContent)
        comment.style.borderColor = "red"
        return false
    }
    return true
}
