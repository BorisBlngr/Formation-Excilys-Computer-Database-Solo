package com.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/addcomputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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
        System.out.println("Post, I will do things soon ...");

        String name = null;
        LocalDate introduced = null;
        LocalDate discontinued = null;
        long companyId = 0;

        // System.out.println(request.getParameterMap().keySet());

        if (request.getParameterMap().containsKey("computerName") && !request.getParameter("computerName").isEmpty()) {
            name = request.getParameter("computerName");
            // name = name.replaceAll("<", "");
            // name = name.replaceAll(">", "");
            // System.out.println(name);
        }
        if (request.getParameterMap().containsKey("introduced") && !request.getParameter("introduced").isEmpty()) {
            // System.out.println(request.getParameter("introduced"));
            introduced = LocalDate.parse(request.getParameter("introduced"), formatter);
        }
        if (request.getParameterMap().containsKey("discontinued") && !request.getParameter("discontinued").isEmpty()) {
            // System.out.println(request.getParameter("discontinued"));
            discontinued = LocalDate.parse(request.getParameter("discontinued"), formatter);
        }
        if (request.getParameterMap().containsKey("companyId")) {
            companyId = Long.parseLong(request.getParameter("companyId"));
            // System.out.println(companyId);
        }
        ComputerDto computerDto = new ComputerDto.ComputerBuilder().name(name).introduced(introduced)
                .discontinued(discontinued).company(new Company.CompanyBuilder().id(companyId).build()).build();

        // System.out.println(computerDto);
        // System.out.println(new ComputerDto());
        RequestDispatcher view;
        if (!computerDto.equals(new ComputerDto())) {
            long id = ComputerService.INSTANCE.createComputer(computerDto);
            System.out.println(id);
            view = request.getRequestDispatcher("/views/jsp/addComputerSuccess.html");
        } else {
            view = request.getRequestDispatcher("/views/jsp/500.html");
        }

        view.forward(request, response);
        // response.sendRedirect("/dashboard");
    }

}
