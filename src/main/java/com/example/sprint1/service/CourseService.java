package com.example.sprint1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sprint1.dto.CourseDto;
import com.example.sprint1.entity.Course;
import com.example.sprint1.mapper.CourseMapper;
import com.example.sprint1.repository.CourseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseDto> getAllCourses() {
        log.debug("Getting all courses");
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        log.debug("Getting course by id: {}", id);
        return courseRepository.findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));
    }

    @Transactional
    public CourseDto createCourse(CourseDto courseDto) {
        log.debug("Creating new course: {}", courseDto);
        Course course = courseMapper.toEntity(courseDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDto(savedCourse);
    }

    @Transactional
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        log.debug("Updating course with id: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Курс не найден");
        }
        Course course = courseMapper.toEntity(courseDto);
        course.setId(id);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        log.debug("Deleting course with id: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Курс не найден");
        }
        courseRepository.deleteById(id);
    }
} 