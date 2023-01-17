import java.util.ArrayList;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private Assistant assistant;
    private String id;
    private final ArrayList<Student> students;

    private Comparator<Student> comp = null;

    public void setAssistant(Assistant assistant){
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
        if  (comp != null) {
            students.sort(comp);
        }
    }

    public Group(String ID, Assistant assistant) {
        this.assistant = assistant;
        this.id = ID;
        students = new ArrayList<>();
    }

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        id = ID;
        this.assistant = assistant;
        this.comp = comp;
        students = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Group{" +
                "assistant=" + assistant +
                ", id='" + id + '\'' +
                ", students=" + students +
                ", comp=" + comp +
                '}';
    }
}
