package bean;

import dao.SubscriberDAOImplementation;
import model.Subscriber;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import java.util.List;


@RequestScoped
public class SubscriberBean {
    private SubscriberDAOImplementation daoImplementation;
    private List<Subscriber> subscribers;
    @PostConstruct
    public void init(){
        daoImplementation = new SubscriberDAOImplementation();
        subscribers = daoImplementation.allSubscribers();
    }

}
