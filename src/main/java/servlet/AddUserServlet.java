package servlet;

import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.User;
import model.UserRole;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/users/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddUserServlet extends HttpServlet {

    private EventManager eventManager = new EventManager();
    private UserManager userManager = new UserManager();

    private static final String IMAGE_PATH = "/Users/karen/data/lessons/Java 2022/EventRegisterWeb/projectImages/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Event> all = eventManager.getAll();
//        request.setAttribute("events", all);
        request.getRequestDispatcher("/WEB-INF/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if(userManager.getUserByEmail(email) != null){
            request.setAttribute("msg", "User already exists");
            request.getRequestDispatcher("/WEB-INF/addUser.jsp").forward(request,response);
        }else {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String password = request.getParameter("password");
            UserRole userRole = UserRole.valueOf(request.getParameter("user_role"));
//        int eventId = Integer.parseInt(request.getParameter("eventId"));

            Part profilePicPart = request.getPart("profilePic");

            String fileName = null;
            if(profilePicPart != null) {
                long nanoTime = System.nanoTime();
                fileName = nanoTime+ "_" + profilePicPart.getSubmittedFileName();
                profilePicPart.write(IMAGE_PATH + fileName);

            }


            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .userRole(userRole)
//                .event(eventManager.getById(eventId))
                    .profilePic(fileName)
                    .build();

            userManager.add(user);
            response.sendRedirect("/login");
        }

    }
}
