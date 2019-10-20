package kr.co.softcampus.memopad;

class Memo {

    String memoContent;
    String memoDate;
    int memoIdx;

    public Memo(String memoContent) {
    }

    public Memo(String memoContent, String memoDate, int memoIdx) {
        this.memoContent = memoContent;
        this.memoDate = memoDate;
        this.memoIdx = memoIdx;
    }

    public Memo(String memoContent, String memoDate) {
        this.memoContent = memoContent;
        this.memoDate = memoDate;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public String getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(String memoDate) {
        this.memoDate = memoDate;
    }

    public int getMemoIdx() {
        return memoIdx;
    }

    public void setMemoIdx(int memoIdx) {
        this.memoIdx = memoIdx;
    }
}
