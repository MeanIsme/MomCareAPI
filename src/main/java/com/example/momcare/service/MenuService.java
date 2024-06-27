package com.example.momcare.service;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.Menu;
import com.example.momcare.repository.MenuRepository;
import com.example.momcare.util.Constant;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MenuService {
    MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public List<Menu> findMenusByCategory(String idCategory) {
        return findByCategory(idCategory);

    }
    public List<Menu> findMenuById(String id) throws ResourceNotFoundException {
        Menu menu = findById(id);
        if (menu != null) {
            return List.of(menu);
        } else {
            throw new ResourceNotFoundException(Constant.MENU_NOT_FOUND);
        }
    }
    public List<Menu> findByCategory (String category){return this.repository.findAllByCategory(category);}
    public Menu findById (String id){return this.repository.getMenuById(id);}

}
