/*
 *  Un parinte o sa verifice (o sa fie notificat)
 *  notele unui copil, ceea ce il face pe acel parinte
 *  un "observer"
 */

import java.util.ArrayList;

public class Parent extends User implements Observer{

    private ArrayList<Notification> notifications;

    public ArrayList<Notification> getNotifications(){
        return notifications;
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }

    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        notifications = new ArrayList<>();
    }


}
