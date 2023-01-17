import java.util.TreeSet;

public class BestPartialScore implements Strategy {

    /*Stim ca intr-un treeset elementele vor fi sortate crescator*/
    @Override
    public Student getBestStudent(TreeSet<Grade> grades) {

        Student aux = null;
        Double max = 0.0;

        for(Grade grade : grades) {
            if (grade.getPartialScore() > max) {
                aux = grade.getStudent();
                max = grade.getPartialScore();
            }
        }

        return aux;
    }
}
