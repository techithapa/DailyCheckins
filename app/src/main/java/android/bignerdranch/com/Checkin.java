package android.bignerdranch.com;

import java.util.Date;
import java.util.UUID;

public class Checkin {
    private UUID mId;
    private String mTitle;
    private Date mPlace;
    private Date mDetails;
    private Date mDate;
    private Date mLocation;
    //private boolean mSolved;
    private String mShare;

    public Checkin() {
        this(UUID.randomUUID());
       // mId = UUID.randomUUID();
        //mDate = new Date();
    }
    public Checkin(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    /*public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }*/

    public String getShare() {
        return mShare;
    }
    public void setShare(String share) {
        mShare = share;
    }
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
