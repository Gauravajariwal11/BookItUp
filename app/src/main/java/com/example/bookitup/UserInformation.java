package com.example.bookitup;

public class UserInformation {

    private String fname;
    private String lname;
    private String school;
    private String major;
    private String email;

    public UserInformation(String fname, String lname, String school, String major, String email) {
        this.fname = fname;
        this.lname = lname;
        this.school= school;
        this.major = major;
        this.email = email;
    }
    public UserInformation(){}

    //fname
    public String getfname() {
        return fname;
    }
    public void setfname(String fname) {
            this.fname = fname;
        }

    //lname
    public String getlname() {
        return lname;
    }
    public void setlname(String lname ) {
            this.lname = lname;
        }

    //university
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
            this.school = school;
        }

    //major
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major;}

    //major
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}
}

