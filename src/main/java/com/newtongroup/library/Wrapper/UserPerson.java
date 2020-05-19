package com.newtongroup.library.Wrapper;

import com.newtongroup.library.Entity.*;

public class UserPerson {

    private User user;
    private Admin admin;
    private Boss boss;
    private Librarian librarian;
    private Visitor visitor;
    private Person person;

    public UserPerson(User user, Admin admin) {
        this.user = user;
        this.admin = admin;
    }

    public UserPerson(User user, Boss boss) {
        this.user = user;
        this.boss = boss;
    }

    public UserPerson(User user, Librarian librarian) {
        this.user = user;
        this.librarian = librarian;
    }

    public UserPerson(User user, Visitor visitor) {
        this.user = user;
        this.visitor = visitor;
    }

    public UserPerson(Person person) {
        this.person = person;
    }

    public UserPerson() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPersonAsVisitor() {
        this.visitor = new Visitor();
        this.visitor.setFirstName(person.getFirstName());
        this.visitor.setLastName(person.getLastName());
        this.visitor.setPersonalNumber(person.getPersonalNumber());
        this.visitor.setPhone(person.getPhone());
        this.visitor.setCity(person.getCity());
        this.visitor.setPostalCode(person.getPostalCode());
        this.visitor.setStreet(person.getStreet());
        this.visitor.setEmail(user.getUsername());
        this.visitor.setActive(true);
    }

    public void setVisitorAsPerson() {
        this.person = new Person();
        this.person.setFirstName(visitor.getFirstName());
        this.person.setLastName(visitor.getLastName());
        this.person.setPersonalNumber(visitor.getPersonalNumber());
        this.person.setPhone(visitor.getPhone());
        this.person.setCity(visitor.getCity());
        this.person.setPostalCode(visitor.getPostalCode());
        this.person.setStreet(visitor.getStreet());
        this.person.setEmail(visitor.getEmail());
    }

    public void setPersonAsLibrarian() {
        this.librarian = new Librarian();
        this.librarian.setFirstName(person.getFirstName());
        this.librarian.setLastName(person.getLastName());
        this.librarian.setPersonalNumber(person.getPersonalNumber());
        this.librarian.setPhone(person.getPhone());
        this.librarian.setCity(person.getCity());
        this.librarian.setPostalCode(person.getPostalCode());
        this.librarian.setStreet(person.getStreet());
        this.librarian.setEmail(user.getUsername());
    }

    public void setPersonAsAdmin() {
        this.admin = new Admin();
        this.admin.setFirstName(person.getFirstName());
        this.admin.setLastName(person.getLastName());
        this.admin.setPersonalNumber(person.getPersonalNumber());
        this.admin.setPhone(person.getPhone());
        this.admin.setCity(person.getCity());
        this.admin.setPostalCode(person.getPostalCode());
        this.admin.setStreet(person.getStreet());
        this.admin.setEmail(user.getUsername());
    }


}
