package xunner.mapper;

import xunner.bean.CallExpense;

import java.time.LocalDateTime;

/**
 * CallExpense映射接口
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public interface CallExpenseMapper {
	CallExpense getById(int callExpenseId);

	double sumMinutesInOrder(int orderId);

	double sumMinutesWithoutOrder(int userId, LocalDateTime startTime, LocalDateTime endTime);

	int add(CallExpense callExpense);
}
