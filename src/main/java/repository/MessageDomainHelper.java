package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.Poruka;
import entities.QPoruka;
import entities.QSanduce;
import entities.Sanduce;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class MessageDomainHelper implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QPoruka qMessage = QPoruka.poruka;
    private static final QSanduce qInbox = QSanduce.sanduce;

    public void createMessage(Poruka poruka) {
        entityManager.persist(poruka);
    }

    public void updateMessage(Poruka poruka) {
        entityManager.merge(poruka);
    }

    public void createInbox(Sanduce sanduce) {
        entityManager.persist(sanduce);
    }

    public void updateInbox(Sanduce sanduce) {
        entityManager.merge(sanduce);
    }

    public boolean inboxHasUnreadMessages(long inboxId) {
        JPAQuery<Poruka> query = new JPAQuery<>(entityManager).select(qMessage)
                .from(qMessage)
                .where(qMessage.sanduce.id.eq(inboxId).and(qMessage.procitana.eq(false)));
        return !query.fetch().isEmpty();
    }

    public List<Poruka> fetchMessagesFromInbox(long inboxId) {
        JPAQuery<Poruka> query = new JPAQuery<>(entityManager).select(qMessage)
                .from(qMessage)
                .where(qMessage.sanduce.id.eq(inboxId));
        return query.fetch();
    }
}
