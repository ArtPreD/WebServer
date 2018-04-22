import accounts.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import accounts.AccountService;
import servlets.SessionsServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class WebServer {

    public static void main(String[] args) throws Exception {
        AccountService accountService = AccountService.getInstance();

        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");
        handler.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        handler.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, handler});

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8080);
        server.setHandler(handlerList);

        server.start();
        System.out.println("WebServer started");
        server.join();
    }
}
