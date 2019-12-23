package model;

public class HiNewVO {
	private String name;
	private String ID;
	private String Password;
	private int Year;
	private String Month;
	private String Day;
	private String Phone;
	private int age;
	public HiNewVO() {
		super();
	}

	public HiNewVO(String name, String iD, String password, int year, String month, String day, String phone,int age) {
		super();
		this.name = name;
		ID = iD;
		Password = password;
		Year = year;
		Month = month;
		Day = day;
		Phone = phone;
		this.age=age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "HiNewVO [name=" + name + ", ID=" + ID + ", Password=" + Password + ", Year=" + Year + ", Month=" + Month
				+ ", Day=" + Day + ", Phone=" + Phone + ", age=" + age + "]";
	}

}
