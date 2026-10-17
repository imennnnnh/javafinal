package Group15.serviceA.controller;

import Group15.serviceA.model.Meal;
import Group15.serviceA.service.MenuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuManager menuManager;
    
    @GetMapping
    public List<Meal> getMenu() { return menuManager.getAllMeals(); }
    
    // 根據 ID 查詢單一餐點 (Service B 呼叫)
    @GetMapping("/{id}")
    public ResponseEntity<Meal> getById(@PathVariable String id) {
        try {
            Meal meal = menuManager.getMeal(id);
            if (meal != null) {
                // 找到了：回傳 200 OK + 餐點資料
                return ResponseEntity.ok(meal);
            } else {
                // 沒找到 (雖然 getMeal 會丟例外，但多防一層)：回傳 404 Not Found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 發生例外 (例如找不到 ID)：回傳 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public String add(@RequestBody Meal meal) {
        menuManager.addMeal(meal);
        return "餐點 [" + meal.getName() + "] 新增成功！";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        try {
            menuManager.deleteMeal(id);
            return "餐點編號 " + id + " 已刪除。";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/search")
    public List<Meal> search(@RequestParam String name) {
        return menuManager.searchByName(name);
    }
}