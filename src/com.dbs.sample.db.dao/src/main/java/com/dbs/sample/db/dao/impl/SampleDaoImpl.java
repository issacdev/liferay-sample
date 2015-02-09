package com.dbs.sample.db.dao.impl;

import java.util.List;

import com.dbs.db.dao.GenericDao;
import com.dbs.db.filter.Criteria;
import com.dbs.sample.db.bean.Sample;
import com.dbs.sample.db.dao.ISampleDao;

public class SampleDaoImpl extends GenericDao<Sample> implements ISampleDao {

	public List<Sample> findByCriteriaWithSQL(Criteria criteria) {
		StringBuffer sb = new StringBuffer();
		String tableName = this.getTableName(criteria.isAudit);
		sb.append("select " + tableName +".* from " + tableName);
		return super.findByCriteria(criteria);
	}

}
