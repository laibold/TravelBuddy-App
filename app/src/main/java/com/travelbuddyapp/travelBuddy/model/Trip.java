package com.travelbuddyapp.travelBuddy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.travelbuddyapp.travelBuddy.R;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Travels contain a UserList and a financesManager
 * @author Marvin
 *
 */
public class Trip implements Parcelable {
	private String name;
	//private UserList userList;
	private LocalDate startDate;
	private LocalDate endDate;
	//private FinancesManager financesMgr = new FinancesManager();
	//private StopManager stopMgr = new StopManager();
	private int imageResource;

	/**
	 * New Trip
	 * @param name Name of the Trip
	 * @param userList UserList of the partaking Users
	 * @param startDate Starting Date of the Trip
	 * @param endDate End Date of the Trip
	 */
	public Trip(String name, /*UserList userList,*/ String startDate, String endDate, int imageResource) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		this.name = name;
		//this.userList = userList;

		this.startDate = LocalDate.parse(startDate, formatter);
		this.endDate = LocalDate.parse(startDate, formatter);

		this.imageResource = imageResource;
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



	//Parcel-Shit

	protected Trip(Parcel in) {
		name = in.readString();
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
