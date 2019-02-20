package org.thinker.oauth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thinker.oauth.AccessTokenVO;
import org.thinker.oauth.TokenSender;

/**
 * Servlet implementation class TwitterCallBackServlet
 */
public class TwitterCallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwitterCallBackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//이곳에 코드를 작성합니다.

	}


}

