package com.example.momcare.security;

import com.example.momcare.models.User;
import com.example.momcare.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CheckAccount {
    public int checkSignup(User user, UserService service){
        if(service.findAccountByEmail(user.getEmail()) != null)
            return 1;
        if (!checkPassWordstrength(user.getPassWord()))
            return 2;
        if( service.findAccountByUserName(user.getUserName()) == null)
            return 3;

        else
            return 0;

    }

    public boolean checkPassWordstrength(String passWord){
        int upChars=0, lowChars=0;
        int special=0, digits=0;
        char ch;
        int total = passWord.length();
        if(total < 8)
            return false;
        for(int i=0; i<total; i++)
        {
            ch = passWord.charAt(i);
            if(Character.isUpperCase(ch))
                upChars = 1;
            else if(Character.isLowerCase(ch))
                lowChars = 1;
            else if(Character.isDigit(ch))
                digits = 1;
            else
                special = 1;
        }

        if(upChars==1 && lowChars==1 && digits==1 && special==1)
            return true;
        return false;
    }

}