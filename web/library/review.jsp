<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="#2196F3" name="theme-color">
    <title>Digital Library for Computer Science Research</title>
    <link rel="stylesheet" type="text/css" href="css/public.css">
    <link rel="stylesheet" type="text/css" href="css/review.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
    <script rel="script" type="text/javascript" src="js/review.js"></script>
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
            <li><a href="${pageContext.request.contextPath}/servlet/findMyPapersServlet">Manage</a></li>
            <li><a href="${pageContext.request.contextPath}/library/upload.jsp">Upload</a></li>
        </c:if>
        <c:if test="${identity == 'reviewer'}">
            <li><a>Review</a></li>
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
        <h1 class="dl4csr-main--paper title">Papers to Be Reviewed</h1>
    </div>
    <input type="hidden" id="count_item" value="${reviewPapers.size()}">
    <c:forEach items="${reviewPapers}" var="paper" varStatus="s">
        <div class="dl4csr-main--paper item" id="item_${s.count}">
            <h2>${paper.title}</h2>
            <div class="dl4csr-main--paper details">
                <p class="dl4csr-main--paper key">Major</p>
                <p class="dl4csr-main--paper value">${paper.major}</p>
                <p class="dl4csr-main--paper key">Keywords</p>
                <p class="dl4csr-main--paper value">${paper.keyword}</p>
                <p class="dl4csr-main--paper key">Outline</p>
                <p class="dl4csr-main--paper value">${paper.outline}</p>
                <p class="dl4csr-main--paper key">Submit date</p>
                <p class="dl4csr-main--paper value">${paper.submit_date}</p>
            </div>
            <div class="dl4csr-main--comment">
                <form id="form_${s.count}" action="${pageContext.request.contextPath}/servlet/peerReviewServlet" method="get" onsubmit="return checkComment(${s.count})">
                    <p class="dl4csr-main--comment input">
                        <label for="comment_${s.count}"></label>
                        <textarea name="comment" id="comment_${s.count}" placeholder="Add comments"></textarea>
                        <input type="hidden" name="id" id="id" value="${paper.id}">
                    </p>
                    <p class="dl4csr-main--comment button">
                        <a class="dl4csr-main--comment btn" href="${pageContext.request.contextPath}/servlet/downloadServlet?filename=${paper.id}.pdf">DOWNLOAD</a>
                        <button class="dl4csr-main--comment btn" type="submit" name="accept" value="true">ACCEPT</button>
                        <button class="dl4csr-main--comment btn neg" type="submit" name="accept" value="false">REJECT</button>
                    </p>
                </form>
            </div>
        </div>
    </c:forEach>
</main>

<footer class="dl4csr-footer" id="footer">
    <p class="dl4csr-footer--title">Digital Library for Computer Science Research</p>
    <p class="dl4csr-footer--content">Copyright &copy; 2021 CNSCC.212 Group 5. All rights reserved.</p>
</footer>

<div class="dl4csr-snackbar" id="snackbar">
    <p id="snackbar_info">Unset</p>
    <div style="text-align: right"><a id="snackbar_close">Okay</a></div>
</div>

</body>

</html>
