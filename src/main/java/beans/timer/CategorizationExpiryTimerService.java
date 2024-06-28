package beans.timer;

import entities.Sanduce;
import entities.Poruka;
import entities.UgostiteljskiObjekat;
import repository.MessageDomainHelper;
import repository.UgostiteljskiObjekatDomainHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@Startup
public class CategorizationExpiryTimerService implements Serializable {

    @Resource
    private TimerService timerService;

    @Inject
    private MessageDomainHelper messageDomainHelper;

    @Inject
    private UgostiteljskiObjekatDomainHelper ugostiteljskiObjekatDomainHelper;
    private static final String EXPIRY_MESSAGE = "Categorization is expiring in less than 60 days for ugostiteljski objekat: ";

    @PostConstruct
    private void init() {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setPersistent(false);
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.dayOfWeek("1-5").hour("15").minute("42").second("0");
        timerService.createCalendarTimer(scheduleExpression, timerConfig);
    }

    @Timeout
    private void execute() {
        List<UgostiteljskiObjekat> ugostiteljskiObjekatList = ugostiteljskiObjekatDomainHelper.getUgostiteljskiObjekatCategorizationExpiryDateLessThan(LocalDate.now().plusDays(60));
        ugostiteljskiObjekatList
                .forEach(this::addMessageToUgostiteljInbox);
    }

    private void addMessageToUgostiteljInbox(UgostiteljskiObjekat ugostiteljskiObjekat) {
        Sanduce sanduce = ugostiteljskiObjekat.getUgostitelj().getSanduce();
        Poruka poruka = Poruka.builder()
                .sadrzaj(EXPIRY_MESSAGE + ugostiteljskiObjekat.getNaziv())
                .datumPrispeca(LocalDateTime.now())
                .sanduce(sanduce)
                .procitana(false)
                .build();
        messageDomainHelper.createMessage(poruka);
        sanduce.getPoruke().add(poruka);
        messageDomainHelper.updateInbox(sanduce);
        ugostiteljskiObjekat.setNotifikovanOIstekuKategorizacije(true);
        ugostiteljskiObjekatDomainHelper.updateUgostiteljskiObjekat(ugostiteljskiObjekat);
    }
}
