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
                "Extra Life?",
                "Ka-boom!",
                "Swift Escape",
                "I AM INVINCIBLE!"
        };
        String[] descriptions = {
                "Gains a shield for 10 seconds.",
                "Nukes around the spaceship.",
                "Increases speed of the spaceship for 5 seconds.",
                "Become invincible for 5 seconds."
        };

        int[] icons = {
                R.drawable.powerup_heart,
                R.drawable.powerup_nuke,
                R.drawable.powerup_speedup,
                R.drawable.powerup_star
        };

        for(int i = 0; i < 4; i++) {
            powerups.add(new PowerUpsModel(
                    titles[i],
                    descriptions[i],
                    icons[i]
            ));
        }

        return powerups;
    }
}
