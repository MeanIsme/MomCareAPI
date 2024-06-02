package com.example.momcare.service;

import com.example.momcare.models.Menu;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.MenuRepository;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MenuService {
    MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public Response findMenusByCategory(String idCategory) {
        List<Menu> menus = findByCategory(idCategory);
        return new Response(HttpStatus.OK.getReasonPhrase(), menus, Constant.SUCCESS);
    }
    public Response findMenuById(String id) {
        Menu menu = findById(id);
        if (menu != null) {
            List<Menu> menus = new ArrayList<>();
            menus.add(menu);
            return new Response(HttpStatus.OK.getReasonPhrase(), menus, Constant.SUCCESS);
        } else {
            return new Response(HttpStatus.NOT_FOUND.getReasonPhrase(), new ArrayList<>(), Constant.MENU_NOT_FOUND);
        }
    }
    public List<Menu> findByCategory (String category){return this.repository.findAllByCategory(category);}
    public Menu findById (String id){return this.repository.getMenuById(id);}

}
