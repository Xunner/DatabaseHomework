package xunner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import xunner.bean.Order;
import xunner.bean.Plan;
import xunner.mapper.OrderMapper;
import xunner.mapper.PlanMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

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
		searchUserOrders(1, LocalDate.of(2018, Month.JANUARY, 1), LocalDate.now());
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
	private static void searchUserCurrentOrders(int userId) {
		// TODO
	}

	/**
	 * 查询用户时间段内订购的套餐
	 *
	 * @param userId    用户id
	 * @param startDate 开始时间（年月）
	 * @param endDate   结束时间（年月）
	 */
	private static void searchUserOrders(int userId, LocalDate startDate, LocalDate endDate) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);
			PlanMapper planMapper = session.getMapper(PlanMapper.class);

			List<Order> orders = orderMapper.getOrdersByUserIdAndDates(userId, startDate, endDate);
			System.out.println("名称\t\t\t价格\t\t\t日期\t\t\t状态");
			for (Order order : orders) {
				Plan plan = planMapper.getById(order.getPlanId());
				System.out.println(plan.getName() + "\t\t\t" + plan.getPrice() + "\t\t\t" + order.getDate() + "\t\t\t" + order.getState().getValue());
				// TODO
			}

			session.commit();
		}
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