package Group15.serviceA.service;

import Group15.serviceA.model.Meal;
import Group15.serviceA.exception.MealNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuManager {
    
    // 使用 HashMap 儲存菜單資料
    private Map<String, Meal> menuMap = new HashMap<>();

    // 注入 WebClient 用來呼叫 Service C (Review Service)
    @Autowired 
    private WebClient reviewWebClient;

    public MenuManager() {
        initFullMenu();
    }

    private void initFullMenu() {
        // A. 主餐
        addMeal(new Meal("M01", "大麥克", 81, "主餐", "經典雙層牛肉堡"));
        addMeal(new Meal("M02", "雙層牛肉吉事堡", 75, "主餐", "雙層牛肉配濃郁起司"));
        addMeal(new Meal("M03", "嫩煎雞腿堡", 86, "主餐", "不裹粉嫩煎雞腿"));
        addMeal(new Meal("M04", "麥香雞", 51, "主餐", "經典雞肉漢堡"));
        addMeal(new Meal("M05", "麥香魚", 60, "主餐", "嚴選魚排配塔塔醬"));
        addMeal(new Meal("M06", "勁辣雞腿堡", 81, "主餐", "辛辣過癮雞腿排"));
        addMeal(new Meal("M07", "BLT安格斯牛肉堡", 125, "主餐", "厚實安格斯牛"));
        addMeal(new Meal("M08", "BLT嫩煎雞腿堡", 125, "主餐", "嫩煎雞腿配培根"));
        addMeal(new Meal("M09", "帕瑪森安格斯牛肉堡", 130, "主餐", "高級帕瑪森起司"));
        addMeal(new Meal("M10", "帕瑪森主廚雞腿堡", 130, "主餐", "主廚特製雞腿排"));
        addMeal(new Meal("M11", "麥克雞塊(6塊)", 69, "主餐", "經典雞塊"));
        addMeal(new Meal("M12", "麥克雞塊(10塊)", 110, "主餐", "大份量雞塊"));
        addMeal(new Meal("M13", "麥脆雞腿(2塊)", 129, "主餐", "酥脆雞腿"));

        // B. 點心
        addMeal(new Meal("S01", "薯條(小)", 40, "點心", "小薯"));
        addMeal(new Meal("S02", "薯條(中)", 50, "點心", "中薯"));
        addMeal(new Meal("S03", "薯條(大)", 66, "點心", "大薯"));
        addMeal(new Meal("S04", "麥克雞塊(4塊)", 49, "點心", "4塊雞塊"));
        addMeal(new Meal("S05", "勁辣香雞翅(1對)", 50, "點心", "1對雞翅"));
        addMeal(new Meal("S06", "勁辣香雞翅(3對)", 131, "點心", "3對雞翅"));
        addMeal(new Meal("S07", "麥脆雞腿(1塊)", 68, "點心", "1塊雞腿"));
        addMeal(new Meal("S08", "蘋果派", 41, "點心", "甜點派"));
        addMeal(new Meal("S09", "OREO冰炫風", 60, "點心", "經典冰炫風"));
        addMeal(new Meal("S10", "蛋捲冰淇淋", 18, "點心", "經典冰品"));
        addMeal(new Meal("S11", "大蛋捲冰淇淋", 30, "點心", "大份量冰品"));

        // C. 飲料
        addMeal(new Meal("D01", "焦糖奶茶(冰)", 69, "飲料", "冰焦糖風味"));
        addMeal(new Meal("D02", "冰蜂蜜奶茶", 69, "飲料", "蜂蜜風味"));
        addMeal(new Meal("D03", "冰蜂蜜紅茶", 59, "飲料", "清爽蜂蜜"));
        addMeal(new Meal("D04", "可口可樂", 38, "飲料", "經典可樂"));
        addMeal(new Meal("D05", "零卡可樂", 38, "飲料", "零熱量"));
        addMeal(new Meal("D06", "雪碧", 38, "飲料", "檸檬汽水"));
        addMeal(new Meal("D07", "無糖綠茶", 38, "飲料", "清爽解膩"));
        addMeal(new Meal("D08", "檸檬紅茶", 38, "飲料", "酸甜解渴"));
    }

    // --- 修改後的 getMeal 方法：取得餐點時，順便去 Service C 抓分數 ---
    public Meal getMeal(String id) throws MealNotFoundException {
        if (!menuMap.containsKey(id)) throw new MealNotFoundException("找不到編號 " + id);
        
        Meal meal = menuMap.get(id);

        // 呼叫 Service C 的邏輯
        try {
            // 這邊使用同步呼叫 (block)
            Double rating = reviewWebClient.get()
                    .uri("/average/" + id) // 對應 Service C 的網址
                    .retrieve()
                    .bodyToMono(Double.class) // 把回應轉成 Double
                    .block(); 
            
            // 把抓到的分數塞進 Meal 物件
            if (rating != null) {
                meal.setAverageRating(rating);
            }
        } catch (Exception e) {
            // 如果連不上 Service C，就印出錯誤，但不要讓程式當掉，分數預設 0.0
            System.out.println("無法取得評分：" + e.getMessage());
            meal.setAverageRating(0.0);
        }

        return meal;
    }

    // --- 其他基本功能 ---

    public void addMeal(Meal meal) { 
        menuMap.put(meal.getId(), meal); 
    }

    public List<Meal> getAllMeals() { 
        // 注意：目前這個方法還沒有去抓分數，只會回傳基本資料。
        // 如果想要列表也顯示分數，這裡也需要改寫 (但會比較慢，因為要跑迴圈)。
        return new ArrayList<>(menuMap.values()); 
    }

    public void deleteMeal(String id) throws MealNotFoundException {
        if (!menuMap.containsKey(id)) throw new MealNotFoundException("刪除失敗：找不到編號 " + id);
        menuMap.remove(id);
    }

    public List<Meal> searchByName(String keyword) {
        return menuMap.values().stream()
                .filter(m -> m.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Meal> getByCategory(String category) {
        return menuMap.values().stream()
                .filter(m -> m.getCategory().equals(category))
                .collect(Collectors.toList());
    }

} // 整個類別在這裡結束