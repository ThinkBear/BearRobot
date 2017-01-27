package cn.thinkbear.robot.vo;


public class QQResponse<T> {

	
	private int retcode = 0;
	private T result = null;
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
}
