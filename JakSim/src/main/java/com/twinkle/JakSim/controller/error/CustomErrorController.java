package com.twinkle.JakSim.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String errorHandle(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String errorPath = "/error/";

        if(status != null){
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.FORBIDDEN.value()){
                errorPath += "forbidden";
            }else if(statusCode == HttpStatus.NOT_FOUND.value()){
                errorPath += "notfound";
            }else{
                errorPath += "error";
            }
        }

        return errorPath;
    }

}
