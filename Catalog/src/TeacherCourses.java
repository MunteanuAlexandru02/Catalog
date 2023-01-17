import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TeacherCourses extends JFrame implements MouseListener, ActionListener {

    String global_teacher;
    Teacher proffesor;
    Assistant global_assistant;
    Integer ok = 0;
    JList<String> list;
    JButton button;

    GradesToValidate GTV;

    public TeacherCourses(String teacher) {
        super("Courses");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.white);
        setLayout(new GridLayout());

        global_teacher = teacher;

        Catalog catalog = Catalog.getInstance();
        String toCompare, toCompare2;

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for(Course course : catalog.getCourses()){
            toCompare = course.getProfessor().getLastName() + " "
                    + course.getProfessor().getFirstName();

            if (toCompare.equals(teacher)) {
                if(ok == 0)
                    proffesor = new Teacher(course.getProfessor().getFirstName(),
                            course.getProfessor().getLastName());
                ok = 1;
                listModel.addElement(course.getName());
            }
            else if (course.getAssistant(teacher) != null) {
                if(ok == 0)
                    global_assistant = course.getAssistant(teacher);
                ok = 2;
                listModel.addElement(course.getName());
            }
        }



        list = new JList<>(listModel);
        list.setLayoutOrientation(JList.VERTICAL);
        list.addMouseListener(this);
        JScrollPane scrollPane = new JScrollPane(list);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        button = new JButton("Verify all");

        button.addActionListener( this);
        this.add(scrollPane);
        this.add(button);
        this.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if(ok == 1)
                GTV = new GradesToValidate(proffesor, list.getSelectedValue());
            if(ok == 2)
                GTV = new GradesToValidate(global_assistant, list.getSelectedValue());
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            Visitor v = new ScoreVisitor();
            if(ok == 1) {
                v.visit(proffesor);
            }
            else {
                v.visit(global_assistant);
            }
        }
    }
}
