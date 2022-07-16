package com.klikdigital.lifecycleexample.data;

import android.content.Context;
import android.content.res.TypedArray;

import com.klikdigital.lifecycleexample.R;
import com.klikdigital.lifecycleexample.model.People;
import com.klikdigital.lifecycleexample.utils.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataGenerator {
    public static List<People> getPeopleData(Context context) {
        ArrayList arrayList = new ArrayList();
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(R.array.people_images);
        String[] stringArray = context.getResources().getStringArray(R.array.people_names);
        for (int i = 0; i < obtainTypedArray.length(); i++) {
            People people = new People();
            people.image = obtainTypedArray.getResourceId(i, -1);
            people.name = stringArray[i];
            people.email = Tools.getEmailFromName(people.name);
            people.imageDrw = context.getResources().getDrawable(people.image);
            arrayList.add(people);
        }
        Collections.shuffle(arrayList);
        return arrayList;
    }

    public static List<String> getStringsMonth(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String add : context.getResources().getStringArray(R.array.month)) {
            arrayList.add(add);
        }
        Collections.shuffle(arrayList);
        return arrayList;
    }
}
