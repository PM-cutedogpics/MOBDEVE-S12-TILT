package com.mobdeve.s12.tiltosurvive;

import java.util.ArrayList;

public class DataHelper {

    public ArrayList<AchievementModel> initAchievements() {
        ArrayList<AchievementModel> achievements = new ArrayList<>();

        String[] titles = {
                "Utter Defeat",
                "Stampede",
                "Cattle Driver",
                "Cow Boy",
                "A-moo-zing",
                "Moooooo",
                "Cow-ard"
        };
        String[] descriptions = {
                "Get defeated within 5 seconds",
                "Kill 100 cows",
                "Block off 500 cows",
                "Kill 1000 cows",
                "Get a score of atleast 10000",
                "Hit a cow",
                "Get a score of atleast 1000000"
        };
        for(int i = 0; i < 7; i++) {
            achievements.add(new AchievementModel(
                    titles[i],
                    descriptions[i]
            ));
        }
        return achievements;
    }

    public ArrayList<PowerUpsModel> initPowerUps() {
        ArrayList<PowerUpsModel> powerups = new ArrayList<>();

        String[] titles = {
                "Bubble",
                "FREEZE!",
                "Swift Escape",
                "Pew pew",
                "Ka-boom!",
                "Slow down"
        };
        String[] descriptions = {
                "Gains a shield for 10 seconds.",
                "Freeze cows around the spaceship.",
                "Increases speed of the spaceship for 5 seconds.",
                "Shoots a laser depending on where the spaceship is aimed at",
                "Nukes around the spaceship.",
                "Slows cows down for 5 seconds."
        };

        int[] icons = {
                R.drawable.force_field_box_grey,
                R.drawable.freeze_box_grey,
                R.drawable.haste_box_grey,
                R.drawable.laser_box_grey,
                R.drawable.nuke_box_grey,
                R.drawable.slow_box_grey
        };

        int[] iconsActivated = {
                R.drawable.force_field_box,
                R.drawable.freeze_box,
                R.drawable.haste_box,
                R.drawable.laser_box,
                R.drawable.nuke_box,
                R.drawable.slow_box
        };

        boolean[] isSelected = {
                false,
                false,
                false,
                false,
                false,
                false,
        };

        for(int i = 0; i < 6; i++) {
            powerups.add(new PowerUpsModel(
                    titles[i],
                    descriptions[i],
                    icons[i],
                    iconsActivated[i],
                    isSelected[i]
            ));
        }

        return powerups;
    }
}
