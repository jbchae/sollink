package com.solutionlink.itscope.intface.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.solutionlink.itscope.framework.exception.UserException;
import com.solutionlink.itscope.framework.util.CommonStaticString;
import com.solutionlink.itscope.framework.util.DateUtil;
import com.solutionlink.itscope.intface.model.PmApprovalRef;
import com.solutionlink.itscope.intface.model.PmIntfaceLog;
import com.solutionlink.itscope.intface.service.ApprovalRefManager;
import com.solutionlink.itscope.intface.service.GwInfoManager;
import com.solutionlink.itscope.intface.service.IntfaceManager;


/**
 * ==========================================================================
 * P R O G R A M    I N F O R M A T I O N
 * ==========================================================================
 * 0. Project		: ITSCOPE
 *
 * 1. FileName		: IntfaceController.java
 * 2. Package		: com.solutionlink.itscope.intface.controller
 * 3. Comment		:
 * 4. Author		: MKNOH
 * 5. Since			: 2017. 2. 15. ���� 6:06:22
 *
 * ==========================================================================
 * V E R S I O N    C O N T R O L
 * ==========================================================================
 * DATE			AUTHOR		VER.	DESCRIPTION
 * ------------ ----------- ------- -----------------------------------------
 * 2017. 2. 15. MKNOH 		1.0		�ű� ����.
 * ==========================================================================
 */
public class IntfaceController extends MultiActionController {

	private final Log log = LogFactory.getLog(this.getClass());
	private IntfaceManager intfaceManager;

	private ApprovalRefManager approvalRefManager;
	private GwInfoManager gwInfoManager;

	public void setApprovalRefManager(ApprovalRefManager approvalRefManager) {
		this.approvalRefManager = approvalRefManager;
	}

	public void setIntfaceManager(IntfaceManager intfaceManager) {
		this.intfaceManager = intfaceManager;
	}

	public void setGwInfoManager(GwInfoManager gwInfoManager) {
		this.gwInfoManager = gwInfoManager;
	}




	/**
	 * <PRE>
	 * 1. MethodName	: list
	 * 2. ClassName		: IntfaceController
	 * 3. Comment		:
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 27. ���� 10:48:31
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param request
	 * 		@param response
	 * 		@return
	 * 		@throws Exception
	 */
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response)throws Exception {


		return new ModelAndView("intface/IntfaceList");
	}

	/**
	 * <PRE>
	 * 1. MethodName	: listFrame
	 * 2. ClassName		: IntfaceController
	 * 3. Comment		:
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 27. ���� 10:48:31
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param request
	 * 		@param response
	 * 		@return
	 * 		@throws Exception
	 */
	public ModelAndView listFrame(HttpServletRequest request, HttpServletResponse response)throws Exception {

		HashMap<String, String> searchMap = new HashMap();
		List intfaceLogList = this.intfaceManager.selectIntfaceLogList(searchMap);

		request.setAttribute("intfaceLogList", intfaceLogList);

		return new ModelAndView("intface/IntfaceListFrame");
	}

	/**
	 * <PRE>
	 * 1. MethodName	: createGwId
	 * 2. ClassName		: IntfaceController
	 * 3. Comment		: GW ID ���� �ޱ�
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 15. ���� 6:04:23
	 * </PRE>
	 * 		@return HashMap<String,String>
	 * 		@param request
	 * 		@param response
	 * 		@return
	 * 		@throws Exception
	 */
	public ModelAndView createGwId(HttpServletRequest request, HttpServletResponse response) throws Exception {


		System.out.println("INTERFACE createGwId START============================================================================");

		StringBuffer paramValue = new StringBuffer();

		String ipAddress  = "";
        if (request.getHeader("HTTP_X_FORWARDED_FOR") == null) {
        	ipAddress = request.getRemoteAddr();
        } else {
        	ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }


    	String mode = request.getParameter("mode");
    	String prjMngNo = request.getParameter("prjMngNo");
    	String wbsSeq = request.getParameter("wbsSeq");
    	String taskId = request.getParameter("taskId");
    	String gwApprType = request.getParameter("gwApprType");
    	/*******�׷���� PARAMETER**********/
    	String gwApprStatus = request.getParameter("apprStatus");
    	String gwKey = request.getParameter("docId");
    	String userId = request.getParameter("userId");


        System.out.println("INTERFACE createGwId CurrentToday()[" + DateUtil.getFullCurrentToday()+"]");
		System.out.println("INTERFACE createGwId getRequestURL[" + request.getRequestURL() + "]");
		System.out.println("INTERFACE createGwId ipAddress[" + ipAddress + "]");

		System.out.println("INTERFACE updateGwStatus docId[" + gwKey + "]");
		System.out.println("INTERFACE updateGwStatus apprStatus[" + gwApprStatus + "]");
		System.out.println("INTERFACE updateGwStatus userId[" + userId + "]");



        Enumeration enu = request.getParameterNames();

    	while(enu.hasMoreElements()) {
    		String key = (String)enu.nextElement();
    		String value = request.getParameter(key);
    		paramValue.append("|"+key+":"+value);
    	}
    	System.out.println(paramValue.toString());

    	PmIntfaceLog pmIntfaceLog = new PmIntfaceLog();
    	pmIntfaceLog.setMode(CommonStaticString.mode);
    	pmIntfaceLog.setInfSystem("GW");
    	pmIntfaceLog.setInfDiv("createGwId");
    	pmIntfaceLog.setSendDiv("R"); 	//S:SEND,R:RECEIVE
    	if (request.getRequestURL() != null) {
    		pmIntfaceLog.setInputUrl(request.getRequestURL().toString());
    	}
    	if (paramValue != null ) {
    		pmIntfaceLog.setInputParam(paramValue.toString());
    	}
    	pmIntfaceLog.setOutputUrl("");
    	pmIntfaceLog.setOutputParam("");
    	pmIntfaceLog.setEntryId(userId);

    	String infSeq = this.intfaceManager.insertIntfaceLog(pmIntfaceLog);
    	pmIntfaceLog.setInfSeq(infSeq);

    	String resultCd = "00"; //00:����, 99:����
    	String resultMsg = "����ó���Ǿ����ϴ�.";

    	try {
	    	PmApprovalRef pmApprovalRef = new PmApprovalRef();

	    	if (gwKey == null || "".equals(gwKey)) {
	    		throw new Exception("�׷���� KEY�� �������� �ʽ��ϴ�.");
	    	}
	    	if (gwApprType == null || "".equals(gwApprType)) {
	    		throw new Exception("�׷���� ������ID �� �������� �ʽ��ϴ�.");
	    	}
	    	if (gwApprStatus == null || "".equals(gwApprStatus)) {
	    		throw new Exception("�׷���� ������°� �������� �ʽ��ϴ�.");
	    	}
	    	if (prjMngNo == null || "".equals(prjMngNo)) {
	    		throw new Exception("������ƮID �� �������� �ʽ��ϴ�.");
	    	}
	    	if (wbsSeq == null || "".equals(wbsSeq)) {
	    		throw new Exception("WBS ������ �������� �ʽ��ϴ�.");
	    	}
	    	if (taskId == null || "".equals(taskId)) {
	    		throw new Exception("�½�ũID �� �������� �ʽ��ϴ�.");
	    	}
	    	if (userId == null || "".equals(userId)) {
	    		throw new Exception("�����ID �� �������� �ʽ��ϴ�.");
	    	}

	    	pmApprovalRef.setGwKey(gwKey);
	    	pmApprovalRef.setGwApprType(gwApprType);
	    	pmApprovalRef.setGwApprStatus(gwApprStatus);
	    	pmApprovalRef.setRefId1(prjMngNo);
	    	pmApprovalRef.setRefId2(wbsSeq);
	    	pmApprovalRef.setRefId3(taskId);
	    	pmApprovalRef.setEntryId(userId);
	    	pmApprovalRef.setUpdateId(userId);

	    	this.approvalRefManager.save(pmApprovalRef);
    	} catch (Exception e) {
    		resultCd = "99"; //00:����, 99:����
    		resultMsg = e.getMessage();
    	}



    	/** �������̽� ��� update **/
    	pmIntfaceLog.setInputUrl(null);
    	pmIntfaceLog.setInputParam(null);
    	pmIntfaceLog.setOutputUrl(null);
    	pmIntfaceLog.setOutputParam(null);
    	pmIntfaceLog.setResultCd(resultCd);
    	pmIntfaceLog.setResultMsg(resultMsg);
    	pmIntfaceLog.setUpdateId(userId);

    	this.intfaceManager.updateIntfaceLog(pmIntfaceLog);

    	request.setAttribute("resultCd", resultCd);
    	request.setAttribute("resultMsg", resultMsg);

    	HashMap<String,String> resultMap = new HashMap<String,String>();

    	resultMap.put("resultCd", resultCd);
    	resultMap.put("resultMsg", resultMsg);


    	System.out.println("INTERFACE createGwId END============================================================================");

    	return new ModelAndView("intface/IntfaceResult");

	}

	/**
	 * <PRE>
	 * 1. MethodName	: updateGwStatus
	 * 2. ClassName		: IntfaceController
	 * 3. Comment		: GW ���� STATUS �ޱ�
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 15. ���� 6:04:23
	 * </PRE>
	 * 		@return HashMap<String,String>
	 * 		@param request
	 * 		@param response
	 * 		@return
	 * 		@throws Exception
	 */
	public ModelAndView updateGwStatus(HttpServletRequest request, HttpServletResponse response)throws Exception {

		StringBuffer paramValue = new StringBuffer();

		String ipAddress  = "";
        if (request.getHeader("HTTP_X_FORWARDED_FOR") == null) {
        	ipAddress = request.getRemoteAddr();
        } else {
        	ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }


    	String mode = request.getParameter("mode");
    	String prjMngNo = request.getParameter("prjMngNo");
    	String wbsSeq = request.getParameter("wbsSeq");
    	String taskId = request.getParameter("taskId");
    	String gwApprType = request.getParameter("gwApprType");

    	/*******�׷���� PARAMETER**********/
    	String gwApprStatus = request.getParameter("apprStatus");
    	String gwKey = request.getParameter("docId");
    	String userId = request.getParameter("userId");

        System.out.println("INTERFACE updateGwStatus START============================================================================");
        System.out.println("INTERFACE updateGwStatus CurrentToday()[" + DateUtil.getFullCurrentToday()+"]");
		System.out.println("INTERFACE updateGwStatus getRequestURL[" + request.getRequestURL() + "]");
		System.out.println("INTERFACE updateGwStatus ipAddress[" + ipAddress + "]");

		System.out.println("INTERFACE updateGwStatus docId[" + gwKey + "]");
		System.out.println("INTERFACE updateGwStatus apprStatus[" + gwApprStatus + "]");
		System.out.println("INTERFACE updateGwStatus userId[" + userId + "]");


        Enumeration enu = request.getParameterNames();

    	while(enu.hasMoreElements()) {
    		String key = (String)enu.nextElement();
    		String value = request.getParameter(key);
    		paramValue.append("|"+key+":"+value);
    	}
    	System.out.println(paramValue.toString());

    	PmIntfaceLog pmIntfaceLog = new PmIntfaceLog();
    	pmIntfaceLog.setMode(CommonStaticString.mode);
    	pmIntfaceLog.setInfSystem("GW");
    	pmIntfaceLog.setInfDiv("updateGwStatus");
    	pmIntfaceLog.setSendDiv("R"); 	//S:SEND,R:RECEIVE
    	if (request.getRequestURL() != null) {
    		pmIntfaceLog.setInputUrl(request.getRequestURL().toString());
    	}
    	if (paramValue != null ) {
    		pmIntfaceLog.setInputParam(paramValue.toString());
    	}
    	pmIntfaceLog.setOutputUrl("");
    	pmIntfaceLog.setOutputParam("");
    	pmIntfaceLog.setEntryId(userId);

    	String infSeq = this.intfaceManager.insertIntfaceLog(pmIntfaceLog);
    	pmIntfaceLog.setInfSeq(infSeq);

    	String resultCd = "00"; //00:����, 99:����
    	String resultMsg = "����ó���Ǿ����ϴ�.";

    	try {
	    	PmApprovalRef pmApprovalRef = new PmApprovalRef();

	    	if (gwKey == null || "".equals(gwKey)) {
	    		throw new Exception("�׷���� KEY�� �������� �ʽ��ϴ�.");
	    	}
	    	if (gwApprType == null || "".equals(gwApprType)) {
	    		throw new Exception("�׷���� ������ID �� �������� �ʽ��ϴ�.");
	    	}
	    	if (gwApprStatus == null || "".equals(gwApprStatus)) {
	    		throw new Exception("�׷���� ������°� �������� �ʽ��ϴ�.");
	    	}
	    	if (prjMngNo == null || "".equals(prjMngNo)) {
	    		throw new Exception("������ƮID �� �������� �ʽ��ϴ�.");
	    	}
	    	if (wbsSeq == null || "".equals(wbsSeq)) {
	    		throw new Exception("WBS ������ �������� �ʽ��ϴ�.");
	    	}
	    	if (taskId == null || "".equals(taskId)) {
	    		throw new Exception("�½�ũID �� �������� �ʽ��ϴ�.");
	    	}
	    	if (userId == null || "".equals(userId)) {
	    		throw new Exception("�����ID �� �������� �ʽ��ϴ�.");
	    	}

	    	pmApprovalRef.setGwKey(gwKey);
	    	pmApprovalRef.setGwApprType(gwApprType);
	    	pmApprovalRef.setGwApprStatus(gwApprStatus);
	    	pmApprovalRef.setRefId1(prjMngNo);
	    	pmApprovalRef.setRefId2(wbsSeq);
	    	pmApprovalRef.setRefId3(taskId);
	    	pmApprovalRef.setEntryId(userId);
	    	pmApprovalRef.setUpdateId(userId);

	    	this.approvalRefManager.save(pmApprovalRef);
    	} catch (Exception e) {
    		resultCd = "99"; //00:����, 99:����
    		resultMsg = e.getMessage();
    	}



    	/** �������̽� ��� update **/
    	pmIntfaceLog.setInputUrl(null);
    	pmIntfaceLog.setInputParam(null);
    	pmIntfaceLog.setOutputUrl(null);
    	pmIntfaceLog.setOutputParam(null);
    	pmIntfaceLog.setResultCd(resultCd);
    	pmIntfaceLog.setResultMsg(resultMsg);
    	pmIntfaceLog.setUpdateId(userId);

    	this.intfaceManager.updateIntfaceLog(pmIntfaceLog);

    	request.setAttribute("resultCd", resultCd);
    	request.setAttribute("resultMsg", resultMsg);

    	HashMap<String,String> resultMap = new HashMap<String,String>();

    	resultMap.put("resultCd", resultCd);
    	resultMap.put("resultMsg", resultMsg);



    	System.out.println("INTERFACE updateGwStatus END============================================================================");

    	return new ModelAndView("intface/IntfaceResult");
	}

	/**
	 * <PRE>
	 * 1. MethodName	: callGwPopup
	 * 2. ClassName		: IntfaceController
	 * 3. Comment		: GW �����û �˾� ȣ��
	 * 					SSO �α��� �ڵ� ����
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 24. ���� 11:41:55
	 * </PRE>
	 * 		@return ModelAndView
	 * 		@param request
	 * 		@param response
	 * 		@return
	 * 		@throws Exception
	 */
	public ModelAndView callGwPopup(HttpServletRequest request, HttpServletResponse response)throws Exception {

		String prjMngNo = request.getParameter("prjMngNo");
		String wbsSeq = request.getParameter("wbsSeq");
		String taskId = request.getParameter("taskId");
		String gwUrl = "";
		String gwKey = request.getParameter("gwKey");
		String userId = request.getParameter("userId");
		String callDiv = request.getParameter("callDiv"); //draft or view
		String gwApprType = request.getParameter("gwApprType");

		String GW_URL_DRAFT = "http://gw.chunilfood.co.kr/app/approval/integration/pms/create"; 	//�����ſ�
		String GW_URL_VIEW = "http://gw.chunilfood.co.kr/app/approval/document/"+gwKey+"/popup";	//������ȸ��



        PmApprovalRef pmApprovalRef = new PmApprovalRef();
        pmApprovalRef.setRefId1(prjMngNo);
        pmApprovalRef.setRefId2(wbsSeq);
        pmApprovalRef.setRefId3(taskId);
        pmApprovalRef.setGwApprType(gwApprType);
        pmApprovalRef.setMode(CommonStaticString.mode);
        PmApprovalRef approvalResult = null;

        /*** �׷���� ���� ����ȭ�� ���Ͽ� appr_status_view�� ��ȸ��. */

        approvalResult = gwInfoManager.selectGwStatus(pmApprovalRef);

        if ("draft".equals(callDiv)) {
        	//�������簡 �����ϸ� �ݷ��� ���°� �ƴϸ� ������ �� �� ����.
        	if (approvalResult != null && approvalResult.getGwKey() !=null &&  !approvalResult.getGwKey().equals("") && !"RETURN".equals(approvalResult.getGwApprStatus())) {
        		//�������簡 �����ϸ� �ݷ��� ���°� �ƴϸ� ��ȸ�� ������ PMS�� GW_KEY��  GW_APPR_STATUS ���� UPDATE �Ѵ�.
        		if (approvalResult.getGwKey() !=null && !approvalResult.getGwKey().equals(gwKey)) {
        			PmApprovalRef updateInfo = new PmApprovalRef();
        			updateInfo.setGwKey(approvalResult.getGwKey());
        			updateInfo.setGwApprType(gwApprType);
        			updateInfo.setGwApprStatus(approvalResult.getGwApprStatus());
        			updateInfo.setRefId1(prjMngNo);
        			updateInfo.setRefId2(wbsSeq);
        			updateInfo.setRefId3(taskId);
        			updateInfo.setEntryId(userId);
        			updateInfo.setUpdateId(userId);

        	    	this.approvalRefManager.save(updateInfo);

        	    	GW_URL_VIEW = "http://gw.chunilfood.co.kr/app/approval/document/"+approvalResult.getGwKey()+"/popup";	//������ȸ��
        	    	//UPDATE�� �� ����ȸ ȭ������ �̵���Ŵ.
        	    	gwUrl = GW_URL_VIEW;
        		} else {
        			//������ ���̽� : �������簡 �������� �ʰų�, �����ϴ���� �ݷ��ΰ��� ������ �����ϴ�.
        			gwUrl = GW_URL_DRAFT;
        		}
        	}
        }

        if ("view".equals(callDiv)) {
        	if (approvalResult == null) {
        		throw new UserException("�׷��� �ش��ϴ�  ���� ������ �����ϴ�.");
        	} else {
        		//PMS���� ������ �ִ� GW KEY�� �׷������ DB VIEW�� GW KEY���� ��ġ ���� ������ PMS�� GW_KEY�� GW_APPR_STATUS ���� UPDATE �Ѵ�.
        		if ( (!approvalResult.getGwKey().equals(gwKey)) || (!approvalResult.getGwApprStatus().equals(gwApprType)) ) {
        			PmApprovalRef updateInfo = new PmApprovalRef();
        			updateInfo.setGwKey(approvalResult.getGwKey());
        			updateInfo.setGwApprType(gwApprType);
        			updateInfo.setGwApprStatus(approvalResult.getGwApprStatus());
        			updateInfo.setRefId1(prjMngNo);
        			updateInfo.setRefId2(wbsSeq);
        			updateInfo.setRefId3(taskId);
        			updateInfo.setEntryId(userId);
        			updateInfo.setUpdateId(userId);

        	    	this.approvalRefManager.save(updateInfo);

        	    	GW_URL_VIEW = "http://gw.chunilfood.co.kr/app/approval/document/"+approvalResult.getGwKey()+"/popup";	//������ȸ��
        	    	gwUrl = GW_URL_VIEW;
        		} else {
        			gwUrl = GW_URL_VIEW;
        		}
        	}
        }
        /****/
        if ("draft".equals(callDiv)) {
        	gwUrl = GW_URL_DRAFT;
        } else if ("view".equals(callDiv)) {
        	gwUrl = GW_URL_VIEW;
        }

        /***************************/
		//õ�Ͻ�ǰ!@()
		String key = "cjsdlftlrvna!@()";
        //String plainText = "empno=0000000000&domain=kmcu.ac.kr";

		String plainText = "id="+userId+"&domain=chunilfood.co.kr";

		String addParam = "userId="+userId
				+"&prjMngNo="+prjMngNo
				+"&wbsSeq="+wbsSeq
				+"&taskId="+taskId
				+"&gwApprType="+gwApprType
				+"&mode="+CommonStaticString.mode;
		if ("test".equals(CommonStaticString.mode)) {
			//addParam +="&test";
		}

		String encodePARAM = "";

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        IvParameterSpec initalVector = new IvParameterSpec(key.getBytes());

        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, initalVector);

        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        encodePARAM = new String(Base64.encodeBase64(Base64.encodeBase64(encrypted)));

        System.out.println("encodePARAM : " + encodePARAM); // ���ڵ� 2��
        /***************************/


        /***************************/
		StringBuffer paramValue = new StringBuffer();

		String ipAddress  = "";
        if (request.getHeader("HTTP_X_FORWARDED_FOR") == null) {
        	ipAddress = request.getRemoteAddr();
        } else {
        	ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }


        System.out.println("INTERFACE callGwPopup START============================================================================");
        System.out.println("INTERFACE callGwPopup CurrentToday()[" + DateUtil.getFullCurrentToday()+"]");
		System.out.println("INTERFACE callGwPopup getRequestURL[" + request.getRequestURL() + "]");
		System.out.println("INTERFACE callGwPopup ipAddress[" + ipAddress + "]");


        Enumeration enu = request.getParameterNames();

        String tempKey = "";
        String tempValue = "";
    	while(enu.hasMoreElements()) {
    		tempKey = (String)enu.nextElement();
    		tempValue = request.getParameter(tempKey);
    		paramValue.append("|"+tempKey+":"+tempValue);
    	}
    	System.out.println(paramValue.toString());

    	PmIntfaceLog pmIntfaceLog = new PmIntfaceLog();
    	pmIntfaceLog.setMode(CommonStaticString.mode);
    	pmIntfaceLog.setInfSystem("GW");
    	pmIntfaceLog.setInfDiv("callGwPopup");
    	pmIntfaceLog.setSendDiv("S"); 	//S:SEND,R:RECEIVE
    	if (request.getRequestURL() != null) {
    		pmIntfaceLog.setInputUrl(request.getRequestURL().toString());
    	}
    	if (paramValue != null ) {
    		pmIntfaceLog.setInputParam(paramValue.toString());
    	}


    	String CALL_URL = "http://gw.chunilfood.co.kr/common/sso?companyId=10&gosso="+encodePARAM+"";
    	String CALL_GW_URL = gwUrl+"?"+addParam;
    	pmIntfaceLog.setOutputUrl(CALL_URL);
    	pmIntfaceLog.setOutputParam(plainText + "&url="+CALL_GW_URL);

    	String infSeq = this.intfaceManager.insertIntfaceLog(pmIntfaceLog);
    	pmIntfaceLog.setInfSeq(infSeq);

    	System.out.println("INTERFACE callGwPopup END============================================================================");

        /***************************/

        request.setAttribute("CALL_URL", CALL_URL);
        request.setAttribute("CALL_GW_URL", CALL_GW_URL);


        return new ModelAndView("intface/CallGwPopup");
	}

}
