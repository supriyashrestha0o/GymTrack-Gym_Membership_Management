package com.gymtrack.model;

public class User {

    private int userId;
    private String username;
    private String password;
    private String role; // "Member", "Trainer", or "Admin"

    // Constructor
    public User(int userId, String username, String password, String role) {
        this.userId   = userId;
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    // Constructor without ID (used when creating a new user before saving to DB)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    // Getters
    public int    getUserId()  { return userId;   }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }
    public String getRole()    { return role;     }

    // Setters
    public void setUserId(int userId)      { this.userId   = userId;   }
    public void setUsername(String u)      { this.username = u;        }
    public void setPassword(String p)      { this.password = p;        }
    public void setRole(String role)       { this.role     = role;     }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "', role='" + role + "'}";
    }
}
