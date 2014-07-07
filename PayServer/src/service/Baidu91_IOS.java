package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.Def;
import utils.LogUtils;
import utils.MD5;
import utils.ReqUtils;
import utils.RespUtils;
import utils.TransferUtils;
import bean.PayBean;
import bean.UserBean;
import common.anno.Path;
import common.config.App;
import common.config.AppContent;
import dao.PayDao;

@Path("91_ios")
public class Baidu91_IOS extends Service{
	private static Log log=LogFactory.getLog(Baidu91_IOS.class);
	
	private static String unionid="2";
	private static String respString="{\"ErrorCode\":\"1\",\"ErrorDesc\":\"接收成功\"}";
	@Override
	protected void messageReceived(int appid, HttpServletRequest req,
			HttpServletResponse resp) {
		//记录请求日志
		LogUtils.logReqParams(log, req);
		App app=AppContent.appContent.get(appid);
		if(app==null){
			log.error("Wrong appid:"+appid);
			RespUtils.stringResp(resp, respString);
			return;
		}

		try {
			String productId = req.getParameter("AppId"); // 应用ID，对应游戏客户端中使用的APPID
			String productName = req.getParameter("ProductName");
			String consumeStreamId = req.getParameter("ConsumeStreamId"); // 消费流水号
			String cooOrderSerial = req.getParameter("CooOrderSerial"); // 商户订单流水号
			String uin = req.getParameter("Uin"); // 91账号ID
			String goodsID = req.getParameter("GoodsId"); // 商品ID
			String goodsInfo = req.getParameter("GoodsInfo");// 商品名称
			String goodsCount = req.getParameter("GoodsCount"); // 商品数量
			String originalMoney = req.getParameter("OriginalMoney"); // 原始总价(格式：0.00)
			String orderMoney = req.getParameter("OrderMoney"); // 实际总价(格式：0.00)
			String note = req.getParameter("Note"); // 即支付描述（客户端API参数中的payDescription字段）购买时客户端应用通过API传入，原样返回给应用服务器开发者可以利用该字段，定义自己的扩展数据。例如区分游戏服务器
			String payStatus = req.getParameter("PayStatus"); // 支付状态：0=失败，1=成功
			String createTime = req.getParameter("CreateTime"); // 订单流水创建时间(yyyy-MM-dd HH:mm:ss)
			String sign = req.getParameter("Sign");

			String AppId=app.getUnionParam(unionid, "AppId");
			String AppKey=app.getUnionParam(unionid, "AppKey");
			
			UserBean userBean=TransferUtils.decode(cooOrderSerial);
			
			StringBuilder strSign = new StringBuilder();
			strSign.append(AppId).append(1).append(productName)
					.append(consumeStreamId).append(cooOrderSerial).append(uin)
					.append(goodsID).append(goodsInfo).append(goodsCount)
					.append(originalMoney).append(orderMoney).append(note)
					.append(payStatus).append(createTime).append(AppKey);
			String sign_check = MD5.encode(strSign.toString());
			
			log.info("MD5 meta source code:" + strSign.toString());
			log.info("MD5 source code:" + sign);
			log.info("MD5 Generated code:" + sign_check);

			if (sign_check.equals(sign) && payStatus.equals("1")) {
				PayBean paybean = PayDao.getPayBeanByCporderid(cooOrderSerial);
				log.error("Paybean is not null by Cporderid:"+cooOrderSerial);
				if (paybean!=null) {
					RespUtils.stringResp(resp, respString);
					return;
				}
				
				PayBean bean = new PayBean(userBean,cooOrderSerial,consumeStreamId,Float.valueOf(orderMoney),Def.CNY,unionid,"");
				PayDao.save(bean);
				
				log.info(cooOrderSerial + " pay success");
				String gameServerUrl = app.getServerUrl(bean.getServerid());
				ReqUtils.requestGameServer(log, gameServerUrl, bean.getLoginid(), bean.getUuid(), bean.getMoney(), bean.getUnionid(), bean.getIsOk(), bean.getOrderid());
			}else{
				log.info("Verification failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		RespUtils.stringResp(resp, respString);
	}
}
