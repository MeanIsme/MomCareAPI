package com.example.momcare.service;

import com.example.momcare.models.Menu;
import com.example.momcare.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MenuService {
    MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public List<Menu> findByCategory (String category){return this.repository.findAllByCategory(category);}
    public Menu findById (String id){return this.repository.getMenuById(id);}

}
