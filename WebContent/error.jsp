<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="common/common.jsp" %>
    <link rel="stylesheet" href='<%= request.getContextPath() + "/css/error.css" %>' />

    <meta charset="UTF-8" />
    <title>Error</title>
</head>
<body>
    <p>Error occurred while running this application:</p>
    <div id="err-msg-frame">
        <pre>${errMsg}</pre>
    </div>
</body>
</html>
