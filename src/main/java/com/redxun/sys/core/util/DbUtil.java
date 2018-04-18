package com.redxun.sys.core.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.redxun.core.database.api.ITableMeta;
import com.redxun.core.database.api.model.Column;
import com.redxun.core.database.api.model.Table;
import com.redxun.core.database.datasource.DataSourceUtil;
import com.redxun.core.database.datasource.DbContextHolder;
import com.redxun.core.entity.GridHeader;
import com.redxun.core.util.AppBeanUtil;

public class DbUtil {

	
	
	/**
	 * 通过SQL获得其查询的表格头部
	 * @param sql
	 * @return
	 * @throws SQLException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 */
	public static List<GridHeader> getGridHeaders(String sql) throws IllegalAccessException, NoSuchFieldException, SQLException{
		JdbcTemplate jdbcTemplate=AppBeanUtil.getBean(JdbcTemplate.class);
		ITableMeta iTableMeta=AppBeanUtil.getBean(ITableMeta.class);;
		List<GridHeader> headers=new ArrayList<GridHeader>();
		String orgSql=sql;
		try {
			SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
			SqlRowSetMetaData metaData = rowSet.getMetaData();  
			int columnCount = metaData.getColumnCount(); 
			//若为单独的表，可以读取其注释字段信息
			sql=sql.toUpperCase();
			int lastFromIndex=sql.indexOf(" FROM ");
			Map<String,Column> columnMap=new HashMap<String, Column>();
			if(lastFromIndex>0){
					String fromSubSql=orgSql.substring(lastFromIndex+5).trim();
					int tableIndex=fromSubSql.indexOf(" ");
					String tableName=null;
					if(tableIndex>0){
						tableName=fromSubSql.substring(0,tableIndex).trim();
					}else{
						tableName=fromSubSql;
					}
					Table table=iTableMeta.getTableByName(tableName);
					List<Column> columns=table.getColumnList();
					for(Column c:columns){
						columnMap.put(c.getFieldName(), c);
					}
			}
			for (int i = 1; i <= columnCount; i++) {
				String fieldName=metaData.getColumnLabel(i);
				String fieldType=metaData.getColumnTypeName(i);
				GridHeader header=new GridHeader();
				header.setFieldName(fieldName);
				header.setFieldType(fieldType);
				Column c=columnMap.get(fieldName);
				if(c!=null){
					header.setFieldLabel(c.getComment());
				}
				header.setDbFieldType(metaData.getColumnType(i));
				headers.add(header);
			}
			return headers;
		} catch (Exception e) {
			Connection con = null;
			ResultSet resultset = null;
			try {
				String ds=DbContextHolder.getDataSource();
				DataSource dataSource= DataSourceUtil.getDataSourceByAlias(ds);
				con = DataSourceUtils.getConnection(dataSource);
				PreparedStatement preparedStatement = con.prepareStatement(orgSql); 
				resultset = preparedStatement.executeQuery(); 
				ResultSetMetaData meta= resultset.getMetaData();
				int colument= meta.getColumnCount();
				for(int i=1;i<=colument;i++){
					GridHeader gridHeader=new GridHeader();
					
					int columnType=meta.getColumnType(i);
					String columnName=meta.getColumnName(i);
					String columnTypeName=meta.getColumnTypeName(i);
				
					gridHeader.setFieldLabel(columnName);
					
					gridHeader.setDbFieldType(columnType);
					gridHeader.setFieldName(columnName);
					gridHeader.setFieldType(columnTypeName);
					headers.add(gridHeader);
				}
			}
			finally {
				JdbcUtils.closeResultSet(resultset);
				JdbcUtils.closeConnection(con);
			}
			
			return headers;
		}
	}
}
