package com.practice.lcn.file_transfer.servlet;

import com.practice.lcn.file_transfer.common.util.PropertiesUtil;

import com.practice.lcn.file_transfer.ejb.LoginEJBRemote;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Properties;
import java.util.Map;
import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name="LoginServlet", urlPatterns="/login")
public class LoginServlet extends HttpServlet {
    private Logger log = LogManager.getLogger();

    private static String PROPERTIES_FILEPATH = null;

    private static final String MSG_WRONG_USERNAME_PASSWORD = "Wrong username/password.";
    private static final String ERR_UNABLE_TO_LOAD_FT_PROP = "Unable to load \"file_transfer.properties\".";
    private static final String ERR_EJB_LOOKUP_FAILURE = "Fail to do EJB lookup.";
    private static final String ERR_LOGIN_EJB_REMOTE = "Internal error occurred.";

    @Override
    public void init(ServletConfig sc) {
        LoginServlet.PROPERTIES_FILEPATH = sc.getInitParameter("PROPERTIES_FILEPATH");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info(String.format("username: \"%s\"; password: \"%s\"", username, password));

        Properties prop = null;
        try {
            prop = PropertiesUtil.loadProperties(LoginServlet.PROPERTIES_FILEPATH);

            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                log.info(String.format("file_transfer.properties: key: \"%s\"; value: \"%s\"", (String) entry.getKey(), (String) entry.getValue()));
            }
        }
        catch (IOException e) {
            log.error(LoginServlet.ERR_UNABLE_TO_LOAD_FT_PROP, e);
            String errMsg = ExceptionUtils.getStackTrace(e);
            request.setAttribute("errMsg", errMsg);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        LoginEJBRemote lr = null;
        Context context = null;
        try {
            context = new InitialContext();
            lr = (LoginEJBRemote) context.lookup("java:global/file_transfer/file_transfer-ejb-1.0/LoginEJB!com.practice.lcn.file_transfer.ejb.LoginEJBRemote");
        }
        catch (NamingException e) {
            log.error(LoginServlet.ERR_EJB_LOOKUP_FAILURE, e);
            String errMsg = ExceptionUtils.getStackTrace(e);
            request.setAttribute("errMsg", errMsg);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        finally {
            try {
                if (context != null)
                    context.close();
            }
            catch (NamingException e) {
            
            }
        }

        try {
            lr.setEnv(prop);
            if (!lr.isCredentialValid(username, password)) {
                request.setAttribute("errMsg", LoginServlet.MSG_WRONG_USERNAME_PASSWORD);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
        }
        catch (Exception e) {
            log.error(LoginServlet.ERR_LOGIN_EJB_REMOTE, e);
            String errMsg = ExceptionUtils.getStackTrace(e);
            request.setAttribute("errMsg", errMsg);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        session.setAttribute("FILE_TRANSFER_PROPERTIES", prop);
        request.getRequestDispatcher("transfer").forward(request, response);
    }
}
