package edu.web.cms.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by Max on 22.11.2018.
 */
@Controller
@RequestMapping("/errors")
public class ErrorController {

    @RequestMapping("")
    public String getErrorPage(Map<String, Object> model){
        model.put("message", "Oooops... Page not found");
        return "error";
    }
}
