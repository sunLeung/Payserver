package service;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.TimerManagerUtils;
import bean.PayBean;
import common.anno.Path;
import dao.PayDao;

@Path("test")
public class Test extends Service {

	@Override
	protected void messageReceived(int appid, HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub

		// PayBean bean = new PayBean();
		// bean.setUuid(12454572l);
		// bean.setLoginid(9182);;
		// bean.setServerid("1");
		// bean.setServerid("1");
		// bean.setOrderid(UUID.genOrderid());;
		// bean.setCporderid(UUID.genOrderid()+"");
		// bean.setSporderid(UUID.genOrderid()+"");
		// bean.setMoney(100);
		// bean.setMoneyType(Def.CNY);
		// bean.setUnionid("1");
		// bean.setInsertTime(new Timestamp(System.currentTimeMillis()));
		// bean.setRemark("remark");
		// bean.setIsOk(Def.PaySucceed);
		// PayDao.save(bean);

		PayBean paybean = PayDao.getPayBeanByCporderid("2341300488800000003");
		System.out.println(paybean);
		System.out.println(paybean.getMoney());
	}
	
	public static void main(String[] args) {
		TimerManagerUtils.schedule(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis());
				
			}
		}, 10, TimeUnit.SECONDS);
	}

}
