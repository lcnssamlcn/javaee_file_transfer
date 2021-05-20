package com.practice.lcn.file_transfer.ejb;

import javax.ejb.Remote;

import java.util.Properties;

@Remote
public interface LoginEJBRemote {
    void setEnv(Properties env);

    boolean isCredentialValid(String username, String password) throws Exception;
}
