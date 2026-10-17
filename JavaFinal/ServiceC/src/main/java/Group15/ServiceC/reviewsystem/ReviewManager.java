package Group15.ServiceC.reviewsystem;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class ReviewManager {
    
    private List<Review> reviews = new ArrayList<>();
    private static final String FILE_NAME = "reviews.txt"; 

    public ReviewManager() {
        loadReviewsFromFile();
    }

    public void addReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            System.out.println("錯誤：評分必須在 1 到 5 之間！");
            return;
        }
        reviews.add(review);
        saveReviewToFile(review); 
    }

    public double getAverageRating(String mealId) {
        OptionalDouble average = reviews.stream()
                .filter(r -> r.getMealId().equals(mealId))
                .mapToInt(Review::getRating)
                .average();
        return average.orElse(0.0);
    }

    public List<String> getComments(String mealId) {
        return reviews.stream()
                .filter(r -> r.getMealId().equals(mealId))
                .map(Review::getComment)
                .collect(Collectors.toList());
    }

    // --- 新增的功能：一鍵清除所有紀錄 ---
    public void clearAllReviews() {
        // 1. 清空記憶體中的 List
        reviews.clear();

        // 2. 清空檔案內容
        // FileWriter 的第二個參數設為 false (或不寫)，代表「覆寫模式」(Overwrite)
        // 這樣開啟檔案後什麼都不寫直接關閉，就會把檔案內容全部清空
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            writer.write(""); // 寫入空字串，確保清空
        } catch (IOException e) {
            System.out.println("清除檔案失敗：" + e.getMessage());
        }
        
        System.out.println("系統訊息：所有評分與留言已全部清除。");
    }

    // --- 檔案處理方法 ---

    private void saveReviewToFile(Review review) {
        // 這裡維持 true (Append模式)，因為是新增單筆
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = review.getMealId() + "###" + review.getRating() + "###" + review.getComment();
            writer.write(line);
            writer.newLine(); 
        } catch (IOException e) {
            System.out.println("存檔失敗：" + e.getMessage());
        }
    }

    private void loadReviewsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("###");
                if (parts.length == 3) {
                    String mealId = parts[0];
                    int rating = Integer.parseInt(parts[1]);
                    String comment = parts[2];
                    reviews.add(new Review(mealId, rating, comment));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("讀取檔案失敗或格式錯誤：" + e.getMessage());
        }
    }
}