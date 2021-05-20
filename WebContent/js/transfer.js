var MSG_NO_FILE_CHOSEN = "No file is chosen. File upload operation aborts.";
var MSG_IS_OVERWRITE_FILE = "\"{0}\" already exists in shared folder. Do you want to overwrite it?";

function clearSharedFolderRegFilesList() {
    $("#shared-folder-reg-files-list > tbody").remove();
    $("<tbody></tbody>").appendTo("#shared-folder-reg-files-list");
}

function showSharedFolderRegFiles() {
    for (var i = 0; i < sharedFolderRegFilesName.length; i++) {
        $(
            "<tr>" +
            "<td><img class='file-icon' src='" + IMG_FILE_ICON_PATH + "' /></td>" +
            "<td>" + sharedFolderRegFilesName[i] + "</td>" +
            "<td><img class='download-icon' src='" + IMG_DOWNLOAD_ICON_PATH + "' /></td>" +
            "</tr>"
        ).appendTo("#shared-folder-reg-files-list > tbody");
    }

    $("img.download-icon").click(function () {
        var downloadFileName = $(this).parent().prev().text();
        $(":input[id='download-file-name']").val(downloadFileName);

        document.forms[0].action = ACTION_DOWNLOAD;
        document.forms[0].enctype = "application/x-www-form-urlencoded";
        document.forms[0].target = "_blank"; 
        document.forms[0].submit();
    });
}

$(document).ready(function () {
    $("#shared-folder-reg-files-list #col-name").click(function () {
        if (!isSharedFolderRegFilesListSorted) {
            // sort the file list by name (ignore case)
            sharedFolderRegFilesName.sort(function(a, b) {
                var aUpper = a.toUpperCase();
                var bUpper = b.toUpperCase();
                if (aUpper < bUpper) {
                    return -1;
                }
                if (aUpper > bUpper) {
                    return 1;
                }

                return 0;
            });
            isSharedFolderRegFilesListSorted = true;
        }
        else {
            sharedFolderRegFilesName.reverse();
        }
        clearSharedFolderRegFilesList();
        showSharedFolderRegFiles();
    });
    $("#btn-upload").click(function () {
        var btnBrowse = document.getElementById("btn-browse");
        var uploadedFilePath = btnBrowse.value;
        if (uploadedFilePath === "") {
            alert(MSG_NO_FILE_CHOSEN);
            return;
        }

        var uploadedFilePathParts = uploadedFilePath.split(/[\\\/]/);
        var uploadedFileName = uploadedFilePathParts[uploadedFilePathParts.length - 1].trim();
        var sharedFolderRegFilesNameIgnoreCase = SHARED_FOLDER_REG_FILES_NAME.slice();
        for (var i = 0; i < sharedFolderRegFilesNameIgnoreCase.length; i++) {
            sharedFolderRegFilesNameIgnoreCase[i] = sharedFolderRegFilesNameIgnoreCase[i].toLowerCase();
        }
        if (sharedFolderRegFilesNameIgnoreCase.includes(uploadedFileName.toLowerCase())) {
            if (!confirm(StringUtil.format(MSG_IS_OVERWRITE_FILE, uploadedFileName))) {
                btnBrowse.value = "";
                return;
            }
        }
        $(":input[id='uploaded-file-name']").val(uploadedFileName);

        document.forms[0].action = ACTION_UPLOAD;
        document.forms[0].enctype = "multipart/form-data";
        document.forms[0].target = "_self"; 
        document.forms[0].submit();
    });

    showSharedFolderRegFiles();
});
