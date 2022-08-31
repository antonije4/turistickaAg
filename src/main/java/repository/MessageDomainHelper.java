package repository;

import com.querydsl.jpa.impl.JPAQuery;
import entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class MessageDomainHelper implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QMessage qMessage = QMessage.message;
    private static final QInbox qInbox = QInbox.inbox;

    public void createMessage(Message message) {
        entityManager.persist(message);
    }

    public void updateMessage(Message message) {
        entityManager.merge(message);
    }

    public void createInbox(Inbox inbox) {
        entityManager.persist(inbox);
    }

    public void updateInbox(Inbox inbox) {
        entityManager.merge(inbox);
    }

    public boolean inboxHasUnreadMessages(long inboxId) {
        JPAQuery<Message> query = new JPAQuery<>(entityManager).select(qMessage)
                .from(qMessage)
                .where(qMessage.inbox.id.eq(inboxId).and(qMessage.read.eq(false)));
        return !query.fetch().isEmpty();
    }

    public List<Message> fetchMessagesFromInbox(long inboxId) {
        JPAQuery<Message> query = new JPAQuery<>(entityManager).select(qMessage)
                .from(qMessage)
                .where(qMessage.inbox.id.eq(inboxId));
        return query.fetch();
    }
}
