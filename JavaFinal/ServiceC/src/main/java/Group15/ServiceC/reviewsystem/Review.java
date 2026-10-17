package Group15.ServiceC.reviewsystem;

public class Review {
    // 屬性 (Attributes)
    private String mealId;  // 餐點 ID
    private int rating;     // 分數 (1-5)
    private String comment; // 留言內容

    // 建構子 (Constructor)
    public Review(String mealId, int rating, String comment) {
        this.mealId = mealId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getter 方法
    public String getMealId() {
        return mealId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    // toString 方法 (方便除錯)
    @Override
    public String toString() {
        return "Review{" +
                "mealId='" + mealId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}