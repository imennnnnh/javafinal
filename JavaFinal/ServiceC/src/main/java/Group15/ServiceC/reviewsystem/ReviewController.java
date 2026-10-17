package Group15.ServiceC.reviewsystem; // 嚴格保留原本的 Package 名稱

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/reviews") // 設定網址前綴
@CrossOrigin(origins = "*") 
public class ReviewController {

    @Autowired
    private ReviewManager reviewManager;

    // 功能 1: 新增評價 (POST)
    @PostMapping
    public String addReview(@RequestBody Review review) {
        reviewManager.addReview(review);
        return "評價新增成功！餐點ID: " + review.getMealId();
    }

    // 功能 2: 取得平均分 (GET) -> 這就是 Service A 要呼叫的 API
    @GetMapping("/average/{mealId}")
    public double getAverage(@PathVariable String mealId) {
        return reviewManager.getAverageRating(mealId);
    }

    // --- 新增的功能 3: 一鍵清除所有評價 (DELETE) ---
    // 前端呼叫網址: DELETE http://localhost:8080/api/reviews/clear
    @DeleteMapping("/clear")
    public String clearAllReviews() {
        // 呼叫 ReviewManager 裡面的清除方法
        reviewManager.clearAllReviews();
        return "系統訊息：所有評價與留言已成功清除！";
    }
}