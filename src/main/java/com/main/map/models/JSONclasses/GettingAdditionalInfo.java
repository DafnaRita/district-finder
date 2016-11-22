package com.main.map.models.JSONclasses;


import com.main.map.models.JSONclasses.factoryMethod.*;

public class GettingAdditionalInfo {
    public static Creator getCreator(int type){
        switch (type){
            case 1:
                return new CreatorParks();
            case 2:
                return new CreatorMall();
            case 3:
                return new CreatorSchool();
            case 4:
                return new CreatorSports();
            case 5:
                return new CreatorRecreation();
            case 6:
                return new CreatorHealthFacility();
            case 7:
                return new CreatorKindergardenInfo();
            default:
                return new CreatorParks();
        }
    }
}
