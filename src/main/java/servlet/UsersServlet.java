package servlet;

import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/users")
public class UsersServlet extends HttpServlet {

    private UserManager userManager = new UserManager();
    private EventManager eventManager = new EventManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userManager.getAll();
        for (User user : userList) {
            List<Event> events = eventManager.getEventsByUserId(user.getId());
            user.setEvents(events);
        }
        req.setAttribute("users", userList);
        req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
    }
}
