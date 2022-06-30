package repository;


import com.querydsl.jpa.impl.JPAQuery;
import entities.Client;
import entities.QClient;
import util.JPAHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ClientRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public Client getClientByEmail(String email) {
        Query query = entityManager.createQuery("select c from Client c where c.email=:email");
        query.setParameter("email", email);
        return JPAHelper.getUniqueResult(Client.class, query);
    }

    public Client getClientByUsername(String username) {
        Query query = entityManager.createQuery("select c from Client c where c.username=:username");
        query.setParameter("username", username);
        return JPAHelper.getUniqueResult(Client.class, query);
    }

    public Client getClientByUsernamePassword(String username, String password) {
        Query query = entityManager.createQuery("select c from Client c where c.username=:username and c.password=:password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return JPAHelper.getUniqueResult(Client.class, query);
    }

    public void updateClient(Client client) {
        entityManager.persist(client);
    }

    public void createClient(Client client) {
        entityManager.persist(client);
    }
}
