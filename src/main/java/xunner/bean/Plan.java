package xunner.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 套餐
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
@Data
@NoArgsConstructor
public class Plan {
	private int planId;

	/** 套餐名称 */
	private String name;

	/** 价格 */
	private double price;

	/** 通话时长（分钟） */
	private double minutes;

	/** 短信（条） */
	private int message;

	/** 本地流量（M） */
	private double localData;

	/** 国内流量（M） */
	private double nationalData;

	/** 废除标记 */
	private boolean isAbolished;

	public Plan(String name, double price, double minutes, int message, double localData, double nationalData, boolean isAbolished) {
		this.name = name;
		this.price = price;
		this.minutes = minutes;
		this.message = message;
		this.localData = localData;
		this.nationalData = nationalData;
		this.isAbolished = isAbolished;
	}
}
