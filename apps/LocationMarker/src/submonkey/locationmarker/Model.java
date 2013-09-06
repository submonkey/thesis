package submonkey.locationmarker;

public class Model {
	
	private long id;
	private String area1;
	private String area2;
	private String area3;
	private double latitude;
	private double longitude;

	public Model() {
		
	}

	public Model(long id, String area1, String area2, String area3,
			double latitude, double longitude) {
		super();
		this.id = id;
		this.area1 = area1;
		this.area2 = area2;
		this.area3 = area3;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getArea1() {
		return area1;
	}
	public void setArea1(String area1) {
		this.area1 = area1;
	}
	public String getArea2() {
		return area2;
	}
	public void setArea2(String area2) {
		this.area2 = area2;
	}
	public String getArea3() {
		return area3;
	}
	public void setArea3(String area3) {
		this.area3 = area3;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "Location [id=" + id + ", area1=" + area1 + ", area2=" + area2
				+ ", area3=" + area3 + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

}
