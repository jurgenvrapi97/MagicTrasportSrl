package org.example;

import dao.UsersDAO;
import entities.Card;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {

///private final static EntityManagerFactory emf= Persistence.createEntityManagerFactory("MagicTrasportSrl");

    public static void main(String[] args) {
//      EntityManager em=emf.createEntityManager();
//        UsersDAO u=new UsersDAO(em);
        User user=new User("elisa","gkjgjkgkjgjkg",100);
        Card card=new Card(LocalDate.now());
        Card card1=new Card(LocalDate.now());
        System.out.println(card.toString());
        System.out.println(user.toString());
        System.out.println(card1.toString());


        System.out.println("Hello World!");
//        em.close();
//        emf.close();
    }
}
