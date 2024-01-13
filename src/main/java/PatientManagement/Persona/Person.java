package PatientManagement.Persona;

import PatientManagement.Patient.Patient;
import java.util.ArrayList;

public class Person {

    String id;
    String name;
   
    Person mother;
    Person father;
    ArrayList<Person> siblings;
    Patient patient;
    int age;

    public Person(String id, String name, int a) {
        this.id = id;
        this.name = name;
        siblings = new ArrayList<Person>();
        age = a;
    }

    public String getPersonId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setFather(Person f) {
        father = f;
    }

    public void setMother(Person m) {
        mother = m;
    }

    public void addSibling(Person s) {
        if (siblings.contains(s))
            return; // sibling already in the arraylist
        siblings.add(s);
    }

    public boolean isMatch(String id) {
        if (getPersonId().equals(id))
            return true;
        return false;
    }

    public int getAge() {
        return age;
    }

    
}
