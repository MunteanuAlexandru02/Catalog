import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Details extends JFrame {
    public Details(String student, String courseName){
        super("Student page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.white);
        setLayout(new GridLayout());



        ArrayList<Student> students;
        //O sa caut cursul primit anterior
        Catalog catalog = Catalog.getInstance();
        Course our_course = null;
        Student our_student = null;
        for (Course course : catalog.getCourses()) {
            if (course.getName().equals(courseName)) {
                students = course.getAllStudents();
                for (Student iterator : students) {
                    if ((iterator.getLastName() + " " + iterator.getFirstName()).equals(student)) {
                        our_course = course;
                        our_student = iterator;
                        break;
                    }
                }
            }
        }
        assert our_course != null;
        Assistant assistant = our_course.getAssistant(our_student);
        Teacher teacher = our_course.getProfessor();

        DefaultListModel listModel = new DefaultListModel<>();

        listModel.addElement(teacher);
        listModel.addElement(assistant);
        if(our_course.getGrade(our_student) != null) {
            listModel.addElement("Partial score: " + our_course.getGrade(our_student).getPartialScore());
            listModel.addElement("Exam score: " + our_course.getGrade(our_student).getExamScore());
            listModel.addElement("Total score: " + our_course.getGrade(our_student).getTotal());
        }
        else {
            listModel.addElement("In acest moment, elevul nu are note\n");
        }



        JList list = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(list);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        list.setLayoutOrientation(JList.VERTICAL);

        this.add(scrollPane);

        System.out.println(assistant);
        System.out.println(our_course.getGrade(our_student));

        show();
    }
}
