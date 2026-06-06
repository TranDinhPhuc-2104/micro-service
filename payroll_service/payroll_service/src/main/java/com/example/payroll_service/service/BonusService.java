package com.example.payroll_service.service;

import com.example.payroll_service.entity.Bonus;
import com.example.payroll_service.repository.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    public List<Bonus> getAllBonuses() {
        return bonusRepository.findAll();
    }

    public Bonus getBonusById(Long id) {
        return bonusRepository.findById(id).orElse(null);
    }

    public Bonus createBonus(Bonus bonus) {
        return bonusRepository.save(bonus);
    }

    public Bonus updateBonus(Long id, Bonus bonus) {
        bonus.setId(id);
        return bonusRepository.save(bonus);
    }

    public void deleteBonus(Long id) {
        bonusRepository.deleteById(id);
    }

    public Bonus getMyBonus(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Number number = jwt.getClaim("id");
        Long id = number.longValue();

        Bonus bonus = bonusRepository.findByEmployeeId(id);
        return bonus;

     }
}