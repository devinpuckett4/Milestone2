package com.gcu.workspacecst339.service;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.gcu.workspacecst339.model.User;

@Service
public class UserService {
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    public boolean usernameTaken(String username){ return username!=null && users.containsKey(username.toLowerCase()); }
    public void register(User u){ if(u!=null && u.getUsername()!=null) users.put(u.getUsername().toLowerCase(), u); }
    public User authenticate(String username, String password){
        if(username==null || password==null) return null;
        User u = users.get(username.toLowerCase());
        return (u!=null && password.equals(u.getPassword())) ? u : null;
    }
}
