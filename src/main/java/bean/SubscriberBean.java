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
    private Integer selectedSubId;
    private String fullName;
    private String phoneNumber;
    private String status;
    private Integer idToDelete;
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

    public Integer getSelectedSubId() {
        return this.selectedSubId;
    }

    public void setSelectedSubId(final Integer selectedSubId) {
        this.selectedSubId = selectedSubId;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
    public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String phone = (value != null) ? value.toString().trim() : "";
        if (!phone.matches("^254[0-9]{9}$")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Format", "Number must be 254XXXXXXXXX"));
        }
        if(daoImplementation.doesPhoneNumberExist(phone,selectedSubId)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Duplicate Found", "This phone number is already registered."));
        }
    }

    public void addSubscriber(){
        Subscriber newSub = new Subscriber();

        newSub.setFullName(fullName);
        newSub.setPhoneNumber(phoneNumber);
        newSub.setStatus(status);
        if(selectedSubId!=null){
            newSub.setId(selectedSubId);
            daoImplementation.update(newSub);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Success", "Subscriber updated successfully!"));
        }
        else {
            daoImplementation.save(newSub);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Success","Subscriber added successfully!"));
        }

        loadSubscribers();
        newSubscriber();

    }

    public void deleteSubscriber(){
        if(idToDelete!=null){
            daoImplementation.delete(idToDelete);
            loadSubscribers();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Success", "Subscriber deleted successfully!"));
            idToDelete=null;
        }
    }
    public void editSubscriber(Subscriber sub){
        selectedSubId = sub.getId();
        fullName=sub.getFullName();
        phoneNumber=sub.getPhoneNumber();
        status=sub.getStatus();
    }
    public void newSubscriber(){
        this.selectedSubId=null;
        this.fullName=null;
        this.status=null;
        this.phoneNumber=null;
    }
    public void initiateSubDelete(Integer id){
        idToDelete=id;
    }
}
