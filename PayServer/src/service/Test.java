package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.anno.Path;

@Path("test")
public class Test extends Service{

@Override
protected void messageReceived(String appid, HttpServletRequest req,
		HttpServletResponse resp) {
	// TODO Auto-generated method stub
	
}

}
