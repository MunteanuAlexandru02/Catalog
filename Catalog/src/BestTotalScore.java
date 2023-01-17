import java.util.TreeSet;

public class BestTotalScore implements Strategy{
    @Override
    public Student getBestStudent(TreeSet<Grade> grades) {
        Student aux = null;
        Double max = 0.0;

        for (Grade grade : grades) {
            if (grade.getTotal() > max) {
                max = grade.getTotal();
                aux = grade.getStudent();
            }
        }

        return aux;
    }
}
