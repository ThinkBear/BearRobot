package cn.thinkbear.robot.vo;

import java.util.List;

public class QQResponseList<T> {

	
	private int retcode = 0;
	private List<T> result = null;
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
	
}
