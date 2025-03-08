package com.neuro.forum2.repositories;

import com.neuro.forum2.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface StudentRepository extends JpaRepository<Student, Long> {}
