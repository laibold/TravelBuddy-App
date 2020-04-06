package com.travelbuddyapp.travelBuddy.model.packingList;

import java.util.Arrays;

public enum PackingListType {
    OWN, SHARED;

    public static String[] getNames(){
        return Arrays.toString(PackingListType.values()).replaceAll("^.|.$", "").split(", ");
    }


}
