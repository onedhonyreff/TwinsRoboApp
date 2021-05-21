package com.codepan.twinsrobo_apps.OtherClass;

import com.codepan.twinsrobo_apps.R;

import java.util.ArrayList;
import java.util.List;

public class ListAvatar {

    Integer[] avatarImageMale = {
            R.drawable.male1, R.drawable.male2, R.drawable.male3, R.drawable.male4, R.drawable.male5,
            R.drawable.male6, R.drawable.male7, R.drawable.male8, R.drawable.male9, R.drawable.male10,
            R.drawable.male11, R.drawable.male12, R.drawable.male13, R.drawable.male14, R.drawable.male15,
            R.drawable.male16, R.drawable.male17, R.drawable.male18, R.drawable.male19, R.drawable.male20,
            R.drawable.male21, R.drawable.male22, R.drawable.male23
    };

    Integer[] avatarImageFemale = {
            R.drawable.female1, R.drawable.female2, R.drawable.female3, R.drawable.female4, R.drawable.female5,
            R.drawable.female6, R.drawable.female7, R.drawable.female8, R.drawable.female9, R.drawable.female10,
            R.drawable.female11, R.drawable.female12, R.drawable.female13, R.drawable.female14
    };

    int jumlahAvatar;
    Integer avatar;

    public int getAvatarLenght(String jk) {
        if (jk.equals("Male")) {
            jumlahAvatar = this.avatarImageMale.length;
        } else if (jk.equals("Female")) {
            jumlahAvatar = this.avatarImageFemale.length;
        }
        return jumlahAvatar;
    }

    public int getAvatarLenght() {
        return this.avatarImageMale.length + this.avatarImageFemale.length;
    }

    public List<Integer> takeAvatar(String jk) {

        List<Integer> takingList = new ArrayList<>();

        if (jk.equals("Male")) {
            for (int i = 0; i < avatarImageMale.length - 1; i++) {
                takingList.add(avatarImageMale[i]);
            }
        } else if (jk.equals("Female")) {
            for (int i = 0; i < avatarImageFemale.length - 1; i++) {
                takingList.add(avatarImageFemale[i]);
            }
        }

        return takingList;
    }

    public Integer getAvatar(String jk, int index) {
        if (jk.equals("Male")) {
            avatar = avatarImageMale[index];
        } else if (jk.equals("Female")) {
            avatar = avatarImageFemale[index];
        }
        return avatar;
    }
}
