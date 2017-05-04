package com.formation.cdb.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Servlet implementation class Dashboard.
 */
@Controller("login")
public class Login {
    private static final Logger LOG = LoggerFactory.getLogger(Login.class);
    private final String regex = "\\d+";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class ResourceNotFoundException extends RuntimeException {
        private static final long serialVersionUID = -4103180427762618619L;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response).
     * @param request Request
     * @param response Response
     * @throws ServletException ServletException.
     * @throws IOException IOException.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    protected void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new ResourceNotFoundException();
    }
}
