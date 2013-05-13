package org.rapid_framework.generator.util.sqlparse;

public class SqlTypeChecker {

	/**
	 * ��ǰ��sourceSql�Ƿ���select���
	 * @return
	 */
	public static boolean isSelectSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*select\\s.*\\sfrom\\s+.*");
	}
	/**
	 * ��ǰ��sourceSql�Ƿ���update���
	 * @return
	 */
	public static boolean isUpdateSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*update\\s+.*");
	}
	/**
	 * ��ǰ��sourceSql�Ƿ���delete���
	 * @return
	 */
	public static boolean isDeleteSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*delete\\s+from\\s.*");
	}
	/**
	 * ��ǰ��sourceSql�Ƿ���insert���
	 * @return
	 */
	public static boolean isInsertSql(String sourceSql) {
		return SqlParseHelper.removeSqlComments(sourceSql).trim().toLowerCase().matches("(?is)\\s*insert\\s+into\\s+.*");
	}
	
}
