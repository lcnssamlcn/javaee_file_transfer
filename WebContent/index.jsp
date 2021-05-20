<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="common/common.jsp" %>
    <link rel="stylesheet" href='<%= request.getContextPath() + "/css/index.css" %>' />

    <meta charset="UTF-8" />
    <title>Login</title>
</head>
<body>
    <div id="title-frame">
        <p id="title">Login</p>
    </div>
    <div id="content">
        <div id="err-msg-frame">
            <p>${errMsg}</p>
        </div>
        <div id="login-frame">
            <form action="login" method="post">
                <table id="login-fields">
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="username" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" /></td>
                    </tr>
                </table>
                <br />
                <input type="submit" value="Login" />
            </form>
        </div>
    </div>
</body>
</html>
