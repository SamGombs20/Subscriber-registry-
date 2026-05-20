package dao;

import model.Subscriber;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubscriberDAOImplementation implements SubscriberDao {

    @Override
    public void save(Subscriber subscriber) {
        String sql ="INSERT INTO subscribers (full_name, phone_number, status) VALUES (?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,subscriber.getFullName());
            stmt.setString(2,subscriber.getPhoneNumber());
            stmt.setString(3, subscriber.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Subscriber> allSubscribers() {
        List<Subscriber> subscribers = new ArrayList<>();
        String query = "SELECT * FROM subscribers";
        try(Connection conn= DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery()) {
            while (result.next()){
                subscribers.add(mapRowToSubscriber(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscribers;
    }

    @Override
    public void update(Subscriber subscriber) {
        String query = "UPDATE subscribers SET full_name = ?, phone_number = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,subscriber.getFullName());
            stmt.setString(2,subscriber.getPhoneNumber());
            stmt.setString(3, subscriber.getStatus());
            stmt.setInt(4,subscriber.getId());

            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM subscribers WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean doesPhoneNumberExist(String phoneNumber, Integer excludeId) {
        String query = excludeId==null?"SELECT COUNT(*) FROM subscribers WHERE phone_number = ?":
                "SELECT COUNT(*) FROM subscribers WHERE phone_number = ? AND id != ?";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber.trim());
            if (excludeId!=null){
                stmt.setInt(2,excludeId);
            }
            try(ResultSet resultSet = stmt.executeQuery()) {
                if(resultSet.next()){
                    return resultSet.getInt(1)>0;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private Subscriber mapRowToSubscriber(ResultSet resultSet) throws SQLException{
        Subscriber sub = new Subscriber();
        sub.setId(resultSet.getInt("id"));
        sub.setFullName(resultSet.getString("full_name"));
        sub.setPhoneNumber(resultSet.getString("phone_number"));
        sub.setStatus(resultSet.getString("status"));
        return sub;
    }
}
