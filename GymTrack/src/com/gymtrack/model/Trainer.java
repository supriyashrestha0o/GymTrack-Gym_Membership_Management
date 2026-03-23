package com.gymtrack.model;

public class Trainer extends User {

    private int    trainerId;
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String speciality;
    private int    experience;

    // Constructor 
    public Trainer(int userId, String username, String password,
                   int trainerId, String fullName, String email,
                   String phone, String dob, String gender,
                   String speciality, int experience) {
        super(userId, username, password, "Trainer");
        this.trainerId  = trainerId;
        this.fullName   = fullName;
        this.email      = email;
        this.phone      = phone;
        this.dob        = dob;
        this.gender     = gender;
        this.speciality = speciality;
        this.experience = experience;
    }

    // Getters 
    public int    getTrainerId()  { return trainerId;  }
    public String getFullName()   { return fullName;   }
    public String getEmail()      { return email;      }
    public String getPhone()      { return phone;      }
    public String getDob()        { return dob;        }
    public String getGender()     { return gender;     }
    public String getSpeciality() { return speciality; }
    public int    getExperience() { return experience; }

    // Setters
    public void setTrainerId(int id)       { this.trainerId  = id; }
    public void setFullName(String n)      { this.fullName   = n;  }
    public void setEmail(String e)         { this.email      = e;  }
    public void setPhone(String p)         { this.phone      = p;  }
    public void setDob(String d)           { this.dob        = d;  }
    public void setGender(String g)        { this.gender     = g;  }
    public void setSpeciality(String s)    { this.speciality = s;  }
    public void setExperience(int exp)     { this.experience = exp;}

    @Override
    public String toString() { return fullName; }
}
