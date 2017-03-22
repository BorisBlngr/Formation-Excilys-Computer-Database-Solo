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
@WebServlet("/editcomputer")
public class EditComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String regex = "\\d+";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
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
        System.out.println("Get");
        System.out.println(request.getParameterMap().keySet());

        long id = 0;
        if (request.getParameterMap().containsKey("id") && request.getParameter("id").matches(regex)) {
            id = Long.parseLong(request.getParameter("id"));
        }
        // System.out.println(id);
        ComputerDto computerDto = new ComputerDto();
        if (id != 0) {
            computerDto = ComputerService.INSTANCE.findComputerDto(id);
        }

        List<Company> companyList = new ArrayList<Company>();
        companyList = ComputerService.INSTANCE.findAllCompany();

        request.setAttribute("computerDto", computerDto);
        request.setAttribute("companyList", companyList);

        //System.out.println(computerDto);
        RequestDispatcher view = request.getRequestDispatcher("/views/jsp/editComputer.jsp");
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
        System.out.println("Post");
        String name = null;
        LocalDate introduced = null;
        LocalDate discontinued = null;
        long companyId = 0;
        long computerId = 0;

        //System.out.println(request.getParameterMap().keySet());

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
        if (request.getParameterMap().containsKey("id")) {
            computerId = Long.parseLong(request.getParameter("id"));
            // System.out.println(companyId);
        }
        ComputerDto computerDto = new ComputerDto.ComputerBuilder().id(computerId).name(name).introduced(introduced)
                .discontinued(discontinued).company(new Company.CompanyBuilder().id(companyId).build()).build();

        // System.out.println(computerDto);
        // System.out.println(new ComputerDto());
        RequestDispatcher view;
        System.out.println(computerDto);
        if (!computerDto.equals(new ComputerDto())) {
            ComputerService.INSTANCE.updateComputer(computerDto);
            view = request.getRequestDispatcher("/views/jsp/editComputerSuccess.html");
        } else {
            view = request.getRequestDispatcher("/views/jsp/500.html");
        }
        view.forward(request, response);
    }

}
