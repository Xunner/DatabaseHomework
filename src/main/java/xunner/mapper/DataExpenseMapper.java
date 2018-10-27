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

	double sumLocalDataInOrder(int orderId);

	double sumNationalDataInOrder(int orderId);

	double sumLocalDataWithoutOrder(int userId, LocalDateTime startTime, LocalDateTime endTime);

	double sumNationalDataWithoutOrder(int userId, LocalDateTime startTime, LocalDateTime endTime);
}
