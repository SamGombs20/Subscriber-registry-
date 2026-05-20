package dao;

import model.Subscriber;

import java.util.List;

public interface SubscriberDao {
    void save(Subscriber subscriber);
    List<Subscriber> allSubscribers();
    void update(Subscriber subscriber);
    void delete(Integer id);
    boolean doesPhoneNumberExist(String phoneNumber, Integer excludeId);
}
