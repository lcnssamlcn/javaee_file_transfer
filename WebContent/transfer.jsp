<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("Cache-Control", "no-cache");
%>
<%
    List<String> sharedFolderRegFilesName = (List<String>) request.getAttribute("sharedFolderRegFilesName");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="common/common.jsp" %>
    <link rel="stylesheet" href='<%= request.getContextPath() + "/css/transfer.css" %>' />
    <script type="text/javascript">
        var SHARED_FOLDER_REG_FILES_NAME = [
        <%
            for (int i = 0; i < sharedFolderRegFilesName.size(); i++) {
                String sfrf = sharedFolderRegFilesName.get(i);
                out.print("\"" + sfrf + "\"");
                if (i != sharedFolderRegFilesName.size() - 1) {
                    out.print(",");
                }
            }
        %>
        ];
        var sharedFolderRegFilesName = SHARED_FOLDER_REG_FILES_NAME.slice();
        var isSharedFolderRegFilesListSorted = false;
        var IMG_FILE_ICON_PATH = '<%= request.getContextPath() + "/img/file_icon.png" %>';
        var IMG_DOWNLOAD_ICON_PATH = '<%= request.getContextPath() + "/img/download_icon.png" %>';
        var ACTION_UPLOAD = '<%= request.getContextPath() + "/upload" %>';
        var ACTION_DOWNLOAD = '<%= request.getContextPath() + "/download" %>';
    </script>
    <script type="text/javascript" src='<%= request.getContextPath() + "/js/common/StringUtil.js" %>'></script>

    <meta charset="UTF-8" />
    <title>File Transfer</title>
</head>
<body>
    <form action="/" method="post" enctype="multipart/form-data">
        <div id="sys-msg-frame">
            <ul>
            <c:forEach var="msg" items="${sysMsgs}">
                <li>${msg}</li>
            </c:forEach>
            </ul>
        </div>
        <div id="file-upload-bar">
            <p>(max file size: 20GB)</p>
            <input id="btn-browse" name="btn-browse" type="file" class="btn btn-success" value="Browse" />
            <input id="btn-upload" name="btn-upload" type="button" class="btn btn-success" value="Upload" />
        </div>
        <div id="shared-folder-path-display-frame">
            <p id="shared-folder-path-display">Shared Folder: ${SHARED_FOLDER_PATH}</p>
        </div>
        <div id="shared-folder-reg-files-list-frame">
            <table id="shared-folder-reg-files-list" class="table table-striped">
                <thead>
                    <tr>
                        <th class="file-icon-header-cell"></th>
                        <th id="col-name">Name</th>
                        <th class="download-icon-header-cell"></th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div>
            <input id="uploaded-file-name" name="uploaded-file-name" type="hidden" value="" />
            <input id="download-file-name" name="download-file-name" type="hidden" value="" />
        </div>
    </form>
    <script type="text/javascript" src='<%= request.getContextPath() + "/js/transfer.js" %>'></script>
</body>
</html>
