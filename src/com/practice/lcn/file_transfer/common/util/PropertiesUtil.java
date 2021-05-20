package com.practice.lcn.file_transfer.common.util;

import java.util.Properties;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class PropertiesUtil {
    public static Properties loadProperties(String propFilePath) throws IOException {
        InputStream is = null;
        Properties prop = null;
        try {
            is = new FileInputStream(propFilePath);
            prop = new Properties();
            prop.load(is);

            return prop;
        }
        finally {
            try {
                if (is != null)
                    is.close();
            }
            catch (IOException e) {
            
            }
        }
    }
}
