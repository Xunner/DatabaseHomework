package xunner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import xunner.bean.User;
import xunner.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Main测试类
 * <br>
 * created on 2018/10/24
 *
 * @author 巽
 **/
public class Main {
	public static void main(String[] args) {
		String resource = "mybatis-config.xml";
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

			try (SqlSession session = sqlSessionFactory.openSession()) {
				UserMapper userMapper = session.getMapper(UserMapper.class);

				User user = userMapper.getById(1);
				if (user == null) {
					System.out.println("not found");
				} else {
					System.out.println(user.getUserId() + ", " + user.getNumber() + ", " + user.getBalance());
				}

				session.commit();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
