package org.thinker.oauth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thinker.oauth.OAuthMsgConstants;
import org.thinker.oauth.RequestTokenVO;
import org.thinker.oauth.TokenSender;

/**
 * Servlet implementation class TwitterRequestServlet
 */
public class TwitterRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// consumerKey=2aa7eeb1-d517-4e9a-88da-28b4822da36c1306945419754,
	// consumerSecretKey=09bf04a3e0ee3dc22ac49263e110ef8f,
	// requestToken=2aa7eeb1-d517-4e9a-88da-28b4822da36c13069454197541306945419768995000,
	// requestTokenSecret=5479d974eaa14604ada5149feff84c1c,
	// accessToken=09bf04a3e0ee3dc22ac49263e110ef8f13069454197691160002aa7eeb1-d517-4e9a-88da-28b4822da36c13069454197541306945419768995000,
	// accessTokenSecret=481fb19369769b29bf0cfde59ca5d5de

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TwitterRequestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 이곳에 코드를 작성합니다.
		// http://jcornor.com:8000/tClientApp/
		// 1. CK CS를 사용하여 RT RTS를 가져와 세션에 저장
		RequestTokenVO rtvo = new RequestTokenVO();
		rtvo.setMethod("GET");
		rtvo.setRequestURL(Setup.REQUEST_TOKEN_URL);
		rtvo.setConsumerKey(Setup.CONSUMER_KEY); // 먼저 받은 컨슈머 키와 시크릿 넣기
		rtvo.setConsumerSecretKey(Setup.CONSUMER_SECRET);
		rtvo.setCallbackURL(Setup.CALLBACK_URL);
		rtvo.sign();

		TokenSender sender = new TokenSender(TokenSender.TYPE_PARAM);
		try {
			sender.sendHttpClient(rtvo);

			HttpSession session = request.getSession();
			session.setAttribute("RT", rtvo.getRequestOauthToken()); // 세션에 리퀘스트 토큰 및 시크릿 저장
			session.setAttribute("RTS", rtvo.getRequestOauthTokenSecret()); // 세션은 쿠키 사용하고 쿠키는 도메인종속적이므로 jcornor 계속 사용
			response.sendRedirect( // 보내기
					Setup.AUTHORIZE_URL + "?" + OAuthMsgConstants.OAUTH_TOKEN + "=" + rtvo.getRequestOauthToken());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
