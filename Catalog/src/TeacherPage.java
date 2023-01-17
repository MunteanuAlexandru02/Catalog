import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherPage extends JFrame implements ActionListener {

    JTextField text;

    JButton button;

    String teacher;

    JLabel label;

    public TeacherPage(){
        super("Teacher page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());

        label = new JLabel("Please use LastName + FirstName format");
        add(label);

        text = new JTextField(25);
        add(text);

        button = new JButton("Search");
        button.addActionListener(this);
        add(button);

        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            teacher = text.getText();
            if (teacher.length() > 0) {
                new TeacherCourses(teacher);
            }
        }
    }
}
