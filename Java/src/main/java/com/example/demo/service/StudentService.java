package com.example.demo.service;

import com.example.demo.entity.Student;

public interface StudentService {
    public Student login(Student student);
    public Long addStudent(Student student);
}
