package com.example.nsbmstudenthub.service;
import com.example.nsbmstudenthub.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Student create(Student s);
    Student getById(Long id);
    Page<Student> getAll(Pageable pageable);
    Student update(Long id, Student s);
    void delete(Long id);
}
