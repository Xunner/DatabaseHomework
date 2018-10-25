package xunner.bean;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 流量开支（每一笔）
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
@Data
public class DataExpense {
	private int dataExpenseId;

	/** 用户id */
	private int userId;

	/** 使用套餐id（若无则为空） */
	private Integer orderId;

	/** 时间（精确到毫秒）*/
	private LocalDateTime time;

	/** 使用流量（M） */
	private double data;

	/** 是否为本地流量（否则为全国流量） */
	private boolean isLocal;

	/** 资费（元） */
	private double cost;
}
