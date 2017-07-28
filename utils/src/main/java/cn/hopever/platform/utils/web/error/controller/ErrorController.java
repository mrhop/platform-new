package cn.hopever.platform.utils.web.error.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Donghui Huo on 2015/12/29.
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends BasicErrorController {

    Logger logger = LoggerFactory.getLogger(ErrorController.class);

    public ErrorController(List<ErrorViewResolver> errorViewResolvers) {
        super(new DefaultErrorAttributes(),
                new ErrorProperties(), errorViewResolvers);
    }

    @Override
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        logger.error("mvc error code:" + status, request.getAttribute("javax.servlet.error.exception"));
        try {
            response.sendRedirect(request.getContextPath() + "/error/" + status + ".html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
