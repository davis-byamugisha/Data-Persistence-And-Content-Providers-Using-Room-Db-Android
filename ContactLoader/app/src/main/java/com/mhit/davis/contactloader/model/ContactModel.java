
package com.mhit.davis.contactloader.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ContactModel implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public final static Parcelable.Creator<ContactModel> CREATOR = new Creator<ContactModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ContactModel createFromParcel(Parcel in) {
            ContactModel instance = new ContactModel();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            instance.avatar = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ContactModel[] newArray(int size) {
            return (new ContactModel[size]);
        }

    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(phone);
        dest.writeValue(avatar);
    }

    public int describeContents() {
        return 0;
    }

    /**
     * For remove ContactModel object out of Collection type like ArrayList, HashSet, Vector,....
     */
    public boolean equals(Object o) {
        if (!(o instanceof ContactModel)) {
            return false;
        }

        ContactModel other = (ContactModel) o;
        return (new Gson()).toJson(this).equals((new Gson()).toJson(other));
    }

}