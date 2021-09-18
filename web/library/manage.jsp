<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="#2196F3" name="theme-color">
    <title>Digital Library for Computer Science Research</title>
    <link rel="stylesheet" type="text/css" href="css/public.css">
    <link rel="stylesheet" type="text/css" href="css/manage.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
    <script rel="script" type="text/javascript" src="js/manage.js"></script>
    <script rel="script" type="text/javascript" src="js/public/onload.js"></script>
    <script rel="script" type="text/javascript" src="js/public/function.js"></script>
</head>


<body>

<button class="back-to-top" id="back_to_top"><span class="material-icons">arrow_upward</span></button>

<header class="dl4csr-header" id="header">
    <ul>
        <li style="float: left;"><a href="${pageContext.request.contextPath}/servlet/findPaperByPageServlet">DL4CSR</a></li>
        <li><a class="material-icons md-24" id="search_btn">search</a></li>
        <li><a id="logout">Logout</a></li>
        <c:if test="${identity == 'university'}">
            <li><a>Manage</a></li>
            <li><a href="${pageContext.request.contextPath}/library/upload.jsp">Upload</a></li>
        </c:if>
    </ul>
</header>

<div id="search_bar" class="dl4csr-search">
    <form action="${pageContext.request.contextPath}/servlet/findPaperByPageServlet" method="get" onsubmit="return checkSearch()">
        <label for="search_input" class="title">DL4CSR</label>
        <button type="submit" class="material-icons md-24">search</button>
        <input type="text" id="search_input" name="w" placeholder="Search anything in the library">
        <button type="button" id="search_close" class="material-icons md-24">close</button>
    </form>
</div>

<main class="dl4csr-main" id="main">
    <div class="dl4csr-main--paper">
        <c:if test="${myPapers.size() == 0}">
            <h1 class="dl4csr-main--paper title">No Submitted Paper</h1>
            <a href="${pageContext.request.contextPath}/library/upload.jsp">Upload Now!</a>
        </c:if>
        <c:if test="${myPapers.size() != 0}">
            <h1 class="dl4csr-main--paper title">Manage Papers</h1>
            <c:forEach items="${myPapers}" var="paper" varStatus="s">
                <div class="dl4csr-main--paper item">
                    <h2>${paper.title}</h2>
                    <div class="dl4csr-main--paper details">
                        <p class="dl4csr-main--paper key">Author</p>
                        <p class="dl4csr-main--paper value">${paper.author}</p>
                        <p class="dl4csr-main--paper key">Major</p>
                        <p class="dl4csr-main--paper value">${paper.major}</p>
                        <p class="dl4csr-main--paper key">Keywords</p>
                        <p class="dl4csr-main--paper value">${paper.keyword}</p>
                        <p class="dl4csr-main--paper key">Status</p>
                        <c:if test="${paper.is_published == 0}">
                            <c:if test="${paper.acceptance_1 == 0 || paper.acceptance_2 == 0 || paper.acceptance_3 == 0}">
                                <p class="dl4csr-main--paper value">To be reviewed</p>
                            </c:if>
                            <c:if test="${paper.acceptance_1 != 0 && paper.acceptance_2 != 0 && paper.acceptance_3 != 0}">
                                <p class="dl4csr-main--paper value">Rejected</p>
                                <p class="dl4csr-main--paper key">Comments</p>
                                <p class="dl4csr-main--paper value">(1)${paper.comment_1}<br>(2)${paper.comment_2}<br>(3)${paper.comment_3}</p>
                            </c:if>
                        </c:if>
                        <c:if test="${paper.is_published == 1}">
                            <p class="dl4csr-main--paper value">Published</p>
                            <p class="dl4csr-main--paper key">Comments</p>
                            <p class="dl4csr-main--paper value">${paper.comment_1}<br>${paper.comment_2}<br>${paper.comment_3}</p>
                            <p class="dl4csr-main--paper key">Publish date</p>
                            <p class="dl4csr-main--paper value">${paper.publish_date}</p>
                        </c:if>
                        <p class="dl4csr-main--paper key">Submit date</p>
                        <p class="dl4csr-main--paper value">${paper.submit_date}</p>
                    </div>
                    <p style="text-align: right">
                        <a class="dl4csr-main--paper download" href="${pageContext.request.contextPath}/servlet/downloadServlet?filename=${paper.id}.pdf">DOWNLOAD</a>
                        <a class="dl4csr-main--paper delete" href="javascript:confirmDelete(${paper.id});">DELETE</a>
                    </p>
                </div>
            </c:forEach>
        </c:if>
    </div>
</main>

<footer class="dl4csr-footer" id="footer">
    <p class="dl4csr-footer--title">Digital Library for Computer Science Research</p>
    <p class="dl4csr-footer--content">Copyright &copy; 2021 CNSCC.212 Group 5. All rights reserved.</p>
</footer>

</body>

</html>