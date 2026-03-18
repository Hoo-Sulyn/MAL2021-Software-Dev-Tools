package com.example.mal2021_assessment2;

import com.example.mal2021_assessment2.gui.LMSDashboard;
import com.example.mal2021_assessment2.repository.LMSCourseRepository;
import com.example.mal2021_assessment2.repository.LMSInstructorRepository;
import com.example.mal2021_assessment2.service.LMSService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class Mal2021Assessment2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Mal2021Assessment2Application.class)
                .headless(false) // Required for Swing/AWT to work
                .run(args);

        // Get the Service and the UI from the context
        EventQueue.invokeLater(() -> {
            LMSService service = context.getBean(LMSService.class);
            LMSCourseRepository courseRepo = context.getBean(LMSCourseRepository.class);
            LMSInstructorRepository instructorRepo = context.getBean(LMSInstructorRepository.class);
            LMSDashboard gui = new LMSDashboard(service, courseRepo, instructorRepo);
            gui.setVisible(true);
        });
    }

}
