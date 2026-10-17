package Group15.ServiceC.reviewsystem;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReviewManager manager = new ReviewManager();
        Scanner scanner = new Scanner(System.in);

        // --- 1. 模擬一筆訂單內容 (List) ---
        // 假設這筆訂單裡有三樣東西
        List<String> myOrder = Arrays.asList("漢堡", "薯條", "玉米濃湯");

        System.out.println("=== 訂單完成 ===");
        System.out.println("您購買了：" + myOrder);
        System.out.println("--------------------------------");

        // --- 2. 詢問使用者是否想評分 ---
        System.out.println("請問您願意為這些餐點個別評分嗎？ (y/n)");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            
            // --- 3. 使用「迴圈」針對每一個食物跑一次流程 ---
            for (String food : myOrder) {
                System.out.println("\n>> 正在評分品項：【" + food + "】");
                
                // A. 輸入分數
                int rating = 0;
                while (true) { // 小迴圈：強迫使用者輸入正確範圍，不然不準走
                    System.out.print("   請給分 (1-5)：");
                    if (scanner.hasNextInt()) {
                        rating = scanner.nextInt();
                        if (rating >= 1 && rating <= 5) break; // 輸入正確，跳出小迴圈
                    }
                    scanner.nextLine(); // 清除錯誤輸入或換行符號
                    System.out.println("   (輸入錯誤，請輸入 1 到 5 的數字)");
                }
                
                scanner.nextLine(); // 吃掉殘留的 Enter 鍵 (重要！)

                // B. 輸入留言
                System.out.print("   請輸入心得：");
                String comment = scanner.nextLine();

                // C. 存入後台
                manager.addReview(new Review(food, rating, comment));
            }
            
            System.out.println("\n所有品項評分完成，感謝您的回饋！");

        } else {
            System.out.println("好的，下次見！");
        }

        // --- 4. 驗證一下資料 ---
        System.out.println("\n=== 後台數據驗證 ===");
        // 隨便抓兩個出來看平均分
        System.out.println("漢堡 平均分：" + manager.getAverageRating("漢堡"));
        System.out.println("薯條 平均分：" + manager.getAverageRating("薯條"));
        System.out.println("玉米濃湯 平均分：" + manager.getAverageRating("玉米濃湯"));
        scanner.close();
    }
}