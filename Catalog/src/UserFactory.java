public class UserFactory {

    public static User getUser(String userType, String firstName, String lastName) {

        if (userType == null)
            return null;

        if (userType.equalsIgnoreCase("Parent"))
            return new Parent(firstName, lastName);

        if (userType.equalsIgnoreCase("Student"))
            return new Student(firstName, lastName);

        if (userType.equalsIgnoreCase("Teacher"))
            return new Teacher(firstName, lastName);

        if (userType.equalsIgnoreCase("Assistant"))
            return new Assistant(firstName, lastName);

        return null;
    }
}
