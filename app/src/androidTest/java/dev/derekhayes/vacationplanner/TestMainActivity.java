package dev.derekhayes.vacationplanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.derekhayes.vacationplanner.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class TestMainActivity {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void correctPasswordFormat() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertTrue(activity.validatePassword("Password1!"));
        });
    }
    @Test
    public void tooShort() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("Pass"));
        });
    }
    @Test
    public void tooLong() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("Password1!ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"));
        });
    }
    @Test
    public void missingNumber() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("Password!"));
        });
    }
    @Test
    public void missingSpecialCharacter() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("Password1"));
        });
    }
    @Test
    public void missingLowerCase() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("PASSWORD1!"));
        });
    }
    @Test
    public void missingUpperCase() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            assertFalse(activity.validatePassword("password1!"));
        });
    }
}
