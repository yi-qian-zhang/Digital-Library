<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="#2196F3" name="theme-color">
    <title>Digital Library for Computer Science Research</title>
    <link rel="stylesheet" type="text/css" href="css/public.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
    <script rel="script" type="text/javascript" src="js/index.js"></script>
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
            <li><a href="${pageContext.request.contextPath}/servlet/findReviewServlet">Review</a></li>
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
        <h1 class="dl4csr-main--paper title">
            <c:if test="${condition == null || condition == ''}">All Published Papers</c:if>
            <c:if test="${condition != null && condition != ''}">
                <c:if test="${pb.getTotalCount() == 0}">No Result of "${condition}" Found</c:if>
                <c:if test="${pb.getTotalCount() != 0}">Search Result of "${condition}"</c:if>
            </c:if>
        </h1>
        <c:forEach items="${pb.list}" var="paper" varStatus="s">
            <div class="dl4csr-main--paper item" id="item_${s.count}">
                <h2>${paper.title}</h2>
                <div class="dl4csr-main--paper details">
                    <p class="dl4csr-main--paper key">Author</p>
                    <p class="dl4csr-main--paper value">${paper.author}</p>
                    <p class="dl4csr-main--paper key">University</p>
                    <p class="dl4csr-main--paper value">${paper.university}</p>
                    <p class="dl4csr-main--paper key">Major</p>
                    <p class="dl4csr-main--paper value">${paper.major}</p>
                    <p class="dl4csr-main--paper key">Keywords</p>
                    <p class="dl4csr-main--paper value">${paper.keyword}</p>
                    <p class="dl4csr-main--paper key">Outline</p>
                    <p class="dl4csr-main--paper value">${paper.outline}</p>
                    <p class="dl4csr-main--paper key">Publish date</p>
                    <p class="dl4csr-main--paper value">${paper.publish_date}</p>
                </div>
                <p style="text-align: right; padding-bottom: 8px">
                    <a class="dl4csr-main--paper download" href="${pageContext.request.contextPath}/servlet/downloadServlet?filename=${paper.id}.pdf">DOWNLOAD</a>
                </p>
            </div>
        </c:forEach>
    </div>
    <c:if test="${pb.getTotalCount() != 0}">
        <nav class="dl4csr-nav">
            <div class="dl4csr-nav--pages">
                <a class="material-icons btn"
                    <c:if test="${pb.currentPage != 1}">
                        href="${pageContext.request.contextPath}/servlet/findPaperByPageServlet?currentPage=${pb.currentPage - 1}&rows=5&w=${condition}"
                    </c:if>>chevron_left</a>
                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                    <c:if test="${pb.currentPage == i}">
                        <a class="dl4csr-nav--num active" href="${pageContext.request.contextPath}/servlet/findPaperByPageServlet?currentPage=${i}&rows=5&w=${condition}">${i}</a>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <a class="dl4csr-nav--num" href="${pageContext.request.contextPath}/servlet/findPaperByPageServlet?currentPage=${i}&rows=5&w=${condition}">${i}</a>
                    </c:if>
                </c:forEach>
                <a class="material-icons btn"
                    <c:if test="${pb.currentPage != pb.totalPage}">
                        href="${pageContext.request.contextPath}/servlet/findPaperByPageServlet?currentPage=${pb.currentPage + 1}&rows=5&w=${condition}"
                    </c:if>>chevron_right</a>
            </div>
            <span>Total ${pb.totalCount} papers found in the library</span>
        </nav>
    </c:if>
</main>

<footer class="dl4csr-footer" id="footer">
    <p class="dl4csr-footer--title">Digital Library for Computer Science Research</p>
    <p class="dl4csr-footer--content">Copyright &copy; 2021 CNSCC.212 Group 5. All rights reserved.</p>
</footer>

</body>

</html>