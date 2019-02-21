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

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 이곳에 코드를 작성합니다.
		// 2. 콜백 URL로 받은 Verifier와 아까 보낸 RT 를 적용하고, 세션에 저장해둔 RTS를 얻어와서, AT, ATS 요청 후 세션
		// 저장
		HttpSession session = request.getSession();
		String rt = (String) session.getAttribute("RT");
		String rts = (String) session.getAttribute("RTS");

		AccessTokenVO atvo = new AccessTokenVO(request.getQueryString());
		atvo.setMethod("GET");
		atvo.setRequestURL(Setup.ACCESS_TOKEN_URL);
		atvo.setConsumerKey(Setup.CONSUMER_KEY);
		atvo.setConsumerSecretKey(Setup.CONSUMER_SECRET);
		// 밑에 두줄은 new AccessTokenVO(request.getQueryString()); 에서 이미 들어감
		// atvo.setVerifier(atvo.getVerifier());
		// atvo.setRequestOauthToken(rt);
		atvo.setRequestOauthTokenSecret(rts);
		atvo.sign();

		TokenSender sender = new TokenSender(TokenSender.TYPE_PARAM);
		try {
			sender.sendHttpClient(atvo);
			session.setAttribute("AT", atvo.getRequestOauthToken());
			session.setAttribute("ATS", atvo.getRequestOauthTokenSecret());
			response.sendRedirect("resource"); // resource (3.) 으로 넘기기
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
