<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="utils.Def"%>
<%@ page import="utils.LogUtils"%>
<%@ page import="utils.MD5"%>
<%@ page import="utils.ReqUtils"%>
<%@ page import="utils.RespUtils"%>
<%@ page import="utils.TransferUtils"%>
<%@ page import="bean.PayBean"%>
<%@ page import="bean.UserBean"%>
<%@ page import="common.config.App"%>
<%@ page import="common.config.AppContent"%>
<%@ page import="common.logger.Logger"%>
<%@ page import="common.logger.LoggerManger"%>
<%@ page import="dao.PayDao"%>

<%!
	private static Logger log=LoggerManger.getLogger("Baidu91_ios");
	private static String unionid="2";
	private static String respString="{\"ErrorCode\":\"1\",\"ErrorDesc\":\"接收成功\"}";

%>
<%
		//记录请求日志
		LogUtils.logReqParams(log, request);
		int appid=Integer.valueOf(request.getAttribute("appid").toString());
		App app=AppContent.appContent.get(appid);
		if(app==null){
			log.error("Wrong appid:"+appid);
			RespUtils.stringResp(response, respString);
			return;
		}

		try {
			String productId = request.getParameter("AppId"); // 应用ID，对应游戏客户端中使用的APPID
			String productName = request.getParameter("ProductName");
			String consumeStreamId = request.getParameter("ConsumeStreamId"); // 消费流水号
			String cooOrderSerial = request.getParameter("CooOrderSerial"); // 商户订单流水号
			String uin = request.getParameter("Uin"); // 91账号ID
			String goodsID = request.getParameter("GoodsId"); // 商品ID
			String goodsInfo = request.getParameter("GoodsInfo");// 商品名称
			String goodsCount = request.getParameter("GoodsCount"); // 商品数量
			String originalMoney = request.getParameter("OriginalMoney"); // 原始总价(格式：0.00)
			String orderMoney = request.getParameter("OrderMoney"); // 实际总价(格式：0.00)
			String note = request.getParameter("Note"); // 即支付描述（客户端API参数中的payDescription字段）购买时客户端应用通过API传入，原样返回给应用服务器开发者可以利用该字段，定义自己的扩展数据。例如区分游戏服务器
			String payStatus = request.getParameter("PayStatus"); // 支付状态：0=失败，1=成功
			String createTime = request.getParameter("CreateTime"); // 订单流水创建时间(yyyy-MM-dd HH:mm:ss)
			String sign = request.getParameter("Sign");

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
					RespUtils.stringResp(response, respString);
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
		RespUtils.stringResp(response, respString);
%>
