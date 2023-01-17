import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CitireTeste {



    public Teacher createTeacher(JSONObject object) {
        JSONObject teacherObj = (JSONObject) object.get("Teacher");
        String firstName = (String) teacherObj.get("firstName");
        String lastName = (String) teacherObj.get("lastName");
        Teacher newTeacher = new Teacher(firstName, lastName);
        return newTeacher;
    }

    public void readCourses(Iterator<JSONObject> iterator) {
        Catalog catalog = Catalog.getInstance();
        ArrayList<Parent> parents = new ArrayList<>();
        while (iterator.hasNext()) {

            JSONObject elem = (JSONObject) iterator.next();
            Integer credits = Math.toIntExact((Long) elem.get("Credits"));
            String strategy = (String) elem.get("Strategy");
            String type = (String) elem.get("Type");
            String name = (String) elem.get("Name");
            Teacher proffesor = createTeacher(elem);

            Strategy str = null;

            if (strategy.equals("BestExamScore")) {
               str = new BestExamScore();
            }
            if(strategy.equals("BestPartialScore")) {
                str = new BestExamScore();
            }
            if(strategy.equals("BestTotalScore")){
                str = new BestTotalScore();
            }

            Course course = null;
            if(type.equals("full")) {
                course = new FullCourse.FullCourseBuilder(name)
                        .professor(proffesor)
                        .credits(credits)
                        .strategy(str)
                        .assistant()
                        .grades()
                        .groups()
                        .build();
            }else{
                course = new PartialCourse.PartialCourseBuilder(name)
                        .professor(proffesor)
                        .credits(credits)
                        .strategy(str)
                        .assistant()
                        .grades()
                        .groups()
                        .build();
            }
            /**
             * O sa adaug studentul prezent in nota, pentru a evita inca un camp si
             * inca o parsare.
             */

            JSONArray array = (JSONArray) elem.get("groups");
            if(array!=null) {
                Iterator<JSONObject> i = array.iterator();
                while (i.hasNext()) {
                    JSONObject e = i.next();
                    String id = (String) e.get("ID");
                    JSONObject a = (JSONObject) e.get("Assistant");
                    String firstName = (String) a.get("firstName");
                    String lastName = (String) a.get("lastName");
                    Assistant assistant = new Assistant(firstName, lastName);
                    course.addGroup(id, assistant);
                }
            }


            JSONArray jsonArray = (JSONArray) elem.get("assistants");
            if(jsonArray != null) {
                Iterator<JSONObject> it = jsonArray.iterator();

                while (it.hasNext()) {
                    JSONObject e = (JSONObject) it.next();
                    e = (JSONObject) e.get("Assistant");
                    String firstName = (String) e.get("firstName");
                    String lastName = (String) e.get("lastName");

                    Assistant assistant = new Assistant(firstName, lastName);
                    //adaugam asistentii intr-o grupa care deja a fost citita din json
                    //pentru a nu complica foarte tare json de test
                    course.addAssistant("ABC", assistant);
                }
            }

            jsonArray = (JSONArray) elem.get("grades");
            if (jsonArray != null) {
                Iterator<JSONObject> it = jsonArray.iterator();

                while (it.hasNext()) {
                    JSONObject e = (JSONObject) it.next();
                    e = (JSONObject) e.get("Grade");
                    String Course = (String) e.get("Course");
                    Double partialScore = (Double) e.get("partialScore");
                    Double finalScore = (Double) e.get("finalScore");
                    String id = (String) e.get("ID");
                    Visitor v = new ScoreVisitor();
                    Student student = ((ScoreVisitor) v).createStudent(e);

                    Grade grade = new Grade(Course, student, partialScore, finalScore);
                    //adaugam asistentii intr-o grupa care deja a fost citita din json
                    //pentru a nu complica foarte tare json de test
                    if(checkcontain(parents, student.getFather()) == true) {
                        parents.add(student.getFather());
                        catalog.addObserver(student.getFather());
                    }

                    if(checkcontain(parents, student.getMother()) == true) {
                        parents.add(student.getMother());
                        catalog.addObserver(student.getMother());
                    }
                    //catalog.addObserver(student.getMother());
                    course.addStudent(id,student);
                    course.addGrade(grade);
                }
            }

            catalog.addCourse(course);

        }
    }

    public boolean checkcontain(ArrayList<Parent> parents, Parent parent) {
        System.out.println(parents);
        System.out.println(parent);
        for(Parent parent1 : parents){
            if((parent1.getFirstName()+" "+parent1.getLastName()).equals(parent.getFirstName()+" "+parent.getLastName()))
                return false;
        }
        return true;
    }

    public static void main() {
        JSONParser parser = new JSONParser();
        Catalog catalog = Catalog.getInstance();

        try {
            FileReader reader = new FileReader("cursuri.json");
            Object object = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;
            JSONArray jsonArray = (JSONArray) jsonObject.get("courses");
            Iterator<JSONObject> iterator = jsonArray.iterator();
            CitireTeste citireTeste = new CitireTeste();
            citireTeste.readCourses(iterator);
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}

    }
}
