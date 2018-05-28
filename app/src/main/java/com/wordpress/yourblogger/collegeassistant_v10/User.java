package com.wordpress.yourblogger.collegeassistant_v10;

/**
 * Created by arafat-213 on 27/3/18.
 */

public class User {

    private String uname;
    private String pass;
    private String semester;
    private String department;

    public User() {

        this.uname = "empty";
        this.pass = "empty";
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
