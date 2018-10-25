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
	User getUserById(int userId);
}
