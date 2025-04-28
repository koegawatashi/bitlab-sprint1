package com.example.sprint1.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.sprint1.dto.CourseDto;
import com.example.sprint1.service.CourseService;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        courseDto = new CourseDto(1L, "Test Course", "Test Description", 99.99);
    }

    @Test
    void getAllCourses_ShouldReturnAllCourses() {
        when(courseService.getAllCourses()).thenReturn(Arrays.asList(courseDto));

        ResponseEntity<List<CourseDto>> response = courseController.getAllCourses();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(courseDto, response.getBody().get(0));
        verify(courseService).getAllCourses();
    }

    @Test
    void getCourseById_ShouldReturnCourse() {
        when(courseService.getCourseById(1L)).thenReturn(courseDto);

        ResponseEntity<CourseDto> response = courseController.getCourseById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
        verify(courseService).getCourseById(1L);
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() {
        when(courseService.createCourse(any(CourseDto.class))).thenReturn(courseDto);

        ResponseEntity<CourseDto> response = courseController.createCourse(courseDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
        verify(courseService).createCourse(courseDto);
    }

    @Test
    void updateCourse_ShouldReturnUpdatedCourse() {
        when(courseService.updateCourse(anyLong(), any(CourseDto.class))).thenReturn(courseDto);

        ResponseEntity<CourseDto> response = courseController.updateCourse(1L, courseDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
        verify(courseService).updateCourse(1L, courseDto);
    }

    @Test
    void deleteCourse_ShouldReturnOk() {
        doNothing().when(courseService).deleteCourse(1L);

        ResponseEntity<Void> response = courseController.deleteCourse(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(courseService).deleteCourse(1L);
    }
} 