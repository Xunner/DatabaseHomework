package xunner.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 话费开支（每一笔）
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
@Data
@NoArgsConstructor
public class CallExpense {
	private int callExpenseId;

	/** 用户id */
	private int userId;

	/** 使用套餐id（若无则为空） */
	private Integer orderId;

	/** 时间（精确到毫秒）*/
	private LocalDateTime time;

	/** 时长（分钟） */
	private double minutes;

	/** 资费（元） */
	private double cost;

	public CallExpense(int userId, Integer orderId, LocalDateTime time, double minutes, double cost) {
		this.userId = userId;
		this.orderId = orderId;
		this.time = time;
		this.minutes = minutes;
		this.cost = cost;
	}
}
