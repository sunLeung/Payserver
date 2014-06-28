package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.anno.Path;

@Path("/test")
public class Test extends Service{

	@Override
	protected void messageReceived(HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub
		try {
			System.out.println("ok");
			System.out.println(req.getRequestURI());
			resp.getWriter().write("fuck");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
