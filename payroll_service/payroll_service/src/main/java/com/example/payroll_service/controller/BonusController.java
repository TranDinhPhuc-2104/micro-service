package com.example.payroll_service.controller;

import com.example.payroll_service.entity.Bonus;
import com.example.payroll_service.entity.Salary;
import com.example.payroll_service.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bonuses")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @GetMapping
    public List<Bonus> getAllBonuses() {
        return bonusService.getAllBonuses();
    }

    @GetMapping("/{id}")
    public Bonus getBonusById(@PathVariable Long id) {
        return bonusService.getBonusById(id);
    }

    @PostMapping
    public Bonus createBonus(@RequestBody Bonus bonus) {
        return bonusService.createBonus(bonus);
    }

    @PutMapping("/{id}")
    public Bonus updateBonus(
            @PathVariable Long id,
            @RequestBody Bonus bonus) {

        return bonusService.updateBonus(id, bonus);
    }

    @DeleteMapping("/{id}")
    public String deleteBonus(@PathVariable Long id) {

        bonusService.deleteBonus(id);

        return "Bonus deleted successfully";
    }

    @GetMapping("/mybonus")
    public Bonus getMyBonus(){
        return bonusService.getMyBonus();
    }
}