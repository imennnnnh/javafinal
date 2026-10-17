package Group15.serviceA;

import Group15.serviceA.model.Meal;
import Group15.serviceA.service.MenuManager;
import Group15.serviceA.exception.MealNotFoundException;
import java.util.Comparator;
import java.util.List;

public class MenuServiceTest {
    public static void main(String[] args) {
        // 初始化邏輯管理員 (自動載入原始 33 項麥當勞菜單) [cite: 3371, 3410]
        MenuManager manager = new MenuManager();

        // --- 1. 展示原有菜單 ---
        System.out.println("========== [Step 1: 展示原始麥當勞菜單] ==========");
        showMenu(manager.getAllMeals());

        // --- 2. 測試新增功能 (新增 吉事漢堡 NT$48) [cite: 3365, 3413] ---
        System.out.println("\n========== [Step 2: 執行新增操作] ==========");
        Meal cheeseBurger = new Meal("M14", "吉事漢堡", 48, "主餐", "經典單層牛肉漢堡");
        manager.addMeal(cheeseBurger);
        System.out.println("成功新增：" + cheeseBurger.getName());

        // --- 3. 測試刪除功能 (刪除 麥香雞 M04) [cite: 3369, 3416] ---
        System.out.println("\n========== [Step 3: 執行刪除操作] ==========");
        try {
            // 根據預設菜單，麥香雞的 ID 為 M04
            manager.deleteMeal("M04");
            System.out.println("成功刪除：編號 M04 (麥香雞)");
        } catch (MealNotFoundException e) {
            System.err.println("刪除失敗：" + e.getMessage()); // 
        }

        // --- 4. 驗證結果並展示新菜單 ---
        System.out.println("\n========== [Step 4: 展示執行後新菜單] ==========");
        List<Meal> updatedList = manager.getAllMeals();
        showMenu(updatedList);
        
        System.out.println("\n[最終檢查]");
        System.out.println("預期品項總數：33 (原始) + 1 (新增) - 1 (刪除) = 33");
        System.out.println("實際品項總數：" + updatedList.size());
        
        // 額外驗證：檢查清單中是否真的包含吉事漢堡
        boolean hasNewItem = updatedList.stream().anyMatch(m -> "M14".equals(m.getId()));
        System.out.println("清單是否包含吉事漢堡 (M14)：" + (hasNewItem ? "是" : "否"));
    }

    /**
     * 輔助方法：使用 Stream API 格式化展示菜單 
     */
    private static void showMenu(List<Meal> meals) {
        System.out.printf("%-6s | %-20s | %-8s | %-6s\n", "編號", "品名", "價格", "分類");
        System.out.println("------------------------------------------------------------");
        meals.stream()
             .sorted(Comparator.comparing(Meal::getCategory).thenComparing(Meal::getId))
             .forEach(m -> System.out.printf("%-6s | %-20s | NT$%4.0f | %-6s\n", 
                      m.getId(), m.getName(), m.getPrice(), m.getCategory()));
    }
}