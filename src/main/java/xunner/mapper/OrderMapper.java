package xunner.mapper;

import xunner.bean.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Order映射接口
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public interface OrderMapper {
	Order getById(int orderId);

	/**
	 * 查询用户一段时间内的订单信息（无论是否生效）
	 *
	 * @param userId 用户id
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return [{state, date, Plan}, {...}, ...]
	 */
	List<Map<String, Object>> getOrdersByUserIdAndDates(int userId, LocalDate startDate, LocalDate endDate);

	int add(Order order);

	/**
	 * 只能修改订单状态
	 *
	 * @param order 已修改的订单
	 * @return 涉及行数
	 */
	int update(Order order);

	/**
	 * 查询用户当前生效的订单信息
	 *
	 * @param userId 用户id
	 * @param startDate 开始日期（月初）
	 * @param endDate 结束日期（月底）
	 * @return [{state:xx, orderId:xx, Plan:xx}, {...}, ...]
	 */
	List<Map<String, Object>> getValidOrdersMessages(int userId, LocalDate startDate, LocalDate endDate);

	/**
	 * 查询用户当前生效的订单id和套餐总量信息
	 *
	 * @param userId 用户id
	 * @param startDate 开始日期（月初）
	 * @param endDate 结束日期（月底）
	 * @return [{orderId:xx, minutes:xx, message:xx, localData:xx, nationalData:xx}, {...}, ...]
	 */
	List<Map<String, Object>> getValidOrdersIdAndTotal(int userId, LocalDate startDate, LocalDate endDate);
}
