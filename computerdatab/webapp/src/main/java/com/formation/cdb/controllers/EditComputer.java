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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller("editcomputer")
public class EditComputer {
    private static final Logger LOG = LoggerFactory.getLogger(EditComputer.class);
    private static final long serialVersionUID = 1L;
    private final String regex = "\\d+";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    CompanyService companyService;
    @Autowired
    ComputerService computerService;

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
    @RequestMapping(value = "/editcomputer", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Get");

        long id = getId(request);

        ComputerDto computerDto = new ComputerDto();
        if (id != 0) {
            computerDto = computerService.findComputerDto(id);
        }

        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        companyDtoList = companyService.findAllCompany();

        request.setAttribute("computerDto", computerDto);
        request.setAttribute("companyDtoList", companyDtoList);

        RequestDispatcher view = request.getRequestDispatcher("/views/jsp/editComputer.jsp");
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response).
     * @param request Request
     * @param response Response
     * @throws ServletException ServletException.
     * @throws IOException IOException.
     */
    @RequestMapping(value = "/editcomputer", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        LOG.debug("Post");
        LOG.debug(request.getParameterMap().keySet().toString());

        String name = getNameAndValidate(request);
        LocalDate introduced = getIntroduced(request);
        LocalDate discontinued = getDiscontinued(request);
        String discontinuedStr;
        String introducedStr;
        if (introduced == null) {
            introducedStr = "";
        } else {
            introducedStr = introduced.toString();
        }
        if (discontinued == null) {
            discontinuedStr = "";
        } else {
            discontinuedStr = introduced.toString();
        }
        long companyId = getCompanyId(request);
        long computerId = getComputerId(request);

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder().id(computerId).name(name)
                .introduced(introducedStr).discontinued(discontinuedStr)
                .company(new Company.CompanyBuilder().id(companyId).build()).build();

        RequestDispatcher view;
        if (!computerDto.equals(new ComputerDto())) {
            computerService.updateComputer(computerDto);
            view = request.getRequestDispatcher("/views/jsp/editComputerSuccess.jsp");
        } else {
            view = request.getRequestDispatcher("/views/jsp/500.jsp");
        }
        view.forward(request, response);
    }

    /**
     * @param request The request.
     * @return id
     */
    private long getId(HttpServletRequest request) {
        long id = 0;
        if (request.getParameterMap().containsKey("id") && request.getParameter("id").matches(regex)) {
            id = Long.parseLong(request.getParameter("id"));
        }
        return id;
    }

    /**
     * @param request The request.
     * @return computerId
     */
    private long getComputerId(HttpServletRequest request) {
        long computerId = 0;
        if (request.getParameterMap().containsKey("id")) {
            computerId = Long.parseLong(request.getParameter("id"));
        }
        return computerId;
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
