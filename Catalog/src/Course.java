import java.util.*;

/**
 * Nu am idee ce face Comparator<Student> comp;
 */

public abstract class Course {
    private final String name;
    //titular
    private Teacher professor;
    private HashSet<Assistant> assistant;
    protected TreeSet<Grade> grades;
    private HashMap<String, Group> groups;
    private Integer credits;
    private Strategy strategy;

    protected Snapshot snapshot = new Snapshot();

    public void makebackup(){
        this.snapshot.makeBackup();
    }

    public void undo(){
        this.snapshot.undo();
    }

    public Course(CourseBuilder builder){
        groups = builder.groups;
        name = builder.name;
        professor = builder.professor;
        assistant = builder.assistant;
        grades = builder.grades;
        credits = builder.credits;
        strategy = builder.strategy;
        snapshot = new Snapshot();
    }

    public TreeSet<Grade> getAllGrades() {
        return grades;
    }

    public Course(String name) {this.name = name;}

    public String getName(){
        return this.name;
    }

    public Teacher getProfessor(){return professor;}

    /*
     * Functie care cauta fiecare grupa pentru a gasi studentul si
     * returneaza asistenul asociat grupei in care se afla studentul
     */
    public Assistant getAssistant(Student student) {
        ArrayList<Student> students;
        for (Map.Entry<String, Group> iterator : groups.entrySet()) {
            students = iterator.getValue().getStudents();
            if (students.contains(student)) {
                return iterator.getValue().getAssistant();
            }
        }
        return null;
    }


    /**
     * Functie care imi returneaza asistentul, primind numele acestuia
     *
     */
    public Assistant getAssistant(String assistant) {
        String toCompare;
        for(Assistant iterator : this.assistant) {
            toCompare = iterator.getLastName() + " " + iterator.getFirstName();
            if(toCompare.equals(assistant))
                return iterator;
        }
        return null;
    }

    public void addAssistant(String ID, Assistant assistant) {
        if(groups.containsKey(ID)) {
            groups.get(ID).setAssistant(assistant);
        }
        if (!this.assistant.contains(assistant)) {
            this.assistant.add(assistant);
        }
    }

    public void addStudent(String ID, Student student) {
        if(groups.get(ID) == null) {
            //System.out.println("HELLO");
            return;
        }

        groups.get(ID).addStudent(student);
    }

    public void addGroup(Group group) {
        String ID = group.getId();
        if (groups.containsKey(ID))
            return;

        groups.put(ID, group);
    }

    public void addGroup(String ID, Assistant assistant) {
        if(groups.containsKey(ID))
            return;

        Group group = new Group(ID, assistant);
        groups.put(ID, group);
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        //Grupa e deja inregistrata
        if(groups.containsKey(ID))
            return;
        Group group = new Group(ID, assistant, comp);
        groups.put(ID, group);
    }

    public Grade getGrade(Student student) {

        if(grades.isEmpty())
            return null;

        Iterator<Grade> iterator = grades.iterator();

        while (iterator.hasNext()) {
            Grade grade = iterator.next();

            if(grade.getStudent().compareStudents(student)) {
                System.out.println(grade);
                return grade;
            }
        }

        return null;
    }

    public void addGrade(Grade grade) {
        System.out.println(grades);
        /*
         * Verificam pentru notificarea din scoreVisitor
         * Cand un asistent introduce nota, examScore va fi
         * 0 => va trebui sa modific doar partial score,
         * invers pentru teacher
         */
        if(grade.getExamScore() == 0.0) {
            for(Grade gr : grades)
                if(gr.getStudent().compareStudents(grade.getStudent())){
                    gr.setPartialScore(grade.getPartialScore());
                    return;
                }
        } else if(grade.getPartialScore() == 0.0) {
            for (Grade gr : grades)
                if (gr.getStudent().compareStudents(grade.getStudent())) {
                    System.out.println(grade.getStudent());
                    gr.setExamScore(grade.getExamScore());
                    return;
                }
        }

        for (Grade gr : grades) {
            if(gr.compareTo(grade) == 0)
                return;
        }

        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();

        ///Parcurgem grupele pentru a gasi toti studentii

        for (Map.Entry<String, Group> iterator : groups.entrySet()) {
            students.addAll(iterator.getValue().getStudents());
        }

        return students;
    }

    public HashMap<Student, Grade> getAllStudentGrades(){
        HashMap<Student, Grade> studentgrades = new HashMap<>();

        if(!grades.isEmpty())
            return null;

        for (Grade grade : grades) {
            studentgrades.put(grade.getStudent(), grade);
        }

        return studentgrades;
    }

    public Student getBestStudent(){
        return this.strategy.getBestStudent(grades);
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", professor=" + professor +
                ", assistant=" + assistant +
                ", grades=" + grades +
                ", groups=" + groups +
                ", credits=" + credits +
                '}';
    }

    public abstract static class CourseBuilder {
        protected String name;
        protected Teacher professor; //titular
        protected HashSet<Assistant> assistant;
        protected TreeSet<Grade> grades;
        protected HashMap<String, Group> groups;
        protected Integer credits;

        protected Strategy strategy;

        public abstract CourseBuilder strategy(Strategy strategy);

        public abstract CourseBuilder name(final String name);

        public abstract CourseBuilder professor(final Teacher professor);

        public abstract CourseBuilder credits(final Integer credits);

        public abstract CourseBuilder assistant();

        public abstract CourseBuilder grades();

        public abstract CourseBuilder groups();

        public abstract Course build();

    }

    private class Snapshot {

        private TreeSet<Grade> back_grades;

        public Snapshot(){
             back_grades = new TreeSet<>();
        }

        public void makeBackup(){
            for (Grade grade : grades) {
                back_grades.add((Grade) grade.clone());
            }
        }

        public void undo() {
            grades = back_grades;
        }

    }
}

