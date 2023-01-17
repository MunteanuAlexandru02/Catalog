public class Grade implements Comparable, Cloneable {

    private Double partialScore;
    private Double examScore;
    private Student student;
    private String course;

    public Grade(){}

    public Grade (String course, Student student, Double partialScore, Double examScore) {
        this.course = course;
        this.student = student;
        this.partialScore = partialScore;
        this.examScore = examScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public Student getStudent() {
        return student;
    }

    public String getCourse() {
        return course;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    /*Voi compara prima data nota de la examen, iar apoi nota partiala*/
    public int compareTo(Object o) {
        Grade grade = (Grade) o;

        if(this.getStudent().getFullName().compareTo(grade.getStudent().getFullName()) == 0
            && this.getCourse().compareTo(grade.getCourse()) == 0)
            return 0;


        if(Double.compare(this.examScore, grade.examScore) == 0) {
            if(Double.compare(this.examScore, grade.examScore) == 0) {
                if (this.getStudent().getFullName().compareTo(grade.getStudent().getFullName()) == 0) {
                    if (this.getCourse().compareTo(grade.getCourse()) == 0) {
                        return 0;
                    }
                    else {
                        return this.getCourse().compareTo(grade.getCourse());
                    }
                }
                else {
                    return this.getStudent().getFullName().compareTo(grade.getStudent().getFullName());
                }
            }
            else {
                return Double.compare(this.examScore, grade.examScore);
            }
        }
        else {
            return Double.compare(this.examScore, grade.examScore);
        }

        /*if (Double.compare(this.examScore, grade.examScore) == 0) {
            return Double.compare(this.partialScore, grade.partialScore);
        } else {
            return Double.compare(this.examScore, grade.examScore);
        }*/

    }

    @Override
    public String toString() {
        return "Grade{" +
                "partialScore=" + partialScore +
                ", examScore=" + examScore +
                ", student=" + student +
                ", course='" + course + '\'' +
                '}';
    }

    public Object clone(){
        Grade clone = new Grade();
        clone.setCourse(course);
        clone.setStudent(student);
        clone.setPartialScore(partialScore);
        clone.setExamScore(examScore);

        return clone;
    }

    public Double getTotal() {
        return partialScore + examScore;
    }
}
