package com.travelbuddyapp.travelBuddy.model.trip;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Trips contain a UserList and a financesManager
 * @author Marvin
 *
 */
@Entity
public class Trip implements Parcelable {
	@ColumnInfo
	@PrimaryKey(autoGenerate = true)
	private int id;

	@ColumnInfo
	private String name;

	@ColumnInfo
	private TripType tripType;

	//private UserList userList;

	@ColumnInfo
	private LocalDate startDate;

	@ColumnInfo
	private LocalDate endDate;

	//private FinancesManager financesMgr = new FinancesManager();
	//private StopManager stopMgr = new StopManager();

	@ColumnInfo
	private int imageResource;

	/**
	 * New Trip
	 * @param name Name of the Trip
	 * @param userList UserList of the partaking Users
	 * @param startDate Starting Date of the Trip
	 * @param endDate End Date of the Trip
	 */
	public Trip(String name, /*UserList userList,*/ TripType tripType, String startDate, String endDate, int imageResource) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		this.name = name;
		//this.userList = userList;

		this.tripType = tripType;

		this.startDate = LocalDate.parse(startDate, formatter);
		this.endDate = LocalDate.parse(startDate, formatter);

		this.imageResource = imageResource;
	}

	public Trip(){
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public FinancesManager getFinancesManager() {
		return this.financesMgr;
	}*/
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TripType getTripType() {
		return tripType;
	}

	public void setTripType(TripType tripType) {
		this.tripType = tripType;
	}

	/*public UserList getUserList() {
		return userList;
	}

	public void setUserList(UserList userList) {
		this.userList = userList;
	}*/

	/*public boolean addUser(User user) {
		return this.userList.addUser(user);
	}

	public boolean removeUser(User user) {
		return this.userList.removeUser(user);
	}*/

	/**
	 * @return the StopManager
	 */
	/*public StopManager getStopMgr() {
		return stopMgr;
	}*/

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 
	 * @return Total amount of days inclusive every day
	 */
	public int getTotalDays() {
		Period period = Period.between(startDate, endDate);
		return period.getDays() + 1;
	}

	public int getImageResource() {
		return imageResource;
	}

	public void setImageResource(int imageResource) {
		this.imageResource = imageResource;
	}

	//Parcel-Shit
	protected Trip(Parcel in) {
		name = in.readString();
		tripType = TripType.values()[in.readInt()];
		startDate = (LocalDate) in.readValue(LocalDate.class.getClassLoader());
		endDate = (LocalDate) in.readValue(LocalDate.class.getClassLoader());
		imageResource = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(tripType.ordinal());
		dest.writeValue(startDate);
		dest.writeValue(endDate);
		dest.writeInt(imageResource);
	}

	public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
		@Override
		public Trip createFromParcel(Parcel in) {
			return new Trip(in);
		}

		@Override
		public Trip[] newArray(int size) {
			return new Trip[size];
		}
	};
}
