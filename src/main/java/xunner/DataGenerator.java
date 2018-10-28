package xunner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import xunner.bean.*;
import xunner.enums.OrderState;
import xunner.mapper.*;

import java.time.LocalDate;
import java.time.Month;

/**
 * 数据生成
 * <br>
 * created on 2018/10/28
 *
 * @author 巽
 **/
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

	public static void generateOrders(SqlSessionFactory sqlSessionFactory) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			OrderMapper orderMapper = session.getMapper(OrderMapper.class);

			orderMapper.add(new Order(1, 1, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 1, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 2, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 3, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 4, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 5, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 6, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(1, 7, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 1, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 3, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 5, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(2, 7, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(3, 2, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(4, 3, LocalDate.now(), OrderState.EFFECTIVE));
			orderMapper.add(new Order(5, 4, LocalDate.now(), OrderState.EFFECTIVE));
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
}
