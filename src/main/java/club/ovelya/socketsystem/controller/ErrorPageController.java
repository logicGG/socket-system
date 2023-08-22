package club.ovelya.socketsystem.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class ErrorPageController implements ErrorController {

    @RequestMapping(produces = {"text/html"})
    public String errorHtmlPage(HttpServletRequest request, HttpServletResponse response,
                                Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        switch (statusCode) {
            case 404 -> {
                return "404.html";
            }
            case 401 -> {
                return "401.html";
            }
        }
        return "400.html";
    }
}
