package org.thinker.oauth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thinker.oauth.ResourceTokenVO;
import org.thinker.oauth.TokenSender;

/**
 * Servlet implementation class TwitterHelloServlet
 */
public class TwitterResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TwitterResourceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 이곳에 코드를 작성하니다.
		// 3. 세션에 저장된 AT ATS 를 사용하여 트위터에 리소스 요청
		HttpSession session = request.getSession();
		String at = (String) session.getAttribute("AT");
		String ats = (String) session.getAttribute("ATS");

		
		System.out.println("### AT : " + at); // at가 "숫자- ~" 이런형식으로 나와야 정상
		System.out.println("### ATS: " + ats);

		ResourceTokenVO vo = new ResourceTokenVO();
		vo.setMethod("GET");
		vo.setRequestURL(Setup.RESOURCE_URL);
		vo.setConsumerKey(Setup.CONSUMER_KEY);
		vo.setConsumerSecretKey(Setup.CONSUMER_SECRET);
		vo.setRequestOauthToken(at);
		vo.setRequestOauthTokenSecret(ats);
		vo.sign();

		TokenSender sender = new TokenSender(TokenSender.TYPE_PARAM);
		try {
			sender.sendHttpClient(vo);
			response.setContentType("application/json");
			response.getWriter().print(vo.getResult());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
