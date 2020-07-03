package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class UserVideoReturn {

    /**
     * code : 200
     * data : {"video_list":[{"bucket_cover":{"file":"uid-2-2020-06-30-23-36-00-1593531357218.png","guest_key":"STS.NTtR9M4MdTksD1UE8JkaJcagS","guest_secret":"sxSqg1fsc3KB6dn4pH12MCE3mv73bEp3e953GH5tkpc","security_token":"CAIS8wF1q6Ft5B2yfSjIr5fBGYP52ZJF46mYRhfkoTgfZ+5mjKTMsTz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/th/1ZIJ5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFoSgqHxRAGToaaaUwyoVSePaEtIePOGRPi3De4a8ZhZJf9QEVLAVfYplfWQ+ERLkE0KJc6MAumhrGMn5TGfpjE3/KthkG5NXsZkqsKiDDRsJ53MvnJG7vZqV8Cfz3cZAVbYDYDn5a3IK3yhmCer0ZLSin+iMJNG5BlPsU6UmJU1w=="},"danmuku":0,"duration":"0:52","likes":0,"pv":23,"time":"2020-06-30 23:37:44","title":"she is lxw","type":2,"views":1}]}
     * message : 获取投稿成功
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
        private List<VideoListBean> video_list;

        public List<VideoListBean> getVideo_list() {
            return video_list;
        }

        public void setVideo_list(List<VideoListBean> video_list) {
            this.video_list = video_list;
        }

        public static class VideoListBean {
            /**
             * bucket_cover : {"file":"uid-2-2020-06-30-23-36-00-1593531357218.png","guest_key":"STS.NTtR9M4MdTksD1UE8JkaJcagS","guest_secret":"sxSqg1fsc3KB6dn4pH12MCE3mv73bEp3e953GH5tkpc","security_token":"CAIS8wF1q6Ft5B2yfSjIr5fBGYP52ZJF46mYRhfkoTgfZ+5mjKTMsTz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/th/1ZIJ5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFoSgqHxRAGToaaaUwyoVSePaEtIePOGRPi3De4a8ZhZJf9QEVLAVfYplfWQ+ERLkE0KJc6MAumhrGMn5TGfpjE3/KthkG5NXsZkqsKiDDRsJ53MvnJG7vZqV8Cfz3cZAVbYDYDn5a3IK3yhmCer0ZLSin+iMJNG5BlPsU6UmJU1w=="}
             * danmuku : 0
             * duration : 0:52
             * likes : 0
             * pv : 23
             * time : 2020-06-30 23:37:44
             * title : she is lxw
             * type : 2
             * views : 1
             */

            private BucketCoverBean bucket_cover;
            private int danmuku;
            private String duration;
            private int likes;
            private int pv;
            private String time;
            private String title;
            private int type;
            private int views;

            public BucketCoverBean getBucket_cover() {
                return bucket_cover;
            }

            public void setBucket_cover(BucketCoverBean bucket_cover) {
                this.bucket_cover = bucket_cover;
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
                 * file : uid-2-2020-06-30-23-36-00-1593531357218.png
                 * guest_key : STS.NTtR9M4MdTksD1UE8JkaJcagS
                 * guest_secret : sxSqg1fsc3KB6dn4pH12MCE3mv73bEp3e953GH5tkpc
                 * security_token : CAIS8wF1q6Ft5B2yfSjIr5fBGYP52ZJF46mYRhfkoTgfZ+5mjKTMsTz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/th/1ZIJ5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFoSgqHxRAGToaaaUwyoVSePaEtIePOGRPi3De4a8ZhZJf9QEVLAVfYplfWQ+ERLkE0KJc6MAumhrGMn5TGfpjE3/KthkG5NXsZkqsKiDDRsJ53MvnJG7vZqV8Cfz3cZAVbYDYDn5a3IK3yhmCer0ZLSin+iMJNG5BlPsU6UmJU1w==
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
}
