package com.solutionlink.itscope.intface.service;

import java.util.HashMap;
import java.util.List;

import com.solutionlink.itscope.intface.dao.PmIntfaceLogDAO;
import com.solutionlink.itscope.intface.model.PmIntfaceLog;

public class IntfaceManagerImpl implements IntfaceManager {

	private PmIntfaceLogDAO pmIntfaceLogDao;

	public void setPmIntfaceLogDao(PmIntfaceLogDAO pmIntfaceLogDao) {
		this.pmIntfaceLogDao = pmIntfaceLogDao;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectIntfaceLogList
	 * 2. ClassName		: InterfaceManagerImpl
	 * 3. Comment		: �������̽� �α� ��� ��ȸ
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 27. ���� 10:37:14
	 * </PRE>
	 * 		@return List
	 * 		@param searchMap
	 * 		@return
	 */
	public List selectIntfaceLogList(HashMap searchMap) {
		return this.pmIntfaceLogDao.selectIntfaceLogList(searchMap);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName	: insertIntfaceLog
	 * 2. ClassName		: InterfaceManagerImpl
	 * 3. Comment		: �������̽� �α� ����ó��
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 27. ���� 10:43:46
	 * </PRE>
	 * 		@return String 
	 * 		@param pmIntfaceLog
	 * 		@return
	 */
	public String insertIntfaceLog(PmIntfaceLog pmIntfaceLog) {
		String intSeq = this.pmIntfaceLogDao.selectIntfaceSeq();
		pmIntfaceLog.setInfSeq(intSeq);
		
		this.pmIntfaceLogDao.insert(pmIntfaceLog);
		
		return intSeq;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName	: updateIntfaceLog
	 * 2. ClassName		: InterfaceManagerImpl
	 * 3. Comment		: �������̽� �α� update ó��
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 2. 27. ���� 10:46:40
	 * </PRE>
	 * 		@return int
	 * 		@param pmIntfaceLog
	 * 		@return
	 */
	public int updateIntfaceLog(PmIntfaceLog pmIntfaceLog) {
		return this.pmIntfaceLogDao.updateByPrimaryKeySelective(pmIntfaceLog);
	}
}
