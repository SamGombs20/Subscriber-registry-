package bean;

import dao.SubscriberDAOImplementation;
import model.Subscriber;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class SubscriberBean {
    private SubscriberDAOImplementation daoImplementation;
    private List<Subscriber> subscribers;
    @PostConstruct
    public void init(){
        daoImplementation = new SubscriberDAOImplementation();
        subscribers = daoImplementation.allSubscribers();
    }
    public List<Subscriber> getSubscribers(){
        return subscribers;
    }


}
