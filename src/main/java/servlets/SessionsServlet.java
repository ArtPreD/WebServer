package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionsServlet extends HttpServlet {
    private final AccountService accountService;

    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    public void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null){
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(json);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    public void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            if (login == null || password == null){
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            UserProfile userProfile = accountService.getUserByLogin(login);
            if (userProfile == null || !userProfile.getPassword().equals(password)){
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            accountService.addSession(request.getSession().getId(), userProfile);
            Gson gson = new Gson();
            String json = gson.toJson(userProfile);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(json);
            response.setStatus(HttpServletResponse.SC_OK);

    }


    public void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile userProfile = accountService.getUserBySessionId(sessionId);

        if(userProfile == null){
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            accountService.deleteSession(sessionId);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
