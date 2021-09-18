window.onload = () => {

    setFooter(document.getElementById("footer"))
    listenBackToTop(document.getElementById("back_to_top"))
    listenSearch(document.getElementById("search_bar"), document.getElementById("search_btn"), document.getElementById("search_close"))
    listenLogout(document.getElementById("logout"))

}


// This function is to confirm the deletion
function confirmDelete(id) {
    if (confirm("Are you sure to delete the paper?")) {
        location.href = "/servlet/deletePaperServlet?id=" + id;
    }
}
