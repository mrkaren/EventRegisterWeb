package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;
import model.User;
import model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private Connection connection = DBConnectionProvider.getINSTANCE().getConnection();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void add(Event event) {
        String sql = "insert into event(name,place,is_online,event_type,price,event_date) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getName());
            ps.setString(2, event.getPlace());
            ps.setBoolean(3, event.isOnline());
            ps.setString(4, event.getEventType().name());
            ps.setDouble(5, event.getPrice());
            ps.setString(6, sdf.format(event.getEventDate()));
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                event.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void join(int eventId, int userId) {
        String sql = "insert into user_event(user_id,event_id) VALUES (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setInt(2, eventId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Event> getAll() {
        String sql = "select * from event";
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getById(int id) {
        String sql = "select * from event where id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return getEventFromResultSet(resultSet);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeEventById(int id) {
        String sql = "delete from event where id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Event getEventFromResultSet(ResultSet resultSet) throws SQLException, ParseException {

        Event event = Event.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .place(resultSet.getString("place"))
                .isOnline(resultSet.getBoolean("is_online"))
                .eventType(EventType.valueOf(resultSet.getString("event_type")))
                .price(resultSet.getDouble("price"))
                .eventDate(resultSet.getString("event_date") == null ? null : sdf.parse(resultSet.getString("event_date")))
                .build();


        return event;
    }


    public void cancel(int eventId, int userId) {
        String sql = "delete from user_event where event_id = ? AND user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, eventId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getEventsByUserId(int userId) {
        String userEventSql = "Select event_id from user_event where user_id = ?";
        PreparedStatement ps = null;
        List<Event> events = new ArrayList<>();
        try {
            ps = connection.prepareStatement(userEventSql);
            ps.setInt(1, userId);
            ResultSet eventsResultSet = ps.executeQuery();
            while (eventsResultSet.next()) {
                events.add(getById(eventsResultSet.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Event> search(String keyword) {
        String sql = "select * from event where name like '%" + keyword + "%' OR place like '%" + keyword + "%'";
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<Event> filter(double minPrice, double maxPrice) {
        String sql = "select * from event where price > " + minPrice + " and price < "+ maxPrice;
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;

    }
}
