import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

/**
  * Pentru a citi datele care o sa fie adaugate in catalog, fie de
  * asistent, fie de profesor, am ales sa folosesc un fisiser json.
  *
  * Pentru a citi din fisier am folosit json.simple, jar care se
  * alfa in folderul src din proiect. Totodata, ca input am ales fisierul
  * "note.json", care se afla tot in folderul src
  *
  *
  * Am presupus ca asistenul poate citi din fisier doar nota
  * "partialGrade", iar profesorum doar "examGrade", de aceea,
  * in fisierul json se afla un singur camp "Grade".
  *
  */
public class ScoreVisitor implements Visitor{

    protected HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> exam = new HashMap<>();
    protected HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partial = new HashMap<>();

    private static Catalog catalog;

    /**
     * Metode care construiesc un string pentru fiecare validare care va fi facuta de
     * de asistent/profesor
     */
    public ArrayList<String> createNotifications(User user) {

        ArrayList<String> v =  new ArrayList<>();
        Set set;
        if (user instanceof Assistant)
            set = partial.entrySet();
        else set = exam.entrySet();

        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) i.next();
            if(mapEntry.getKey().equals(user)) {
                ArrayList<Tuple<Student, String, Double>> list =
                        (ArrayList<Tuple<Student, String, Double>>) mapEntry.getValue();
                for (Tuple<Student, String, Double> j : list) {
                    v.add(j.getStudent() + " " + j.getCourse() + " " + j.getGrade());
                }
            }
        }

        return v;
    }


    public Student createStudent(JSONObject object){
        JSONObject studentObj = (JSONObject) object.get("Student");
        String firstName = (String) studentObj.get("firstName");
        String lastName = (String) studentObj.get("lastName");
        Student newStudent = new Student(firstName, lastName);

        JSONObject motherObj = (JSONObject) studentObj.get("mother");
        firstName = (String) motherObj.get("firstName");
        lastName = (String) motherObj.get("lastName");
        Parent mother = new Parent(firstName,lastName);

        JSONObject fatherObj = (JSONObject) studentObj.get("father");
        firstName = (String) fatherObj.get("firstName");
        lastName = (String) fatherObj.get("lastName");
        Parent father = new Parent(firstName,lastName);

        newStudent.setMother(mother);
        newStudent.setFather(father);

        return newStudent;
    }

    public void readFile(User user) {

        JSONParser parser = new JSONParser();
        ArrayList<Tuple<Student, String, Double>> list = new ArrayList<>();

        try {
            FileReader reader = new FileReader("note.json");
            Object object = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonArray = (JSONArray) jsonObject.get("students");
            Iterator<JSONObject> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject elem = (JSONObject) iterator.next();

                Student student = createStudent(elem);
                String course = (String) elem.get("Course");
                Double grade = (Double) elem.get("Grade");

                Tuple<Student, String, Double> tuple = new Tuple<>(student, course, grade);
                //adaugam in lista cu note
                list.add(tuple);
            }

        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}

        if(user instanceof Assistant)
            partial.put((Assistant) user, list);
        else
            exam.put((Teacher) user, list);

    }

    @Override
    public void visit(Assistant assistant) {
        catalog = Catalog.getInstance();

        readFile(assistant);

        Grade grade = new Grade();
        //Parcurgem fiecare nota adaugata pentru a putea crea un Grade, iar apoi
        //notificam observatorii

        Set set = partial.entrySet();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) i.next();
            ArrayList<Tuple<Student, String, Double>> list =
                    (ArrayList<Tuple<Student, String, Double>>) mapEntry.getValue();
            for (Tuple<Student, String, Double> j : list) {
                grade.setStudent((Student) j.getStudent());
                grade.setPartialScore((Double) j.getGrade());
                grade.setExamScore(0.0);
                grade.setCourse((String) j.getCourse());

                catalog.getCourse(grade.getCourse()).addGrade(grade);
                catalog.notifyObserver(grade);
            }
        }
    }


    @Override
    public void visit(Teacher teacher) {
        catalog = Catalog.getInstance();
        Grade grade = new Grade();

        readFile(teacher);

        Set set = exam.entrySet();
        Iterator i = set.iterator();

        while (i.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) i.next();
            ArrayList<Tuple<Student, String, Double>> list =
                    (ArrayList<Tuple<Student, String, Double>>) mapEntry.getValue();
            for (Tuple<Student, String, Double> j : list) {
                grade.setStudent((Student) j.getStudent());
                grade.setExamScore((Double) j.getGrade());
                grade.setPartialScore(0.0);
                grade.setCourse((String) j.getCourse());
                catalog.getCourse(grade.getCourse()).addGrade(grade);
                catalog.notifyObserver(grade);
            }
        }

    }

    private class Tuple<T,K,V>{
        private T student;
        private K course;
        private V grade;

        public Tuple(Student student, String course, Double grade) {
            this.course = (K) course;
            this.student = (T) student;
            this.grade = (V) grade;
        }

        public T getStudent() {
            return student;
        }

        public K getCourse() {
            return course;
        }

        public V getGrade() {
            return grade;
        }

        public void setStudent(T student) {
            this.student = student;
        }

        public void setCourse(K course) {
            this.course = course;
        }

        public void setGrade(V grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "student=" + student +
                    ", course=" + course +
                    ", grade=" + grade +
                    '}';
        }
    }
}
