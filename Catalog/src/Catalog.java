import java.util.ArrayList;

public class Catalog implements Subject{

    ///ma asigur ca e un singur catalog
    private static Catalog catalog;

    private Visitor v;

    private ArrayList<Observer> observers;

    private ArrayList<Course> courses;

    /**
     * Pentru a ma asigura ca mereu va exista o singura instanta a unui CATALOG,
     * voi implementa metoda getInstance.
     */

    public static Catalog getInstance(){
        if (catalog == null) {
            synchronized (Catalog.class) {
                if(catalog == null)
                    catalog = new Catalog();
            }
        }
        return catalog;
    }

    private Catalog(){
        observers = new ArrayList<>();
        courses = new ArrayList<>();
        v = new ScoreVisitor();
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "courses=" + courses +
                '}';
    }

    /**
     *  Am creat getCourses, deoarece, pentru a afisa toate cursurile la care
     *  participa un student, va trebui sa parcurg fiecare curs si sa verific daca
     *  exista studentul respectiv
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }


    /**
     * Functie care imi returneaza un curs, care are numele name
     */
    public Course getCourse(String name) {
        for(Course course : courses) {
            if(course.getName().equals(name))
                return course;
        }
        return null;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    @Override
    public void addObserver(Observer observer) {
       if(observer != null)
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Grade grade) {
        for (Observer observer : observers) {
            String name = grade.getStudent().getMother().getFirstName() + " " +
                    grade.getStudent().getMother().getLastName();
            Parent parinte = (Parent) observer;
            String name2 = parinte.getFirstName() + " " + parinte.getLastName();


            if (name.equals(name2)) {
                System.out.println(name);
                Course course = this.getCourse(grade.getCourse());

                Grade final_grade = course.getGrade(grade.getStudent());

                observer.update(new Notification("Studentul " + grade.getStudent().getLastName() + " " +
                        grade.getStudent().getFirstName() + " are nota la cursul " + grade.getCourse()
                        + " egala cu: " + final_grade.getTotal()));
            }
        }
    }
}