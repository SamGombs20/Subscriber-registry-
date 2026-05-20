package bean;

import dao.SubscriberDAOImplementation;
import model.Subscriber;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "subscriberBean")
@ViewScoped
public class SubscriberBean implements Serializable {

    private SubscriberDAOImplementation daoImplementation;
    private List<Subscriber> subscribers;
    private String fullName;
    private String phoneNumber;
    private String status;
    @PostConstruct
    public void init(){
        daoImplementation=new SubscriberDAOImplementation();
        loadSubscribers();
    }
    public void loadSubscribers(){
        subscribers = daoImplementation.allSubscribers();
    }
    public List<Subscriber> getSubscribers(){
        return subscribers;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
    public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException{
        String phone = (String) value;
        if(phone==null|| !phone.startsWith("254")|| phone.length()!=12){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid phone number","Must start with 254 and be exactly 12 digits");
            throw new ValidatorException(msg);
        }
    }
    public void addSubscriber(){
        Subscriber newSub = new Subscriber();

        newSub.setFullName(fullName);
        newSub.setPhoneNumber(phoneNumber);
        newSub.setStatus(status);
        daoImplementation.save(newSub);
        loadSubscribers();
        fullName=null;
        status=null;
        phoneNumber=null;
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Success","Subscriber added successfully!"));
    }
    public void deleteSubscriber(int id){
        daoImplementation.delete(id);
        loadSubscribers();
    }
}
