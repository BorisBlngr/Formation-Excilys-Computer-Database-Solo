package com.formation.cdb.webControllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.formation.cdb.service.CompanyService;
import com.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
@RestController("webServices")
public class WebServices {
    private static final Logger LOG = LoggerFactory.getLogger(WebServices.class);
    private final String regex = "\\d+";
    
    @Autowired
    CompanyService companyService;
    @Autowired
    ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebServices() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response).
     * @param request Request
     * @param response Response
     * @throws ServletException ServletException.
     * @throws IOException IOException.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/views/jsp/login.jsp");
        view.forward(request, response);
    }

  
}
