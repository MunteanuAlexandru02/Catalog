import java.util.*;

public class FullCourse extends Course{

    public FullCourse(String name){
        super(name);
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        HashMap<Student, Grade> students = getAllStudentGrades();

        ArrayList<Student> grad_students = new ArrayList<>();

        if (!students.isEmpty()) {
            System.out.println("Nu sunt studenti\n");
            return null;
        }

        Set entrySet = students.entrySet();

        for (Object o : entrySet) {
            Map.Entry<Student, Grade> m = (Map.Entry<Student, Grade>) o;

            if(m.getValue().getPartialScore() >= 3 && m.getValue().getExamScore() >= 2) {
                grad_students.add(m.getKey());
            }
        }

        return grad_students;
    }

    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String name) {
            this.name = name;
            this.assistant = new HashSet<>();
            this.grades = new TreeSet<>();
            this.groups = new HashMap<>();
        }

        @Override
        public CourseBuilder strategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        @Override
        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public CourseBuilder professor(Teacher professor) {
            this.professor = professor;
            return this;
        }

        @Override
        public CourseBuilder credits(Integer credits) {
            this.credits = credits;
            return this;
        }

        @Override
        public CourseBuilder assistant() {
            this.assistant = new HashSet<>();
            return this;
        }

        @Override
        public CourseBuilder grades() {
            this.grades = new TreeSet<>();
            return this;
        }

        @Override
        public CourseBuilder groups() {
            this.groups = new HashMap<>();
            return this;
        }

        @Override
        public Course build() {
            return new Course(this) {
                @Override
                public ArrayList<Student> getGraduatedStudents() {
                    return null;
                }
            };
        }
    }
}
