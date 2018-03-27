package cn.yuyizyk.ground.mapper.provider;

public class SqlForMySql {

	public static final String toPage(String sql, int page, int size) {
		return new StringBuffer(" select * from ( ").append(sql).append(" ) t  limit ").append((page - 1) * size)
				.append(" , ").append(size).append(" ").toString();
	}
}
