package com.leanlogistics.squotem.service.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.leanlogistics.squotem.co.QuoteWorkflowStatusCO;
import com.leanlogistics.squotem.model.User;
import com.leanlogistics.squotem.service.dao.UserDao;

public class UserManager extends BaseManager {
    
    protected static final String HASH_ALGORITHM = "SHA-256";
    
    private UserDao dao;
    
    public UserManager() {
        dao = new UserDao();
    }
   
    public User getUser(String userName, String password) {
        String hashedPassword = null;
        try {
            // Hash the provided password before querying
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] dataBytes = new byte[1024];
            InputStream is = new ByteArrayInputStream(password.getBytes());
            int nread = 0; 
            while ((nread = is.read(dataBytes)) != -1) {
              md.update(dataBytes, 0, nread);
            };
            byte[] mdbytes = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
              sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();            
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
        }
        catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        }
        // System.out.println("Hashed password: " + hashedPassword);
        return dao.getUser(hibernateSession, userName, hashedPassword);
    }
    
    public List<User> getUsers() {
        return dao.getUsers(hibernateSession);
    }
    
    public List<User> getNotificationList(QuoteWorkflowStatusCO status)
    {
    	return dao.getNotificationList(hibernateSession, status);
    }

}
