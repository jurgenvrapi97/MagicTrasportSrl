package org.example;

import dao.UsersDAO;
import entities.Card;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {

private final static EntityManagerFactory emf= Persistence.createEntityManagerFactory("MagicTrasportSrl");

    public static void main(String[] args) {
      EntityManager em=emf.createEntityManager();
      
    }
}
