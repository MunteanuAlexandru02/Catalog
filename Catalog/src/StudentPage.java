import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class StudentPage extends JFrame implements ListSelectionListener, ActionListener, MouseListener {

    JTextField text;
    JButton button;
    JScrollPane scrollPane;
    String student;

    Integer mouse_counter = 0;

    JLabel label;

    JList<String> list;
    public StudentPage(){
        super("Student page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());

        label = new JLabel("Please use LastName + FirstName format");
        add(label);

        text = new JTextField(25);
        add(text);

        button = new JButton("Search");

        add(button);

        button.addActionListener(this);

        show();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            student = text.getText();
            if(student.length() > 0) {
                this.remove(text);
                this.remove(label);
                this.remove(button);
                this.revalidate();
                this.repaint();
                Courses();
            }
        }
    }

        public void Courses() {
            setLayout(new GridLayout());
            Catalog catalog = Catalog.getInstance();
            ArrayList<Student> students;
            ArrayList<Course> courses = new ArrayList<>();

            /**
             * Parcurg fiecare curs din catalog si verific studentii
             * inscrisi la cursul respectiv, apoi creez o lista de cursuri
             * la care va participa studentul, pe care o voi afisa, ulterior,
             * in JSCROLLPANE
             *
             *
             */
            for (Course course : catalog.getCourses()) {
                students = course.getAllStudents();
                for (Student iterator : students) {
                    if ((iterator.getLastName() + " " + iterator.getFirstName()).equals(student)) {
                        courses.add(course);
                    }
                }
            }

            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (Course course : courses) {
                listModel.addElement(course.getName());
            }

            list = new JList<>(listModel);

            JScrollPane scrollPane = new JScrollPane(list);

            list.addMouseListener(this);

            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            list.setLayoutOrientation(JList.VERTICAL);

            this.add(scrollPane);

            this.revalidate();
            this.repaint();
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            new Details(student, list.getSelectedValue());
        }
    }

    /*Functii care sunt din interfata MouseListener*/
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void valueChanged(ListSelectionEvent e){}
}
