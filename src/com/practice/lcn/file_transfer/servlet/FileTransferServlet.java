package com.practice.lcn.file_transfer.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name="FileTransferServlet", urlPatterns="/transfer")
public class FileTransferServlet extends HttpServlet {
    private Logger log = LogManager.getLogger();

    private static final String MSG_MISSING_ENV_SHARED_FOLDER_PATH = "\"ENV.SHARED_FOLDER_PATH\" attribute in file_transfer.properties is not found.";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Properties prop = (Properties) session.getAttribute("FILE_TRANSFER_PROPERTIES");

        List<String> sysMsgs = (List<String>) request.getAttribute("sysMsgs");
        if (sysMsgs == null) {
            sysMsgs = new ArrayList<>();
        }

        response.setContentType("text/html");

        String SHARED_FOLDER_PATH = prop.getProperty("ENV.SHARED_FOLDER_PATH");
        if (SHARED_FOLDER_PATH == null) {
            request.setAttribute("errMsg", FileTransferServlet.MSG_MISSING_ENV_SHARED_FOLDER_PATH);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        File sharedFolder = new File(SHARED_FOLDER_PATH);
        File[] sharedFolderRegFiles = sharedFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isFile();
            }
        });
        List<String> sharedFolderRegFilesName = new ArrayList<>();
        for (File sfrf : sharedFolderRegFiles) {
            sharedFolderRegFilesName.add(sfrf.getName());
        }

        request.setAttribute("sharedFolderRegFilesName", sharedFolderRegFilesName);
        request.setAttribute("sysMsgs", sysMsgs);
        session.setAttribute("SHARED_FOLDER_PATH", SHARED_FOLDER_PATH);
        request.getRequestDispatcher("transfer.jsp").forward(request, response);
    }
}
