package com.example.pilipili_android.fragment;

public class FragmentMsg {

    private String whatFragment;
    private String msgString;
    private int msgInt;

    private FragmentMsg(String whatFragment, String msgString) {
        this.whatFragment = whatFragment;
        this.msgString = msgString;
    }

    private FragmentMsg(String whatFragment, String msgString, int msgInt) {
        this.whatFragment = whatFragment;
        this.msgString = msgString;
        this.msgInt = msgInt;
    }

    public static FragmentMsg getInstance(String whatFragment, String msgString) {
        return new FragmentMsg(whatFragment, msgString);
    }

    public static FragmentMsg getInstance(String whatFragment, String msgString, int msgInt) {
        return new FragmentMsg(whatFragment, msgString, msgInt);
    }

    public String getWhatFragment() {
        return whatFragment;
    }

    public void setWhatFragment(String whatFragment) {
        this.whatFragment = whatFragment;
    }

    public String getMsgString() {
        return msgString;
    }

    public void setMsgString(String msgString) {
        this.msgString = msgString;
    }

    public int getMsgInt() {
        return msgInt;
    }

    public void setMsgInt(int msgInt) {
        this.msgInt = msgInt;
    }
}
