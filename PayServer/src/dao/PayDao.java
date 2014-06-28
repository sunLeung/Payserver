package dao;

import static common.db.DbUtils.dbUtils;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bean.PayBean;

public class PayDao {
	private static Log log=LogFactory.getLog(PayDao.class);
	
	public static int save(PayBean bean){
		try {
			return dbUtils.insert(bean);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
	
	public static PayBean getPayBeanByCporderid(String cporderid){
		PayBean bean=null;
		try {
			bean=dbUtils.read(PayBean.class, "where cporderid=?", cporderid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PayBean getPayBeanByOrderid(String orderid){
		PayBean bean=null;
		try {
			bean=dbUtils.read(PayBean.class, "where orderid=?", orderid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
}
