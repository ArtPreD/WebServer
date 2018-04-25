package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import database.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;
    private final DBService dbService;

    public SignUpServlet(AccountService accountService, DBService dbService) {
        this.accountService = accountService;
        this.dbService = dbService;
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        //String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (login == null || password == null){
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }else {
            //accountService.addNewUser(new UserProfile(login, login, password));
            dbService.addUser(login, password);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Thanks for registration " + login + "!");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
