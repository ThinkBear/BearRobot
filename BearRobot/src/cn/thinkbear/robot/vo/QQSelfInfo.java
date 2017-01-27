package cn.thinkbear.robot.vo;

/**
 * 
 * {"retcode":0,
 * 		"result":{
 * 			"birthday":{"month":9,"year":1980,"day":7},
 * 			"face":663,
 * 			"phone":"",
 * 			"occupation":"",
 * 			"allow":1,
 * 			"college":"",
 * 			"uin":740578998,
 * 			"blood":0,
 * 			"constel":8,
 * 			"lnick":"",
 * 			"vfwebqq":"5d32e053abd6fb2a92beb90400a605f8def83dcfbed139e38ec1696d3966f2ad6800595427c08054",
 * 			"homepage":"",
 * 			"vip_info":0,
 * 			"city":"汕头",
 * 			"country":"中国",
 * 			"personal":"",
 * 			"shengxiao":9,
 * 			"nick":"/xmao",
 * 			"email":"",
 * 			"province":"广东",
 * 			"account":740578998,
 * 			"gender":"male",
 * 			"mobile":""}
 * }
 *
 * @author ThinkBear
 * @date 2016年11月10日 下午1:11:19
 */
public class QQSelfInfo {
	
	private Birthday birthday;
	private int face;
	private String phone;
	private String occupation;
	private int allow;
	private String college;
	private long uin;
	private int blood;
	private int constel;
	private String lnick;
	private String vfwebqq;
	private String homepage;
	private int vip_info;
	private String city;
	private String country;
	private String personal;
	private int shengxiao;
	private String nick;
	private String email;
	private String province;
	private long account;
	private String gender;
	private String mobile;
	

	public Birthday getBirthday() {
		return birthday;
	}


	public void setBirthday(Birthday birthday) {
		this.birthday = birthday;
	}


	public int getFace() {
		return face;
	}


	public void setFace(int face) {
		this.face = face;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public int getAllow() {
		return allow;
	}


	public void setAllow(int allow) {
		this.allow = allow;
	}


	public String getCollege() {
		return college;
	}


	public void setCollege(String college) {
		this.college = college;
	}


	public long getUin() {
		return uin;
	}


	public void setUin(long uin) {
		this.uin = uin;
	}


	public int getBlood() {
		return blood;
	}


	public void setBlood(int blood) {
		this.blood = blood;
	}


	public int getConstel() {
		return constel;
	}


	public void setConstel(int constel) {
		this.constel = constel;
	}


	public String getLnick() {
		return lnick;
	}


	public void setLnick(String lnick) {
		this.lnick = lnick;
	}


	public String getVfwebqq() {
		return vfwebqq;
	}


	public void setVfwebqq(String vfwebqq) {
		this.vfwebqq = vfwebqq;
	}


	public String getHomepage() {
		return homepage;
	}


	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}


	public int getVip_info() {
		return vip_info;
	}


	public void setVip_info(int vip_info) {
		this.vip_info = vip_info;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPersonal() {
		return personal;
	}


	public void setPersonal(String personal) {
		this.personal = personal;
	}


	public int getShengxiao() {
		return shengxiao;
	}


	public void setShengxiao(int shengxiao) {
		this.shengxiao = shengxiao;
	}


	public String getNick() {
		return nick;
	}


	public void setNick(String nick) {
		this.nick = nick;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public long getAccount() {
		return account;
	}


	public void setAccount(long account) {
		this.account = account;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	private class Birthday{
		private int month;
		private int year;
		private int day;
		public int getMonth() {
			return month;
		}
		public void setMonth(int month) {
			this.month = month;
		}
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		public int getDay() {
			return day;
		}
		public void setDay(int day) {
			this.day = day;
		}
		
		
	}
}
