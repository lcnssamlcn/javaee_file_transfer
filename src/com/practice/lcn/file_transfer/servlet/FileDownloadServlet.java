package com.practice.lcn.file_transfer.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletOutputStream;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.io.FileUtils;

@WebServlet(name="FileDownloadServlet", urlPatterns="/download")
public class FileDownloadServlet extends HttpServlet {
    private Logger log = LogManager.getLogger();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String SHARED_FOLDER_PATH = (String) session.getAttribute("SHARED_FOLDER_PATH");

        List<String> sysMsgs = new ArrayList<>();

        String downloadFileName = request.getParameter("download-file-name");
        log.info(String.format("downloadFileName: \"%s\"", downloadFileName));

        try {
            response.setContentType("*/*");
            response.setHeader("Content-disposition", String.format("attachment; filename=%s", downloadFileName));

            byte[] downloadFileBytes = FileUtils.readFileToByteArray(new File(SHARED_FOLDER_PATH, downloadFileName));
            ServletOutputStream sos = response.getOutputStream();
            sos.write(downloadFileBytes);
        }
        finally {
            request.setAttribute("sysMsgs", sysMsgs);
        }
    }
}
