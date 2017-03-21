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

import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.ComputerService;
import com.formation.cdb.ui.Page;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String regex = "\\d+";

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
        int pageIndex = 1;
        if (request.getParameterMap().containsKey("page") && request.getParameter("page").matches(regex)) {
            pageIndex = Integer.parseInt(request.getParameter("page"));
        }
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        int maxPage = ComputerService.INSTANCE.getNbComputers() / 10;
        if (10 % ComputerService.INSTANCE.getNbComputers() != 0) {
            maxPage++;
        }

        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        computerDtoList = ComputerService.INSTANCE.findComputersInRange(pageIndex, 10);
        List<Page> pageList = constructionPageChoices(pageIndex, maxPage);

        // System.out.println(pageIndex + " " + maxPage);
        // System.out.println(pageList);

        request.setAttribute("computerDtoList", computerDtoList);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("pageList", pageList);

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
        // TODO Auto-generated method stub
        doGet(request, response);
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

}
