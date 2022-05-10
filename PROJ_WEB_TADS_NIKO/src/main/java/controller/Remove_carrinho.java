package controller;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.StringTokenizer;

public class Remove_carrinho {

    @GetMapping("/removerCarrinho")
    public void remover_item(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        boolean e_logado = false;
        var writer = response.getWriter();
        try {
            e_logado = session.getAttribute("e_logado").equals(true);
        } catch (NullPointerException e) {

        }

        if (e_logado) {
            var contain_token = "";
            var id_rem = request.getParameter("id");
            boolean achouCarrinho = false;

            Cookie[] requestCookies = request.getCookies();
            Cookie carrinho = new Cookie("carrinho", "");

            if (requestCookies != null) {
                for (var item : requestCookies) {
                    if (item.getName().equals("carrinho")) {
                        achouCarrinho = true;
                        carrinho = item;
                        break;
                    }
                }
            }

            if (achouCarrinho) {
                StringTokenizer tokenizer = new StringTokenizer(carrinho.getValue(), "|");
                var elem_id = "";


                while (tokenizer.hasMoreTokens()) {

                    elem_id = tokenizer.nextToken();

                    if (elem_id != id_rem) {

                        contain_token += elem_id + "|";
                    }
                }
                carrinho.setValue(contain_token);

                response.addCookie(carrinho);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/carrinho");
                dispatcher.forward(request, response);

            } else {
                writer.println("<p> produto não encontrado!</p>");
            }

        } else
            response.sendRedirect("/index.html");
    }
}

