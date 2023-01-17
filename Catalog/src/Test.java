public class Test {
    public static void main(String[] args) {
        /*Useri*/
        User studentA = UserFactory.getUser("Student", "Alexandra", "Popescu");
        User studentB = UserFactory.getUser("Student", "Bogdan", "Lolot");
        User studentC = UserFactory.getUser("Student", "Alexandru", "Chiriac");
        User student = UserFactory.getUser("Student","Alexandru", "Munteanu");

        User mother = UserFactory.getUser("Parent", "Tanti", "Ionescu");
        User ramona = UserFactory.getUser("Parent", "Ramona", "Munteanu");
        User father = UserFactory.getUser("Parent", "Nenea", "Ionescu");
        User ionut = UserFactory.getUser("Parent", "Ionut", "Munteanu");

        User teacher = UserFactory.getUser("Teacher", "Victor", "Georgescu");
        User teacher2 = UserFactory.getUser("Teacher", "Mihai", "Popescu");
        User assistant = UserFactory.getUser("Assistant", "George", "Constantin");


        /*Cursuri*/
        Course course = new FullCourse.FullCourseBuilder("POO")
                                      .professor((Teacher) teacher)
                                      .assistant()
                                      .grades()
                                      .build();


        Course course2 = new FullCourse.FullCourseBuilder("DEEA")
                .professor((Teacher) teacher)
                .strategy(new BestExamScore())
                .build();

        Course course3 = new FullCourse.FullCourseBuilder("SO")
                .professor((Teacher) teacher2)
                .build();
        Course course4 = new FullCourse.FullCourseBuilder("FIZICA")
                .professor((Teacher) teacher)
                .build();
        Course course5 = new FullCourse.FullCourseBuilder("SPORT")
                .professor((Teacher) teacher2)
                .build();

        /*Adaugare separata de asistenti si crearea unei grupe*/
        course.addAssistant("ABC", (Assistant) assistant);

        Group group = new Group("ABC", (Assistant) assistant);
        group.addStudent((Student) studentA);
        group.addStudent((Student) studentC);
        group.addStudent((Student) student);

        /*Atribuirea unor note*/

        Grade grade = new Grade("POO", (Student) studentA, 10.0, 10.0);
        Grade grade2 = new Grade("POO", (Student) studentB, 9.0, 9.5);
        Grade grade3 = new Grade("POO",(Student) student, 0d,0d);

        /*adaugare separata de note in curs*/
        course.addGrade(grade);
        course.addGrade(grade3);
        course.addGrade(grade2);

        /*adaugarea unei grupe in curs*/
       /* course.addGroup(group);
        course2.addGroup(group);
        course3.addGroup(group);
        course4.addGroup(group);
        course5.addGroup(group);*/

        course.addStudent("ABC", (Student) studentB);

        Catalog catalog = Catalog.getInstance();
        catalog.addCourse(course);
        catalog.addCourse(course2);
        catalog.addCourse(course3);
        catalog.addCourse(course4);
        catalog.addCourse(course5);

        ((Student) studentB).setMother((Parent) mother);
        ((Student) student).setMother((Parent) ramona);
        ((Student) student).setFather((Parent) ionut);
        ((Student) studentC).setMother((Parent) mother);

        catalog.addObserver((Parent) mother);
        catalog.addObserver((Parent) ramona);
        catalog.addObserver((Parent) ionut);
        catalog.notifyObserver(new Grade("POO", (Student)studentB,2.5d,3d));

        catalog.removeCourse((Course) course);

        System.out.println(catalog);

        //System.out.println(course.getAllGrades());
        course.makebackup();
        course.addGrade(new Grade("POO", (Student) studentC, 3.0, 3.0));
        System.out.println(course.getAllGrades());
        course.undo();
        System.out.println(course.getAllGrades());

        MasterPage masterPage = new MasterPage();


    }
}
