import java.util.TreeSet;

public class BestExamScore implements Strategy{
    @Override
    public Student getBestStudent(TreeSet<Grade> grades) {

        Student aux = null;
        Double max = 0.0;

        for (Grade grade : grades) {
            if(grade.getExamScore() > max) {
                max = grade.getExamScore();
                aux = grade.getStudent();
            }
        }

        return aux;
    }
}
