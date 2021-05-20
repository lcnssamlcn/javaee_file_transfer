package com.practice.lcn.file_transfer.ejb;

import com.practice.lcn.file_transfer.common.util.Sha512Util;

import javax.ejb.Stateful;

import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateful
public class LoginEJB implements LoginEJBRemote {
    private Logger log = LogManager.getLogger();

    private Properties env;

    public void setEnv(Properties env) {
        this.env = env;
    }

    @Override
    public boolean isCredentialValid(String username, String password) throws Exception {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(env.getProperty("DB.SQL_DRIVER_CLASS"));
            conn = DriverManager.getConnection(
                env.getProperty("DB.URL"), 
                env.getProperty("DB.USERNAME"), 
                env.getProperty("DB.PASSWORD")
            );

            cstmt = conn.prepareCall("{call FT_FindUserByName(?)}");
            cstmt.setString("p_name", username);
            cstmt.execute();

            rs = cstmt.getResultSet();
            if (rs.next()) {
                String passwordReal = rs.getString(2);
                String passwordSha512 = Sha512Util.hash(password);
                return passwordReal.equals(passwordSha512);
            }
            else {
                return false;
            }
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
            }
            catch (SQLException e) {
            
            }
            try {
                if (cstmt != null)
                    cstmt.close();
            }
            catch (SQLException e) {
            
            }
            try {
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e) {
            
            }
        }
    }
}
