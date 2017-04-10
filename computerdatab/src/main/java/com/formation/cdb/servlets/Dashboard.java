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

import org.apache.commons.lang3.ArrayUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.ComputerService;
import com.formation.cdb.ui.Page;
import com.formation.cdb.util.Order;
import com.formation.cdb.util.Search;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    final Logger logger = LoggerFactory.getLogger(HttpServlet.class);
    private static final long serialVersionUID = 1L;
    private final String regex = "\\d+";
    private final String selectionSplitter = ",";
    private int[] maxsInPage = {10, 50, 100};

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
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
        logger.info(request.getParameterMap().keySet().toString());
        int pageIndex = 1;
        if (request.getParameterMap().containsKey("page") && request.getParameter("page").matches(regex)) {
            pageIndex = Integer.parseInt(request.getParameter("page"));
        }
        if (pageIndex <= 0) {
            pageIndex = 1;
        }

        int maxInPage = 10;
        if (request.getParameterMap().containsKey("maxInPage") && request.getParameter("maxInPage").matches(regex)) {
            maxInPage = Integer.parseInt(request.getParameter("maxInPage"));
            if (!ArrayUtils.contains(maxsInPage, maxInPage)) {
                maxInPage = 10;
            }
        }
        String search = "";
        if (request.getParameterMap().containsKey("search")) {
            search = request.getParameter("search");
        }

        String searchBy = "";
        if (request.getParameterMap().containsKey("searchBy")
                && request.getParameter("searchBy").equals(Search.COMPANIES.toString())) {
            searchBy = Search.COMPANIES.toString();
        } else {
            searchBy = Search.COMPUTERS.toString();
        }
        Order order;
        if (request.getParameterMap().containsKey("order")
                && request.getParameter("order").equals(Order.DESC.toString())) {
            order = Order.DESC;
        } else {
            order = Order.ASC;
        }

        Search filterBy;
        if (request.getParameterMap().containsKey("filterBy")
                && request.getParameter("filterBy").equals(Search.COMPANIES.toString())) {
            filterBy = Search.COMPANIES;
        } else {
            filterBy = Search.COMPUTERS;
        }

        int nbComputer = 0;
        if (search.isEmpty()) {
            nbComputer = ComputerService.INSTANCE.getNbComputers();
        } else if (Search.COMPUTERS.equalsName(searchBy)) {
            nbComputer = ComputerService.INSTANCE.getNbComputersSearchName(search);
        } else if (Search.COMPANIES.equalsName(searchBy)) {
            nbComputer = ComputerService.INSTANCE.getNbComputersSearchCompanyName(search);
        }

        int maxPage = nbComputer / maxInPage;
        if (nbComputer != 0 && maxInPage % nbComputer != 0) {
            maxPage--;
        }

        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        if (search.isEmpty()) {
            computerDtoList = ComputerService.INSTANCE.findComputersInRangeSearchName(pageIndex, maxInPage, "",
                    filterBy, order);

        } else {
            if (Search.COMPANIES.equalsName(searchBy)) {
                computerDtoList = ComputerService.INSTANCE.findInRangeSearchCompanyName(pageIndex, maxInPage, search,
                        filterBy, order);
            } else {
                computerDtoList = ComputerService.INSTANCE.findComputersInRangeSearchName(pageIndex, maxInPage, search,
                        filterBy, order);
            }
        }

        List<Page> pageList = constructionPageChoices(pageIndex, maxPage);

        // System.out.println(pageIndex + " " + maxPage);
        // System.out.println(pageList);

        request.setAttribute("computerDtoList", computerDtoList);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("maxInPage", maxInPage);
        request.setAttribute("pageList", pageList);
        request.setAttribute("nbComputer", nbComputer);
        request.setAttribute("search", search);
        request.setAttribute("searchBy", searchBy);
        request.setAttribute("order", order.toString());
        request.setAttribute("filterBy", filterBy.toString());

        RequestDispatcher view = request.getRequestDispatcher("/views/jsp/dashboard.jsp");
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
        // System.out.println(request.getParameterMap().keySet());
        String selection = "";

        if (request.getParameterMap().containsKey("selection") && !request.getParameter("selection").isEmpty()) {
            selection = request.getParameter("selection");
            // System.out.println(selection);
        }

        String[] idToDeleteStr = selection.split(selectionSplitter);
        List<Long> idToDelete = new ArrayList<Long>();
        for (String id : idToDeleteStr) {
            if (id.matches(regex)) {
                idToDelete.add(Long.parseLong(id));
            }
        }
        System.out.println(idToDelete);
        doGet(request, response);

        for (Long id : idToDelete) {
            ComputerService.INSTANCE.deleteComputer(id);
        }
    }

    /**
     * Construction of the page selector.
     * @param indexPage Index page.
     * @param maxPage Max page.
     * @return pageList
     */
    public List<Page> constructionPageChoices(int indexPage, int maxPage) {
        List<Page> pageList = new ArrayList<Page>();
        Page page;

        if (maxPage < 5) {
            for (int i = 1; i <= maxPage; i++) {
                page = new Page(i, false);
                if (i == indexPage) {
                    page.setActive(true);
                }
                pageList.add(page);
            }
        } else {
            if (indexPage < 3) {
                for (int i = 1; i <= 5; i++) {
                    page = new Page(i, false);
                    if (i == indexPage) {
                        page.setActive(true);
                    }
                    pageList.add(page);
                }
            } else if (indexPage > maxPage - 2) {
                for (int i = maxPage - 4; i <= maxPage; i++) {
                    page = new Page(i, false);
                    if (i == indexPage) {
                        page.setActive(true);
                    }
                    pageList.add(page);
                }
            } else {
                for (int i = indexPage - 2; i <= indexPage + 2; i++) {
                    page = new Page(i, false);
                    if (i == indexPage) {
                        page.setActive(true);
                    }
                    pageList.add(page);
                }
            }
        }
        return pageList;
    }

    /**
     * main.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        Order search = Order.DESC;
        System.out.println(search);
        System.out.println(search.toString());
        System.out.println(search.equalsName("DESC"));

    }
}
