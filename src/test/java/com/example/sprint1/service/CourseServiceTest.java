package com.example.sprint1.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sprint1.dto.CourseDto;
import com.example.sprint1.entity.Course;
import com.example.sprint1.mapper.CourseMapper;
import com.example.sprint1.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        course = new Course(1L, "Test Course", "Test Description", 99.99);
        courseDto = new CourseDto(1L, "Test Course", "Test Description", 99.99);
    }

    @Test
    void getAllCourses_ShouldReturnAllCourses() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        when(courseMapper.toDto(any(Course.class))).thenReturn(courseDto);

        List<CourseDto> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseDto, result.get(0));
        verify(courseRepository).findAll();
    }

    @Test
    void getCourseById_WhenCourseExists_ShouldReturnCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.getCourseById(1L);

        assertNotNull(result);
        assertEquals(courseDto, result);
        verify(courseRepository).findById(1L);
    }

    @Test
    void getCourseById_WhenCourseDoesNotExist_ShouldThrowException() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> courseService.getCourseById(1L));
        verify(courseRepository).findById(1L);
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() {
        when(courseMapper.toEntity(courseDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.createCourse(courseDto);

        assertNotNull(result);
        assertEquals(courseDto, result);
        verify(courseRepository).save(course);
    }

    @Test
    void updateCourse_WhenCourseExists_ShouldReturnUpdatedCourse() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(courseMapper.toEntity(courseDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDto(course)).thenReturn(courseDto);

        CourseDto result = courseService.updateCourse(1L, courseDto);

        assertNotNull(result);
        assertEquals(courseDto, result);
        verify(courseRepository).save(course);
    }

    @Test
    void updateCourse_WhenCourseDoesNotExist_ShouldThrowException() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(1L, courseDto));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void deleteCourse_WhenCourseExists_ShouldDeleteCourse() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        courseService.deleteCourse(1L);

        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteCourse_WhenCourseDoesNotExist_ShouldThrowException() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(1L));
        verify(courseRepository, never()).deleteById(any());
    }
} 