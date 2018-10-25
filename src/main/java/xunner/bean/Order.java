package xunner.bean;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用户订购套餐订单
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
@Data
public class Order {
	private int orderId;

	/** 用户id */
	private int userId;

	/** 套餐id */
	private int planId;

	/** 订购日期（年月） */
	private LocalDate date;

	/** 退订标记 */
	private boolean isCanceled;
}
