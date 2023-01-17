public class Student extends User{

    private Parent mother;
    private Parent father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public String getFullName(){
        return this.getFirstName() + " " + this.getLastName();
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother(){
        return mother;
    }

    public Parent getFather(){
        return father;
    }

    public boolean compareStudents(Student student) {
        if(!(this.getFirstName() + " " + this.getLastName())
                .equals(student.getFirstName() + " " + student.getLastName())) {
            return false;
        }

        if(this.getMother() == null && student.getMother() != null)
            return false;
        if(this.getFather() == null && student.getFather() != null)
            return false;
        if(this.getMother() != null && student.getMother() == null)
            return false;
        if(this.getFather() != null && student.getFather() == null)
            return false;

        if(this.getFather() != null) {
            String father = this.getFather().getFirstName() + " " + this.getFather().getLastName();
            if (!father.equals(student.getFather().getFirstName() + " " + student.getFather().getLastName()))
                return false;
        }
        if(this.getMother() != null) {
            String mother = this.getMother().getFirstName() + " " + this.getMother().getLastName();
            if (!mother.equals(student.getMother().getFirstName() + " " + student.getMother().getLastName()))
                return false;

        }
        return true;
    }

}
