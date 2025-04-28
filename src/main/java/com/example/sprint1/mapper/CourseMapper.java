package com.example.sprint1.mapper;

import org.mapstruct.Mapper;

import com.example.sprint1.dto.CourseDto;
import com.example.sprint1.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
} 