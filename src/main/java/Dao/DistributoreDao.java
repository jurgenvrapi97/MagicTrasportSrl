package Dao;

import entities.Distributore;
import entities.Ticket;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DistributoreDao {
    private final EntityManager em;

    public DistributoreDao(EntityManager em) {
        this.em = em;
    }

    //methods
    public void saveDistributore(Distributore distributore) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(distributore);
        transaction.commit();
        System.out.println("Distributore " + distributore.getId() + " salvato");
    }
    public Distributore getById(int distributore_id){
        Distributore distributore=em.find(Distributore.class,distributore_id);
        if(distributore.equals(null))throw new RuntimeException();
        else{
            System.out.println("trovato distributore: "+distributore.toString());
            return distributore;
        }
    }
    public void delete(int distributore_id){
        Distributore distributoreToDelete=this.getById(distributore_id);
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.remove(distributoreToDelete);
        transaction.commit();
        System.out.println("distributore"+ distributoreToDelete.toString()+" eliminato");
    }
    public List<Ticket> findBigliettiEmessiByLocation(String location){
        TypedQuery<Ticket> query=em.createNamedQuery("findBigliettiEmessiByLocation", Ticket.class);
        query.setParameter("location",location);
        List <Ticket>foundTickets=query.getResultList();
        System.out.println("biglietti emessi da distributore in"+location+" :"+foundTickets);
        return foundTickets;
    }
}
