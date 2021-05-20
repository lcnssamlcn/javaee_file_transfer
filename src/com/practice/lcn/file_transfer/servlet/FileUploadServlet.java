package com.practice.lcn.file_transfer.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name="FileUploadServlet", urlPatterns="/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private Logger log = LogManager.getLogger();

    private static final String MSG_UPLOAD_SUCCESS = "Upload Successful.";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String SHARED_FOLDER_PATH = (String) session.getAttribute("SHARED_FOLDER_PATH");

        List<String> sysMsgs = new ArrayList<>();

        String uploadedFileName = request.getParameter("uploaded-file-name");
        log.info(String.format("uploadedFileName: \"%s\"", uploadedFileName));

        response.setContentType("text/html");

        Part attachment = request.getPart("btn-browse");
        File attachmentFile = new File(SHARED_FOLDER_PATH, uploadedFileName);

        Files.copy(attachment.getInputStream(), attachmentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        sysMsgs.add(FileUploadServlet.MSG_UPLOAD_SUCCESS);
        request.setAttribute("sysMsgs", sysMsgs);
        request.getRequestDispatcher("transfer").forward(request, response);
    }
}
