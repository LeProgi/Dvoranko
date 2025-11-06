package fer.leprogi.dvoranko.dto;

public class RequestAddDTO {
	private Long ownerId;
	private String naziv;
	private Integer kapacitet;
	private String opis;
	private Double latitude;
	private Double longitude;
	private String kategorija;
	private String adresa;
	private String mjesto;

	public RequestAddDTO() {}

	// getters / setters
	public Long getOwnerId() { return ownerId; }
	public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
	public String getNaziv() { return naziv; }
	public void setNaziv(String naziv) { this.naziv = naziv; }
	public Integer getKapacitet() { return kapacitet; }
	public void setKapacitet(Integer kapacitet) { this.kapacitet = kapacitet; }
	public String getOpis() { return opis; }
	public void setOpis(String opis) { this.opis = opis; }
	public Double getLatitude() { return latitude; }
	public void setLatitude(Double latitude) { this.latitude = latitude; }
	public Double getLongitude() { return longitude; }
	public void setLongitude(Double longitude) { this.longitude = longitude; }
	public String getKategorija() { return kategorija; }
	public void setKategorija(String kategorija) { this.kategorija = kategorija; }
	public String getAdresa() { return adresa; }
	public void setAdresa(String adresa) { this.adresa = adresa; }
	public String getMjesto() { return mjesto; }
	public void setMjesto(String mjesto) { this.mjesto = mjesto; }
}
