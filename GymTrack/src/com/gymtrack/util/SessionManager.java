package com.gymtrack.util;
import com.gymtrack.model.User;

public class SessionManager {

    private static User currentUser = null;

    public static void setCurrentUser(User user) 
    { 
    	currentUser = user; }
    public static User getCurrentUser()          
    {
    	return currentUser; }

    public static void clearSession()            
    {
    	currentUser = null; }

    public static boolean isLoggedIn()           
    { 
    	return currentUser != null; }

    public static String getRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
}

