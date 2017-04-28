package com.formation.cdb.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.CompanyService;
import com.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
@Controller("addcomputer")
public class AddComputer {
    private static final long serialVersionUID = 1L;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    CompanyService companyService;
    @Autowired
    ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response).
     * @param request Request
     * @param response Response
     * @throws ServletException ServletException.
     * @throws IOException IOException.
     */
    @RequestMapping(value = "/addcomputer", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        companyDtoList = companyService.findAllCompany();

        request.setAttribute("companyDtoList", companyDtoList);

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
    @RequestMapping(value = "/addcomputer", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Post, I will do things soon ...");

        String name = getNameAndValidate(request);
        LocalDate introduced = getIntroduced(request);
        LocalDate discontinued = getDiscontinued(request);
        long companyId = getCompanyId(request);

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder().name(name).introduced(introduced)
                .discontinued(discontinued).company(new Company.CompanyBuilder().id(companyId).build()).build();

        RequestDispatcher view;

        if (!computerDto.equals(new ComputerDto())) {
            long id = computerService.createComputer(computerDto);
            System.out.println(id);
            view = request.getRequestDispatcher("/views/jsp/addComputerSuccess.jsp");
        } else {
            view = request.getRequestDispatcher("/views/jsp/500.jsp");
        }

        view.forward(request, response);
    }

    /**
     * @param request The request.
     * @return companyId
     */
    private long getCompanyId(HttpServletRequest request) {
        long companyId = 0;
        if (request.getParameterMap().containsKey("companyId")) {
            companyId = Long.parseLong(request.getParameter("companyId"));
        }
        return companyId;
    }

    /**
     * @param request The request.
     * @return discontinued
     */
    private LocalDate getDiscontinued(HttpServletRequest request) {
        LocalDate discontinued = null;
        if (request.getParameterMap().containsKey("discontinued") && !request.getParameter("discontinued").isEmpty()) {
            discontinued = LocalDate.parse(request.getParameter("discontinued"), formatter);
        }
        return discontinued;
    }

    /**
     * @param request The request.
     * @return introduced
     */
    private LocalDate getIntroduced(HttpServletRequest request) {
        LocalDate introduced = null;
        if (request.getParameterMap().containsKey("introduced") && !request.getParameter("introduced").isEmpty()) {
            introduced = LocalDate.parse(request.getParameter("introduced"), formatter);
        }
        return introduced;
    }

    /**
     * @param request The request.
     * @return name
     */
    private String getNameAndValidate(HttpServletRequest request) {
        String name = "";
        if (request.getParameterMap().containsKey("computerName") && !request.getParameter("computerName").isEmpty()) {
            name = request.getParameter("computerName");
        }
        return name;
    }

}
