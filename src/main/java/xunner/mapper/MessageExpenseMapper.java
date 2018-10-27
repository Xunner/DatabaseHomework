package xunner.mapper;

import xunner.bean.MessageExpense;

import java.time.LocalDateTime;

/**
 * MessageExpense映射接口
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public interface MessageExpenseMapper {
	MessageExpense getById(int messageExpenseId);

	int countMessagesInOrder(int orderId);

	int countMessageWithoutOrder(int userId, LocalDateTime startTime, LocalDateTime endTime);
}
