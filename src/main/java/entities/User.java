package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Entity
@Table(name="users")
public class User {
    //attributes
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private long id;
    private String name;
    private String surname;
    private int age;
    @OneToOne(mappedBy = "user")
    private Card card;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();
    //constructors

    public User() {
    }

    public User(String name, String surname, int age) {
        this.setName(name);
        this.setSurname(surname);
        this.setAge(age);
    }

    //getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public Card getCard() {
        return card;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }


    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    //methods


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }



}
