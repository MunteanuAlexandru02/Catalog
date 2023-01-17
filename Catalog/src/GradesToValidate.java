import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Din moment ce Tuple din ScoreVisitor este o clasa privata,
 * voi crea o copia a clasei private in aceasta, deoarece am ales sa
 * ma folosesc de metoda de citire din ScoreVisitor pentru a determina ce
 * note ar trebui validate de fiecare profesor/ asistent.
 *
 */


public class GradesToValidate extends JFrame {

    Teacher teacher;
    Assistant assistant;

    HashMap grades;
    JScrollPane scrollPane;

    public GradesToValidate(User user, String course) {
        super("Grades");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.white);
        setLayout(new GridLayout());

        Visitor v = new ScoreVisitor();
        System.out.println(user);
        ((ScoreVisitor) v).readFile(user);
        ArrayList<String> list = ((ScoreVisitor) v).createNotifications(user);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String notification : list) {
            if(notification.contains(course)){
                listModel.addElement(notification);
            }
        }

        System.out.println(listModel);

        JList<String> jList = new JList<>(listModel);
        jList.setLayoutOrientation(JList.VERTICAL);
        scrollPane = new JScrollPane(jList);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollPane);

        setVisible(true);
    }

}
