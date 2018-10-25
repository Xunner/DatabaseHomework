package xunner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import xunner.bean.User;
import xunner.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * 调用演示类
 * <br>
 * created on 2018/10/24
 *
 * @author 巽
 **/
public class Main {
	private static SqlSessionFactory sqlSessionFactory;

	public static void main(String[] args) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper userMapper = session.getMapper(UserMapper.class);

			User user = userMapper.getById(1);
			if (user == null) {
				System.out.println("not found");
			} else {
				System.out.println("User: id = " + user.getUserId() + ", number = " + user.getNumber() + ", balance = " + user.getBalance());
			}

			session.commit();
		}
	}

	/*
		对某个用户进行套餐的查询（包括历史记录）、订购、退订（考虑立即生效和次月生效）操作
		某个用户在通话情况下的资费生成
		某个用户在使用流量情况下的资费生成
		某个用户月账单的生成
	 */

	/**
	 * 生成月账单
	 *
	 * @param userId 用户id
	 * @param date   日期（年月）
	 */
	private static void generateMonthlyBill(int userId, LocalDate date) {
		//TODO
	}

	/**
	 * 生成流量资费
	 *
	 * @param userId  用户id
	 * @param data    流量大小
	 * @param isLocal 是否为本地流量（否则为全国流量）
	 */
	private static void generateDataExpense(int userId, double data, boolean isLocal) {
		//TODO
	}

	/**
	 * 生成（一条）短信资费
	 *
	 * @param userId 用户id
	 */
	private static void generateMessageExpense(int userId) {
		//TODO
	}

	/**
	 * 生成通话资费
	 *
	 * @param userId  用户id
	 * @param minutes 通话时长（分钟）
	 */
	private static void generateCallExpense(int userId, double minutes) {
		//TODO
	}

	/**
	 * 退订套餐，立即生效
	 *
	 * @param userId  用户id
	 * @param orderId 订单id
	 */
	private static void unsubscribeByNow(int userId, int orderId) {
		//TODO
	}

	/**
	 * 退订套餐，次月生效
	 *
	 * @param userId  用户id
	 * @param orderId 订单id
	 */
	private static void unsubscribeNextMonth(int userId, int orderId) {
		//TODO
	}

	/**
	 * 订购套餐
	 *
	 * @param userId 用户id
	 * @param planId 套餐id
	 */
	private static void subscribe(int userId, int planId) {
		//TODO
	}

	/**
	 * 查询用户当前生效中的套餐
	 *
	 * @param userId 用户id
	 */
	private static void serachUserCurrentOrders(int userId) {
		//TODO
	}

	/**
	 * 查询用户时间段内订购的套餐
	 *
	 * @param userId    用户id
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 */
	private static void serachUserOrders(int userId, LocalDate startDate, LocalDate endDate) {
		//TODO
	}

	static {
		String resource = "mybatis-config.xml";
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}