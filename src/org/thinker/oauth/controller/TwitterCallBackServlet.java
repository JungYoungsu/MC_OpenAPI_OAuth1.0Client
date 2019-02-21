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
		// �̰��� �ڵ带 �ۼ��մϴ�.
		// 2. �ݹ� URL�� ���� Verifier�� �Ʊ� ���� RT �� �����ϰ�, ���ǿ� �����ص� RTS�� ���ͼ�, AT, ATS ��û �� ����
		// ����
		HttpSession session = request.getSession();
		String rt = (String) session.getAttribute("RT");
		String rts = (String) session.getAttribute("RTS");

		AccessTokenVO atvo = new AccessTokenVO(request.getQueryString());
		atvo.setMethod("GET");
		atvo.setRequestURL(Setup.ACCESS_TOKEN_URL);
		atvo.setConsumerKey(Setup.CONSUMER_KEY);
		atvo.setConsumerSecretKey(Setup.CONSUMER_SECRET);
		// �ؿ� ������ new AccessTokenVO(request.getQueryString()); ���� �̹� ��
		// atvo.setVerifier(atvo.getVerifier());
		// atvo.setRequestOauthToken(rt);
		atvo.setRequestOauthTokenSecret(rts);
		atvo.sign();

		TokenSender sender = new TokenSender(TokenSender.TYPE_PARAM);
		try {
			sender.sendHttpClient(atvo);
			session.setAttribute("AT", atvo.getRequestOauthToken());
			session.setAttribute("ATS", atvo.getRequestOauthTokenSecret());
			response.sendRedirect("resource"); // resource (3.) ���� �ѱ��
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
