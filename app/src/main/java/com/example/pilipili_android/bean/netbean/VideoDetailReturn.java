package com.example.pilipili_android.bean.netbean;

public class VideoDetailReturn {


    /**
     * code : 200
     * data : {"author":1,"bucket_cover":{"file":"uid-1-2020-06-28-23-06-26-1593356786818.png","guest_key":"STS.NTxa7gxNJpWz3Kn24WFQBmXsv","guest_secret":"DQJQeUrFSvUvcXo7aTNThu1mVMdo16swSwF2QVvFgKBV","security_token":"CAIS8wF1q6Ft5B2yfSjIr5fNKo3TlZFrx5WRMW3f1jQCSt5ugp3YlDz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tleNOsR5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEzAi59fOe3YkgLBPWGxbb2kzZTyNKFjcoVa3Djr/Dn2DDVXBXMJzcCGD+QBWTHFqJwfBv4BGd1vCot63yfW7WpBt45/iVmPVp9cq25h6QoMZWh8xsMRIwE5zLWuze24nluw4rE7/l7Wd0akXRfENmlpgnciKTzz/PM65MSaK0C3Q=="},"coins":0,"collections":0,"comments":0,"danmuku":0,"duration":"0:40","is_collected":false,"is_liked":false,"likes":0,"pv":2,"sign":"Ggghh","time":"2020-06-28 23:08:07","title":"Ycyvyyvyv","type":1,"views":0}
     * message : 详情
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
        /**
         * author : 1
         * bucket_cover : {"file":"uid-1-2020-06-28-23-06-26-1593356786818.png","guest_key":"STS.NTxa7gxNJpWz3Kn24WFQBmXsv","guest_secret":"DQJQeUrFSvUvcXo7aTNThu1mVMdo16swSwF2QVvFgKBV","security_token":"CAIS8wF1q6Ft5B2yfSjIr5fNKo3TlZFrx5WRMW3f1jQCSt5ugp3YlDz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tleNOsR5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEzAi59fOe3YkgLBPWGxbb2kzZTyNKFjcoVa3Djr/Dn2DDVXBXMJzcCGD+QBWTHFqJwfBv4BGd1vCot63yfW7WpBt45/iVmPVp9cq25h6QoMZWh8xsMRIwE5zLWuze24nluw4rE7/l7Wd0akXRfENmlpgnciKTzz/PM65MSaK0C3Q=="}
         * coins : 0
         * collections : 0
         * comments : 0
         * danmuku : 0
         * duration : 0:40
         * is_collected : false
         * is_liked : false
         * likes : 0
         * pv : 2
         * sign : Ggghh
         * time : 2020-06-28 23:08:07
         * title : Ycyvyyvyv
         * type : 1
         * views : 0
         */

        private int author;
        private BucketCoverBean bucket_cover;
        private int coins;
        private int collections;
        private int comments;
        private int danmuku;
        private String duration;
        private boolean is_collected;
        private boolean is_liked;
        private int likes;
        private int pv;
        private String sign;
        private String time;
        private String title;
        private int type;
        private int views;

        public int getAuthor() {
            return author;
        }

        public void setAuthor(int author) {
            this.author = author;
        }

        public BucketCoverBean getBucket_cover() {
            return bucket_cover;
        }

        public void setBucket_cover(BucketCoverBean bucket_cover) {
            this.bucket_cover = bucket_cover;
        }

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }

        public int getCollections() {
            return collections;
        }

        public void setCollections(int collections) {
            this.collections = collections;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getDanmuku() {
            return danmuku;
        }

        public void setDanmuku(int danmuku) {
            this.danmuku = danmuku;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public boolean isIs_collected() {
            return is_collected;
        }

        public void setIs_collected(boolean is_collected) {
            this.is_collected = is_collected;
        }

        public boolean isIs_liked() {
            return is_liked;
        }

        public void setIs_liked(boolean is_liked) {
            this.is_liked = is_liked;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public static class BucketCoverBean {
            /**
             * file : uid-1-2020-06-28-23-06-26-1593356786818.png
             * guest_key : STS.NTxa7gxNJpWz3Kn24WFQBmXsv
             * guest_secret : DQJQeUrFSvUvcXo7aTNThu1mVMdo16swSwF2QVvFgKBV
             * security_token : CAIS8wF1q6Ft5B2yfSjIr5fNKo3TlZFrx5WRMW3f1jQCSt5ugp3YlDz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tleNOsR5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEzAi59fOe3YkgLBPWGxbb2kzZTyNKFjcoVa3Djr/Dn2DDVXBXMJzcCGD+QBWTHFqJwfBv4BGd1vCot63yfW7WpBt45/iVmPVp9cq25h6QoMZWh8xsMRIwE5zLWuze24nluw4rE7/l7Wd0akXRfENmlpgnciKTzz/PM65MSaK0C3Q==
             */

            private String file;
            private String guest_key;
            private String guest_secret;
            private String security_token;

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public String getGuest_key() {
                return guest_key;
            }

            public void setGuest_key(String guest_key) {
                this.guest_key = guest_key;
            }

            public String getGuest_secret() {
                return guest_secret;
            }

            public void setGuest_secret(String guest_secret) {
                this.guest_secret = guest_secret;
            }

            public String getSecurity_token() {
                return security_token;
            }

            public void setSecurity_token(String security_token) {
                this.security_token = security_token;
            }
        }
    }
}
