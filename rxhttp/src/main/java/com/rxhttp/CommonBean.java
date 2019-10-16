package com.rxhttp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * 服务端的CommonBean
 *
 * @author attect
 * @date 2019年2月20日
 */
public class CommonBean implements Parcelable, Cloneable
{
    public int id;
    public String name;

    public String remark;
    public String url;
    public boolean checked;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public CommonBean() {
    }

    public CommonBean(int id) {
        this.id = id;
    }

    public CommonBean(int id, String name) {
        this.id = id;
        this.name = name;
    }


    protected CommonBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        remark = in.readString();
        url = in.readString();
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(remark);
        dest.writeString(url);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonBean> CREATOR = new Creator<CommonBean>() {
        @Override
        public CommonBean createFromParcel(Parcel in) {
            return new CommonBean(in);
        }

        @Override
        public CommonBean[] newArray(int size) {
            return new CommonBean[size];
        }
    };

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        CommonBean bean = new CommonBean();
        bean.setName(name);
        bean.setId(id);
        bean.setRemark(remark);

        bean.setChecked(checked);

        return bean;
    }

    @Override
    public String toString() {
        return name;
    }

}
