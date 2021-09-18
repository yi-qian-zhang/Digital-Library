<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="#2196F3" name="theme-color">
    <title>Digital Library for Computer Science Research</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">
    <script rel="script" type="text/javascript" src="js/login.js"></script>
    <script rel="script" type="text/javascript" src="js/public/onload.js"></script>
    <script rel="script" type="text/javascript" src="js/public/function.js"></script>
    <script rel="script" type="text/javascript" src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.js"></script>
</head>


<body>

<main class="dl4csr-main">
    <div class="dl4csr-main--content">
        <div class="dl4csr-main--content--left">
            <h1>Digital Library for Computer Science Research</h1>
        </div>
        <div class="dl4csr-main--content--right">
            <h1 id="title">Sign in</h1>
            <div class="dl4csr-main--selector">
                <button id="university_selector" style="border-bottom-color: #1E88E5;">As University</button>
                <button id="reviewer_selector">As Reviewer</button>
                <button id="reader_selector">As Reader</button>
            </div>
            <form method="post" onsubmit="return false">
                <input type="hidden" id="action" value="sign_in">
                <input type="hidden" id="identity" value="university">
                <p id="input_email" style="display: flex">
                    <label for="email"></label>
                    <input type="email" id="email" placeholder="Enter the email">
                </p>
                <p id="input_name" style="display: none;">
                    <label for="name"></label>
                    <input type="text" id="name" placeholder="Enter the user name">
                </p>
                <p id="input_password" style="display: flex">
                    <label for="password"></label>
                    <input type="password" id="password" placeholder="Enter the password">
                    <span class="material-icons vis" id="show_password">visibility_off</span>
                </p>
                <p id="input_major" style="display: none;">
                    <label for="major"></label>
                    <input type="text" list="selectable_majors" id="major" placeholder="Select a major">
                    <datalist id="selectable_majors" style="height: 200px">
                    </datalist>
                </p>
                <p>
                    <button type="submit" id="submit" onclick="handleSubmit()">SIGN IN</button>
                </p>
            </form>
            <p style="text-align: center; padding-top: 12px;">
                <a id="switch_sign">Don't have account? Click to sign up.</a>
            </p>
        </div>
    </div>
    <div class="dl4csr-main--copyright">
        <p>Copyright &copy; 2021 CNSCC.212 Group 5. All rights reserved.</p>
    </div>
</main>

<div class="dl4csr-snackbar" id="snackbar">
    <p id="snackbar_info">Unset</p>
    <div style="text-align: right"><a id="snackbar_close">Okay</a></div>
</div>

</body>

</html>