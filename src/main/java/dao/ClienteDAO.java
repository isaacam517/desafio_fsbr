package dao;

import model.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class ClienteDAO {

    private EntityManager em;

    public ClienteDAO() {
        em = Persistence.createEntityManagerFactory("clientePU").createEntityManager();
    }

    public void salvarCliente(Cliente cliente) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(cliente); // Persiste o cliente
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

    public List<Cliente> listarClientes() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public void removerCliente(Cliente cliente) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(cliente) ? cliente : em.merge(cliente)); // Remove o cliente
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }
}
