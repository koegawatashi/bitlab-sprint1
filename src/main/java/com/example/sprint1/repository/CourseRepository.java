package com.example.sprint1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sprint1.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
} 