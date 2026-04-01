package com.example.mal2021_assessment2.gui;

import com.example.mal2021_assessment2.model.*;
import com.example.mal2021_assessment2.repository.LMSCourseRepository;
import com.example.mal2021_assessment2.repository.LMSInstructorRepository;
import com.example.mal2021_assessment2.service.LMSService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LMSDashboard extends JFrame {

    private final LMSService lmsService;
    private DefaultTableModel studentModel;
    private DefaultTableModel enrollmentModel;
    private JTable studentTable;
    private LMSCourseRepository courseRepo;
    private LMSInstructorRepository instructorRepo;

    public LMSDashboard(LMSService lmsService,
                        LMSCourseRepository courseRepo,
                        LMSInstructorRepository instructorRepo) {
        this.lmsService = lmsService;
        this.courseRepo = courseRepo;
        this.instructorRepo = instructorRepo;

        // 1. Basic Window Setup
        setTitle("LMS - Student Administration (F1)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Panel with Padding
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- 2. TOP SECTION: Student Table ---
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.WHITE);
        JLabel studentHeader = new JLabel("Select a Student to View Enrollments");
        studentHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));

        studentModel = new DefaultTableModel(new String[]{"Student ID", "Full Name", "Email"}, 0);
        studentTable = new JTable(studentModel);
        studentTable.setRowHeight(25);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        topPanel.add(studentHeader, BorderLayout.NORTH);
        topPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        mainPanel.add(topPanel);

        // --- 3. BOTTOM SECTION: Enrollment Table (F1) ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(Color.WHITE);
        JLabel enrollmentHeader = new JLabel("Student Course Enrollments Info");
        enrollmentHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        enrollmentHeader.setForeground(new Color(41, 128, 185));

        // Show Course details
        enrollmentModel = new DefaultTableModel(new String[]{"Course ID", "Course Name", "Instructor"}, 0);
        JTable enrollmentTable = new JTable(enrollmentModel);
        enrollmentTable.setRowHeight(25);

        bottomPanel.add(enrollmentHeader, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(enrollmentTable), BorderLayout.CENTER);
        mainPanel.add(bottomPanel);

        // --- 4. SELECTION LOGIC ---
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int selectedRow = studentTable.getSelectedRow();
                // Assumes Column 0 is the ID
                Long studentId = (Long) studentModel.getValueAt(selectedRow, 0);
                refreshEnrollmentTable(studentId);
            }
        });

        // --- 5. FOOTER SECTION: BUTTONS ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);

        JButton btnClear = new JButton("Clear Selection");
        btnClear.setBackground(new Color(231, 76, 60)); // A nice soft Red
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);

        // Logic for the Clear Button
        btnClear.addActionListener(e -> {
            studentTable.clearSelection();

            enrollmentModel.setRowCount(0);

            enrollmentModel.addRow(new Object[]{"-", "Select a student above", "-"});
        });

        footerPanel.add(btnClear);

        add(footerPanel, BorderLayout.SOUTH);

        // Initial Load
        refreshStudentTable();
        setLocationRelativeTo(null);
    }

    private void refreshStudentTable() {
        studentModel.setRowCount(0);

        List<Student> students = lmsService.getAllStudents();

        for (Student s : students) {
            studentModel.addRow(new Object[]{
                    s.getStudentId(),
                    s.getName(),
                    s.getEmail()
            });
        }
    }

    private void refreshEnrollmentTable(Long studentId) {
        enrollmentModel.setRowCount(0);

        // 1. Get the list of enrollments for this student (Requirement F1)
        List<Enrollment> enrollments = lmsService.getStudentEnrollments(studentId);

        for (Enrollment en : enrollments) {
            // 2. Use the courseId from the enrollment to find the actual Course details
            courseRepo.findById(en.getCourseId()).ifPresent(course -> {

                // 3. Find the Instructor name using the instructorId from the course
                String instructorName = instructorRepo.findById(course.getInstructorId())
                        .map(Instructor::getName)
                        .orElse("Unknown");

                // 4. Add the data to the table row
                enrollmentModel.addRow(new Object[]{
                        course.getCourseId(),
                        course.getTitle(),
                        instructorName
                });
            });
        }

        if (enrollments.isEmpty()) {
            enrollmentModel.addRow(new Object[]{"N/A", "No Enrollments Found", "-"});
        }
    }
}