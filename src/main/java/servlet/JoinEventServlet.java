package servlet;

import manager.EventManager;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/events/join")
public class JoinEventServlet extends HttpServlet {

    private EventManager eventManager = new EventManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int eventId = Integer.parseInt(req.getParameter("eventId"));
        User user = (User) req.getSession().getAttribute("user");
        eventManager.join(eventId, user.getId());
        resp.sendRedirect("/events");
    }
}
