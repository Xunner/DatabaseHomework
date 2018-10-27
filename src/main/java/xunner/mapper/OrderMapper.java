package xunner.mapper;

import xunner.bean.Order;

import java.time.LocalDate;
import java.util.List;

/**
 * Order映射接口
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public interface OrderMapper {
	Order getById(int orderId);

	List<Order> getOrdersByUserIdAndDates(int userId, LocalDate startDate, LocalDate endDate);

	int add(Order order);

	/**
	 * 只能修改订单状态
	 *
	 * @param order 已修改的订单
	 * @return 涉及行数
	 */
	int update(Order order);
}
