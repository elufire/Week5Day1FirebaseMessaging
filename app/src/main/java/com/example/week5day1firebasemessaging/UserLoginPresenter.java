package com.example.week5day1firebasemessaging;

import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginPresenter {
    UserLoginContract userLoginContract;

    //Constructor takes a Contract to allow for communication between presenter and view
    public UserLoginPresenter(UserLoginContract userLoginContract) {
        this.userLoginContract = userLoginContract;
    }

    //Check the message isn't null or empty
    public void checkMessage(String message, FirebaseUser user){
        if(!message.equals("") && user != null){
            System.out.println(message);
            userLoginContract.MessageReturnFromCheck();
        }else {
            userLoginContract.MessageReturnFromBadCheck();
        }
    }
}
