package com.example.bonnie.petaid;

import android.app.Application;

public class PetAidApplication extends Application {
    String typeUser;
    String emailSignUser;

    public String getEmailSignUser() {
        return emailSignUser;
    }

    public void setEmailSignUser(String emailSignUser) {
        this.emailSignUser = emailSignUser;
    }

    public String getTypeUser() {

        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
}
