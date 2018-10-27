package xunner.mapper;

import xunner.bean.User;

/**
 * User映射接口
 * <br>
 * created on 2018/10/24
 *
 * @author 巽
 **/
public interface UserMapper {
	User getById(int userId);

	int add(User user);

	/**
	 * 只能修改账户余额
	 *
	 * @param user 已修改的用户
	 * @return 涉及行数
	 */
	int update(User user);
}
