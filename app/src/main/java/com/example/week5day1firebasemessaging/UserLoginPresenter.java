package com.example.week5day1firebasemessaging;

import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginPresenter {
    UserLoginContract userLoginContract;


    public UserLoginPresenter(UserLoginContract userLoginContract) {
        this.userLoginContract = userLoginContract;
    }

    public void checkMessage(String message, FirebaseUser user){
        if(!message.equals("") && user != null){
            System.out.println(message);
            userLoginContract.MessageReturnCheck();
        }else {
            userLoginContract.MessageReturnBadCheck();
        }
    }
}
