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
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return [{state, Plan}, {}]
	 */
	List<Map<String, Object>> getValidOrdersMessages(int userId, LocalDate startDate, LocalDate endDate);
}
