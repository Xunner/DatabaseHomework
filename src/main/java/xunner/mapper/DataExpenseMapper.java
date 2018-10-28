package xunner.mapper;

import xunner.bean.DataExpense;

import java.time.LocalDateTime;

/**
 * DataExpense映射接口
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public interface DataExpenseMapper {
	DataExpense getById(int dataExpenseId);

	double sumDataInOrder(int orderId, boolean isLocal);

	double sumDataWithoutOrder(int userId, boolean isLocal, LocalDateTime startTime, LocalDateTime endTime);

	int add(DataExpense dataExpense);
}
