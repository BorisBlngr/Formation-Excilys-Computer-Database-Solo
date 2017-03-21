package com.formation.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.formation.cdb.model.Company;
import com.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/addcomputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Company> companyList = new ArrayList<Company>();
        companyList = ComputerService.INSTANCE.findAllCompany();

        request.setAttribute("companyList", companyList);

        RequestDispatcher view = request.getRequestDispatcher("/views/jsp/addComputer.jsp");
        view.forward(request, response);

        // response.getWriter().append("Served at:
        // ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response).
     * @param request Request
     * @param response Response
     * @throws ServletException ServletException.
     * @throws IOException IOException.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
