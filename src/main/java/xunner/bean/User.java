package xunner.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户
 * <br>
 * created on 2018/10/24
 *
 * @author 巽
 **/
@Data
@NoArgsConstructor
public class User {
	private int userId;

	/** 手机号 */
	private String number;

	/** 账户余额 */
	private double balance;

	public User(String number, double balance) {
		this.number = number;
		this.balance = balance;
	}
}
