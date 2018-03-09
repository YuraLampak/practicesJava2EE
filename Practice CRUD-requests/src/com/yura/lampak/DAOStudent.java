package com.yura.lampak;

import java.util.List;

public interface DAOStudent {
    void connection();
    void disconnection();
    List<Student> getStudents();
    void removeStudentById();
    void createStudent();
    void updateStudentById();

}
