package xunner.bean;

import lombok.Data;

/**
 * 套餐
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
@Data
public class Plan {
	private int pid;

	/** 套餐名称 */
	private String name;

	/** 价格 */
	private double price;

	/** 通话时长（分钟） */
	private double minutes;

	/** 短信（条） */
	private int message;

	/** 流量（M） */
	private double data;

	/** 废除标记 */
	private boolean abolished;
}
