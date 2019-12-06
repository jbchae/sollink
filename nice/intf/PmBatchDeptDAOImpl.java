package com.solutionlink.itscope.intface.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.solutionlink.itscope.common.model.PmDept;
import com.solutionlink.itscope.intface.model.PmBatchDept;

public class PmBatchDeptDAOImpl extends SqlMapClientDaoSupport implements PmBatchDeptDAO {
	
	
	@Override
	public void deleteBatchDeptTable() {
		getSqlMapClientTemplate().delete("PM_BATCH_DEPT.deletePmBatchDeptTable");
		
	}


	@Override
	public void insertDeptList(PmBatchDept data) {
		getSqlMapClientTemplate().insert("PM_BATCH_DEPT.insertPmBatchDept", data);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName	: selectDeptList
	 * 2. ClassName		: PmBatchDeptDAOImpl
	 * 3. Comment		: ���� PM_DEPT�� ����ȭ�� ������ ���� ��������
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 3. 3. ���� 5:36:03
	 * </PRE>
	 * 		@param pmBatchDept
	 * 		@return
	 */
	public List<PmBatchDept> selectDeptList(PmBatchDept pmBatchDept) {
		return getSqlMapClientTemplate().queryForList("PM_BATCH_DEPT.selectDeptList", pmBatchDept);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName	: insertDeptByBatch
	 * 2. ClassName		: PmBatchDeptDAOImpl
	 * 3. Comment		: ����ȭ�� �μ� insert
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 3. 3. ���� 5:40:13
	 * </PRE>
	 * 		@return void
	 * 		@param pmDept
	 */
	public void insertDeptByBatch(PmDept pmDept) {
		getSqlMapClientTemplate().insert("PM_BATCH_DEPT.insertDeptByBatch", pmDept);
	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName	: updateDeptByBatch
	 * 2. ClassName		: PmBatchDeptDAOImpl
	 * 3. Comment		: ����ȭ�� �μ� update
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 3. 3. ���� 5:40:16
	 * </PRE>
	 * 		@return int
	 * 		@param pmDept
	 * 		@return
	 */
	public int updateDeptByBatch(PmDept pmDept) {
		return getSqlMapClientTemplate().update("PM_BATCH_DEPT.updateDeptByBatch", pmDept);
	}
	
	
	
	/**
	 * <PRE>
	 * 1. MethodName	: updateDeptStatus
	 * 2. ClassName		: PmBatchDeptDAOImpl
	 * 3. Comment		: batch���� ������ �μ� �ܿ��� ��� DEPT_STATUS�� '0'���� ������.
	 * 4. �ۼ���			: MKNOH
	 * 5. �ۼ���			: 2017. 3. 9. ���� 4:20:29
	 * </PRE>
	 * 		@return int
	 * 		@param pmDept
	 * 		@return
	 */
	public int updateDeptStatus(PmDept pmDept) {
		return getSqlMapClientTemplate().update("PM_BATCH_DEPT.updateDeptStatus", pmDept);
	}
	
	
}
