package com.formation.cdb.webControllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.formation.cdb.model.dto.ComputerDto;
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

    @RequestMapping(value = "/computer/{computerId}", method = RequestMethod.GET)
    protected ComputerDto getComputer(@PathVariable long computerId) throws ServletException, IOException {
        return computerService.findComputerDto(computerId);
    }

}
