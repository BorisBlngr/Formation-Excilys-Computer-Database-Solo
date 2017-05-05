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

import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.service.CompanyService;

/**
 * Servlet implementation class Dashboard.
 */
@RestController("companyWebController")
public class CompanyWebController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyWebController.class);
    private final String regex = "\\d+";

    @Autowired
    CompanyService companyService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyWebController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    protected CompanyDto getComany(@PathVariable long id) throws ServletException, IOException {
        return companyService.findOne(id);
    }

}
