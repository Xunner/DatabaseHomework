package xunner.enums;

/**
 * 套餐订单状态
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public enum OrderState {
	EFFECTIVE("生效"), INVALID_NEXT_MONTH("次月失效"), INVALID("已失效");

	private String value;

	OrderState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
