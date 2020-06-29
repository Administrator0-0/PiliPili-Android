package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class GetRecommendVideoListReturn {

    /**
     * code : 200
     * data : {"video_list":[3,7,13,6,12,2,9,4]}
     * message : 获取视频成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<Integer> video_list;

        public List<Integer> getVideo_list() {
            return video_list;
        }

        public void setVideo_list(List<Integer> video_list) {
            this.video_list = video_list;
        }
    }
}
