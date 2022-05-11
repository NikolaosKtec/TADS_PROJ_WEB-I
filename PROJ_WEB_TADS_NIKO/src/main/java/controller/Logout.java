package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class Logout {
    @GetMapping("/logout")
    public void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        session.invalidate();

        try{
//            e_logado = session.getAttribute("e_logado").equals(true);
//            e_logista  = session.getAttribute("e_logista").equals(true);
        }catch(NullPointerException e){

        }

        response.sendRedirect("/index.html");

    }
}
