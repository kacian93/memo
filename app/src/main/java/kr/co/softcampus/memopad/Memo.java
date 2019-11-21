package kr.co.softcampus.memopad;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Memo implements Parcelable {

    String memoContent;
    Date memoDate;
    int memoIdx;

    //デターをもらったActivityで復元する
    public static  final Creator<Memo> CREATOR = new Creator<Memo>() {
        @Override
        public Memo createFromParcel(Parcel source) {
            Memo memo = new Memo();
            memo.memoContent = source.readString();
            try {
                memo.memoDate = new SimpleDateFormat("yyyy-MM-dd\nhh:mm:ss").parse(source.readString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            memo.memoIdx = source.readInt();
            return  memo;
        }

        @Override
        public Memo[] newArray(int size) {
            return new Memo[size];
        }
    };


    public Memo() {

    }

    public Memo(String memoContent, Date memoDate, int memoIdx) {
        this.memoContent = memoContent;
        this.memoDate = memoDate;
        this.memoIdx = memoIdx;
    }

    public Memo(String memoContent, Date memoDate) {
        this.memoContent = memoContent;
        this.memoDate = memoDate;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public Date getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(Date memoDate) {
        this.memoDate = memoDate;
    }

    public int getMemoIdx() {
        return memoIdx;
    }

    public void setMemoIdx(int memoIdx) {
        this.memoIdx = memoIdx;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    //objectをIntentに入ったとき自動で呼び出せるmethod
    //Parcelオブジェクトは復元に必要な情報が入る
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(memoContent);
        dest.writeString(memoDate.toString());
        dest.writeInt(memoIdx);
    }
}
