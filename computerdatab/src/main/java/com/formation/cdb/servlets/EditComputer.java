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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.CompanyService;
import com.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/editcomputer")
public class EditComputer extends HttpServlet {
    final Logger logger = LoggerFactory.getLogger(EditComputer.class);
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
        logger.debug("Get");

        long id = getId(request);

        ComputerDto computerDto = new ComputerDto();
        if (id != 0) {
            computerDto = ComputerService.INSTANCE.findComputerDto(id);
        }

        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        companyDtoList = CompanyService.INSTANCE.findAllCompany();

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        logger.debug("Post");
        logger.debug(request.getParameterMap().keySet().toString());

        String name = getNameAndValidate(request);
        LocalDate introduced = getIntroduced(request);
        LocalDate discontinued = getDiscontinued(request);
        long companyId = getCompanyId(request);
        long computerId = getComputerId(request);

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder().id(computerId).name(name).introduced(introduced)
                .discontinued(discontinued).company(new Company.CompanyBuilder().id(companyId).build()).build();

        RequestDispatcher view;
        if (!computerDto.equals(new ComputerDto())) {
            ComputerService.INSTANCE.updateComputer(computerDto);
            view = request.getRequestDispatcher("/views/jsp/editComputerSuccess.html");
        } else {
            view = request.getRequestDispatcher("/views/jsp/500.html");
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
