package com.example.nsbmstudenthub.service.impl;

import com.example.nsbmstudenthub.entity.Student;
import com.example.nsbmstudenthub.repository.StudentRepository;
import com.example.nsbmstudenthub.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Student create(Student s) {
        if (repo.existsByEmail(s.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return repo.save(s);
    }

    @Override
    public Student getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public Page<Student> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Student update(Long id, Student s) {
        Student existing = getById(id);

        // if email changed, enforce uniqueness
        if (!existing.getEmail().equals(s.getEmail()) && repo.existsByEmail(s.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
        existing.setBatch(s.getBatch());
        existing.setGpa(s.getGpa());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
