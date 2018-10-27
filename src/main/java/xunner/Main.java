package xunner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import xunner.bean.Order;
import xunner.bean.Plan;
import xunner.bean.User;
import xunner.enums.OrderState;
import xunner.mapper.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			PlanMapper planMapper = session.getMapper(PlanMapper.class);
//			OrderMapper orderMapper = session.getMapper(OrderMapper.class);
//
//			LocalDate today = LocalDate.now();
//			List<Map<String, Object>> ret = orderMapper.getValidOrdersMessages(1, LocalDate.of(today.getYear(), today.getMonth(), 1), LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth()));
//			System.out.println(ret);
//
//			session.commit();
//		}

		searchUserCurrentOrders(1);


//		String s = String.format("%10s", new String("（汉字）".getBytes(), StandardCharsets.ISO_8859_1));
//		System.out.println(new String(s.getBytes(StandardCharsets.ISO_8859_1)) + "-");
//		System.out.println("12345678910");
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
	 * 退订套餐，立即生效。逻辑：套餐内已使用的部分不再变动，未使用部分不予退款；若订单非当月订单则退订失败
	 *
	 * @param orderId 订单id
	 */
	private static void unsubscribeByNow(int orderId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);

			LocalDate today = LocalDate.now();
			Order order = orderMapper.getById(orderId);
			if ((order.getState() == OrderState.EFFECTIVE || order.getState() == OrderState.INVALID_NEXT_MONTH) &&
					order.getDate().getYear() == today.getYear() && order.getDate().getMonth() == today.getMonth()) {
				order.setState(OrderState.INVALID);
				orderMapper.update(order);
			} else {
				System.out.println("订单已失效，退订失败");
			}

			session.commit();
		}
	}

	/**
	 * 退订套餐，次月生效；若订单非当月订单则退订失败
	 *
	 * @param orderId 订单id
	 */
	private static void unsubscribeNextMonth(int orderId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);

			LocalDate today = LocalDate.now();
			Order order = orderMapper.getById(orderId);
			if (order.getState() == OrderState.EFFECTIVE &&
					order.getDate().getYear() == today.getYear() && order.getDate().getMonth() == today.getMonth()) {
				order.setState(OrderState.INVALID_NEXT_MONTH);
				orderMapper.update(order);
			} else {
				System.out.println("订单已失效，退订失败");
			}

			session.commit();
		}
	}

	/**
	 * 订购套餐
	 *
	 * @param userId 用户id
	 * @param planId 套餐id
	 */
	private static void subscribe(int userId, int planId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			PlanMapper planMapper = session.getMapper(PlanMapper.class);

			User user = userMapper.getById(userId);
			Plan plan = planMapper.getById(planId);
			if (user.getBalance() < plan.getPrice()) {
				System.out.println("账户余额不足！");
			} else {
				OrderMapper orderMapper = session.getMapper(OrderMapper.class);
				orderMapper.add(new Order(userId, planId, LocalDate.now(), OrderState.EFFECTIVE));

				user.setBalance(user.getBalance() - plan.getPrice());
				userMapper.update(user);
			}

			session.commit();
		}
	}

	/**
	 * 查询用户当前生效中的套餐信息
	 *
	 * @param userId 用户id
	 */
	private static void searchUserCurrentOrders(int userId) {
		LocalDate today = LocalDate.now();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);
			CallExpenseMapper callExpenseMapper = session.getMapper(CallExpenseMapper.class);
			DataExpenseMapper dataExpenseMapper = session.getMapper(DataExpenseMapper.class);
			MessageExpenseMapper messageExpenseMapper = session.getMapper(MessageExpenseMapper.class);

			List<Map<String, Object>> results = orderMapper.getValidOrdersMessages(userId, LocalDate.of(today.getYear(), today.getMonth(), 1), LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth()));
			for (Map result : results) {
				OrderState state = (OrderState) result.get("state");
				Plan plan = (Plan) result.get("plan");
				int orderId = (int) result.get("orderId");
				int messagesLeft = plan.getMessage() - messageExpenseMapper.countMessagesInOrder(orderId);
				double minutesLeft = plan.getMinutes() - callExpenseMapper.sumMinutesInOrder(orderId);
				double localDataLeft = plan.getLocalData() - dataExpenseMapper.sumLocalDataInOrder(orderId);
				double nationalDataLeft = plan.getNationalData() - dataExpenseMapper.sumNationalDataInOrder(orderId);
				System.out.println("名称: " + plan.getName()
						+ ", 价格: " + plan.getPrice()
						+ ", 时长（剩/总）: " + minutesLeft + "/" + plan.getMinutes()
						+ ", 短信（剩/总）: " + messagesLeft + "/" + plan.getMessage()
						+ ", 本地流量（剩/总）: " + localDataLeft + "/" + plan.getLocalData()
						+ ", 全国流量（剩/总）: " + nationalDataLeft + "/" + plan.getNationalData()
						+ ", 状态: " + state.getValue());
			}

			session.commit();
		}
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

			List<Map<String, Object>> results = orderMapper.getOrdersByUserIdAndDates(userId, startDate, endDate);
			for (Map result : results) {
				OrderState state = (OrderState) result.get("state");
				LocalDate date = (LocalDate) result.get("date");
				Plan plan = (Plan) result.get("plan");
				System.out.println("名称: " + plan.getName()
						+ ", 价格: " + plan.getPrice()
						+ ", 日期: " + date
						+ ", 状态: " + state.getValue());
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

	private static String format(Object o) {
//		String s = String.format("%-25s", new String(o.toString().getBytes(), StandardCharsets.ISO_8859_1));
//		return new String(s.getBytes(StandardCharsets.ISO_8859_1));
		return o.toString() + "\t\t";
	}
}