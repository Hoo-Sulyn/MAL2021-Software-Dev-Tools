package com.example.mal2021_assessment2.repository;

import com.example.mal2021_assessment2.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
public class LMSRepositoryImp implements LMSRepository {
    private final RestTemplate restTemplate;

    private final String BASE_URL = "http://lms-api.com/api";

    public LMSRepositoryImp(){
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Student> getAllStudents(){
        // connect and parse API
        Student[] students = restTemplate.getForObject(BASE_URL + "/students", Student[].class);

        return students != null ? Arrays.asList(students) : Arrays.asList();
    }

    @Override
    public List<Instructor> getAllInstructors(){
        Instructor[] instructors = restTemplate.getForObject(BASE_URL + "/instructors", Instructor[].class);

        return instructors != null ? Arrays.asList(instructors): Arrays.asList();
    }

    @Override
    public List<Course> getAllCourses(){
        Course[] courses = restTemplate.getForObject(BASE_URL + "/courses", Course[].class);

        return courses != null ? Arrays.asList(courses): Arrays.asList();
    }

    @Override
    public List<Enrollment> getAllEnrollments(){
        Enrollment[] enrollments = restTemplate.getForObject(BASE_URL + "/enrollments", Enrollment[].class);

        return enrollments != null ? Arrays.asList(enrollments): Arrays.asList();
    }
}
