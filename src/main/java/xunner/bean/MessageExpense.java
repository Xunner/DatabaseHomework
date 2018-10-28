package xunner.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 短信开支（每条）
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
@Data
@NoArgsConstructor
public class MessageExpense {
	private int messageExpenseId;

	/** 用户id */
	private int userId;

	/** 使用套餐id（若无则为空） */
	private Integer orderId;

	/** 时间（精确到毫秒） */
	private LocalDateTime time;

	/** 资费（元） */
	private double cost;

	public MessageExpense(int userId, Integer orderId, LocalDateTime time, double cost) {
		this.userId = userId;
		this.orderId = orderId;
		this.time = time;
		this.cost = cost;
	}
}
