import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ParentPage extends JFrame implements ActionListener {
    JTextField text;
    JButton button;
    JLabel label;
    String parent;
    public ParentPage(){
        super("Parent page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        getContentPane().setBackground(Color.white);
        setLayout(new FlowLayout());

        label = new JLabel("Please use LastName + FirstName format");
        text = new JTextField(25);
        button = new JButton("Search");
        button.addActionListener(this);

        add(label);
        add(text);
        add(button);

        show();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            parent = text.getText();
            if(parent.length() > 0)
                Notifications();
        }
    }

    public void Notifications(){
        setLayout(new GridLayout());

        Catalog catalog = Catalog.getInstance();
        ArrayList<Student> students;
        ArrayList<Notification> notifications = new ArrayList<>();
        String mother;
        String father;
        DefaultListModel<Notification> listModel = new DefaultListModel<>();
        JList<Notification> list;
        JScrollPane scrollPane;

        /**
         *  Parcurg fiecare curs si caut studentii care
         *  au ca parinte numele introdus, adaug toate notificarile
         *  intr-un vector separat si ii afisez continutul intr-o
         *  fereastra separata
         */

        for (Course course : catalog.getCourses()) {
            students = course.getAllStudents();
            for (Student iterator : students) {
                mother = father = null;

                if(iterator.getMother() != null)
                    mother = iterator.getMother().getLastName() + " " + iterator.getMother().getFirstName();

                if(iterator.getFather() != null)
                    father = iterator.getFather().getLastName() + " " + iterator.getFather().getFirstName();

                if(mother != null)
                    if(mother.equals(parent))
                        notifications.addAll(iterator.getMother().getNotifications());

                if(father != null)
                    if(father.equals(parent))
                        notifications = iterator.getMother().getNotifications();
            }
        }

        ArrayList<Notification> no_duplicates = new ArrayList<>();
        for (Notification not : notifications)
            if (!no_duplicates.contains(not))
                no_duplicates.add(not);

        for (Notification not : no_duplicates)
            listModel.addElement(not);

        list = new JList<>(listModel);

        scrollPane = new JScrollPane(list);

        Window(scrollPane);
    }

    public void Window(JScrollPane scrollPane){
        JFrame frame = new JFrame("Notifications!!!!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        /*Timer timer = new Timer(3000, new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               scrollPane.revalidate();
               scrollPane.repaint();
           }
        });

        timer.start();*/
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}
