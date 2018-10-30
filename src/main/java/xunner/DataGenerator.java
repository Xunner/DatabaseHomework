package xunner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import xunner.bean.*;
import xunner.enums.OrderState;
import xunner.mapper.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 数据生成
 * <br>
 * created on 2018/10/28
 *
 * @author 巽
 **/
@SuppressWarnings({"unused", "WeakerAccess"})
public class DataGenerator {
	public static void generatePlans(SqlSessionFactory sqlSessionFactory) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			PlanMapper planMapper = session.getMapper(PlanMapper.class);

			planMapper.add(new Plan("500M全国流量月包", 30, 0, 0, 500, 0, false));
			planMapper.add(new Plan("本地流量套餐", 20, 0, 0, 2000, 0, false));
			planMapper.add(new Plan("话费套餐", 20, 100, 0, 0, 0, false));
			planMapper.add(new Plan("短信套餐", 10, 0, 200, 0, 0, false));
			planMapper.add(new Plan("国内流量套餐", 30, 0, 0, 0, 2000, false));
			planMapper.add(new Plan("无限流量套餐", 70, 0, 0, 22000, 10000, false));
			planMapper.add(new Plan("联通新生豪华套餐", 36, 200, 200, 7000, 4000, false));

			session.commit();
		}
	}

	public static void generateUsers(SqlSessionFactory sqlSessionFactory) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper userMapper = session.getMapper(UserMapper.class);

			userMapper.add(new User("13236550000", 99999));
			userMapper.add(new User("18778080000", 99999));
			userMapper.add(new User("13878840000", 0));
			userMapper.add(new User("12345678900", 50));
			userMapper.add(new User("13766666666", 100));

			session.commit();
		}
	}

	public static void generateOrders(SqlSessionFactory sqlSessionFactory, LocalDate date) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);

			orderMapper.add(new Order(1, 1, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 1, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 2, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 3, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 4, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 5, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 6, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 7, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 1, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 3, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 5, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 7, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(3, 2, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(4, 3, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(5, 4, date, OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 3, LocalDate.of(2018, Month.SEPTEMBER, 1), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 5, LocalDate.of(2018, Month.SEPTEMBER, 1), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 7, LocalDate.of(2018, Month.SEPTEMBER, 1), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 1, LocalDate.of(2018, Month.OCTOBER, 1), OrderState.INVALID_NEXT_MONTH));
			orderMapper.add(new Order(2, 1, LocalDate.of(2018, Month.OCTOBER, 1), OrderState.INVALID_NEXT_MONTH));
			orderMapper.add(new Order(1, 2, LocalDate.of(2018, Month.OCTOBER, 1), OrderState.INVALID_NEXT_MONTH));
			orderMapper.add(new Order(1, 1, LocalDate.of(2018, Month.FEBRUARY, 10), OrderState.INVALID));
			orderMapper.add(new Order(1, 1, LocalDate.of(2018, Month.APRIL, 1), OrderState.INVALID));
			orderMapper.add(new Order(1, 1, LocalDate.of(2018, Month.SEPTEMBER, 20), OrderState.INVALID));

			session.commit();
		}
	}

	public static void generateExpenses(SqlSessionFactory sqlSessionFactory, int userId, LocalDate date) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);
			CallExpenseMapper callExpenseMapper = session.getMapper(CallExpenseMapper.class);
			DataExpenseMapper dataExpenseMapper = session.getMapper(DataExpenseMapper.class);
			MessageExpenseMapper messageExpenseMapper = session.getMapper(MessageExpenseMapper.class);

			LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
			LocalDate lastDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
			LocalDateTime now = LocalDateTime.of(date, LocalTime.now());
			List<Map<String, Object>> results = orderMapper.getValidOrdersIdAndTotal(userId, firstDayOfMonth, lastDayOfMonth);
			Random rand = new Random();
			assert !results.isEmpty();
			for (Map result : results) {
				int orderId = (int) result.get("orderId");
				int messagesLeft = (int) result.get("message") - messageExpenseMapper.countMessagesInOrder(orderId);
				double minutesLeft = (double) result.get("minutes") - callExpenseMapper.sumMinutesInOrder(orderId);
				double localDataLeft = (double) result.get("localData") - dataExpenseMapper.sumDataInOrder(orderId, true);
				double nationalDataLeft = (double) result.get("nationalData") - dataExpenseMapper.sumDataInOrder(orderId, false);
//				System.out.println(messagesLeft + ", " + minutesLeft + ", " + localDataLeft + ", " + nationalDataLeft);
				if (messagesLeft > 0) {
//					System.out.println(messagesLeft);
					messageExpenseMapper.add(new MessageExpense(userId, orderId, now, 0));
				}
				if (minutesLeft > 0) {
					double minutes = rand.nextDouble() * minutesLeft;
//					System.out.println(minutesLeft + ", rand minutes:" + minutes);
					callExpenseMapper.add(new CallExpense(userId, orderId, now, minutes, 0));
				}
				if (localDataLeft > 0) {
					double localData = rand.nextDouble() * localDataLeft;
//					System.out.println(localDataLeft + ", rand localData:" + localData);
					dataExpenseMapper.add(new DataExpense(userId, orderId, now, localData, true, 0));
				}
				if (nationalDataLeft > 0) {
					double nationalData = rand.nextDouble() * nationalDataLeft;
//					System.out.println(nationalDataLeft + ", rand nationalData:" + nationalData);
					dataExpenseMapper.add(new DataExpense(userId, orderId, now, nationalData, false, 0));
				}
			}

			messageExpenseMapper.add(new MessageExpense(userId, null, now, 0.1));
			callExpenseMapper.add(new CallExpense(userId, null, now, rand.nextDouble() * 10, 0));
			double localData = rand.nextDouble() * 30;
			dataExpenseMapper.add(new DataExpense(userId, null, now, localData, true, 2 * localData));
			double nationalData = rand.nextDouble() * 20;
			dataExpenseMapper.add(new DataExpense(userId, null, now, nationalData, false, 5 * nationalData));

			session.commit();
		}
	}

	/**
	 * 11月数据在此生成
	 */
	public static void generateDataOfNovember(SqlSessionFactory sqlSessionFactory) {
		DataGenerator.generateOrders(sqlSessionFactory, LocalDate.of(2018, Month.NOVEMBER, 10));
		DataGenerator.generateExpenses(sqlSessionFactory, 1, LocalDate.of(2018, Month.NOVEMBER, 10));
	}
}
