import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MasterPage extends JFrame implements ActionListener {

    JButton parent, student, teacher;

    public MasterPage(){
        super("Master Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());

        parent = new JButton("Parent Page");
        student = new JButton("Student Page");
        teacher = new JButton("Teacher Page");
        parent.addActionListener(this);
        student.addActionListener(this);
        teacher.addActionListener(this);

        add(parent);
        add(student);
        add(teacher);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == parent) {
            new ParentPage();
        } else if(e.getSource() == teacher) {
            new TeacherPage();
        } else if(e.getSource() == student) {
            new StudentPage();
        }

    }
}
