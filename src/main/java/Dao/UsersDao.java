package Dao;

import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UsersDao {
    EntityManager em;

    public UsersDao(EntityManager em) {
        this.em = em;
    }

    //methods
    public void save(User user){
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.persist(user);
        transaction.commit();
        System.out.println("user"+ user.toString()+" salvato");
    }
    public User getById(long user_id){
      User user=em.find(User.class,user_id);
        if(user.equals(null))throw new RuntimeException();
        else{
            System.out.println(user.toString());
            return user;
        }
    }
    public void delete(long user_id){
       User userToDelete=this.getById(user_id);
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.remove(userToDelete);
        transaction.commit();
        System.out.println("user"+ userToDelete.toString()+" eliminato");
    }
}
