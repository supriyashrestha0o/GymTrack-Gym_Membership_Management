package com.gymtrack.model;

public class Member extends User {

    private int    memberId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private String gender;
    private String goal;

    // Membership plan fields
    private String planType;
    private int    duration;
    private String startDate;
    private double totalAmount;

    // Assigned trainer name (joined from DB for display)
    private String assignedTrainerName;

    // Constructor
    public Member(int userId, String username, String password,
                  int memberId, String fullName, String email,
                  String phone, String address, String dob,
                  String gender, String goal) {
        super(userId, username, password, "Member");
        this.memberId  = memberId;
        this.fullName  = fullName;
        this.email     = email;
        this.phone     = phone;
        this.address   = address;
        this.dob       = dob;
        this.gender    = gender;
        this.goal      = goal;
    }

    // Getters 
    public int    getMemberId()   { return memberId;   }
    public String getFullName()   { return fullName;   }
    public String getEmail()      { return email;      }
    public String getPhone()      { return phone;      }
    public String getAddress()    { return address;    }
    public String getDob()        { return dob;        }
    public String getGender()     { return gender;     }
    public String getGoal()       { return goal;       }
    public String getPlanType()   { return planType;   }
    public int    getDuration()   { return duration;   }
    public String getStartDate()  { return startDate;  }
    public double getTotalAmount(){ return totalAmount; }
    public String getAssignedTrainerName() { return assignedTrainerName; }

    // Setters 
    public void setMemberId(int id)           { this.memberId   = id;    }
    public void setFullName(String n)         { this.fullName   = n;     }
    public void setEmail(String e)            { this.email      = e;     }
    public void setPhone(String p)            { this.phone      = p;     }
    public void setAddress(String a)          { this.address    = a;     }
    public void setDob(String d)              { this.dob        = d;     }
    public void setGender(String g)           { this.gender     = g;     }
    public void setGoal(String g)             { this.goal       = g;     }
    public void setPlanType(String pt)        { this.planType   = pt;    }
    public void setDuration(int dur)          { this.duration   = dur;   }
    public void setStartDate(String sd)       { this.startDate  = sd;    }
    public void setTotalAmount(double amt)    { this.totalAmount= amt;   }
    public void setAssignedTrainerName(String t) { this.assignedTrainerName = t; }

    @Override
    public String toString() { return fullName; }
}
