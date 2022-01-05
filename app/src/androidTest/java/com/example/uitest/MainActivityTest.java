package com.example.uitest;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Thread.sleep(1000);
    }

    @Test
    public void editTextInputCheck() {
        //Type a text
        String sampleString = "Espresso";
        Espresso.onView(withId(R.id.editText)).perform(typeText(sampleString));
        //Close soft keyboard
        Espresso.closeSoftKeyboard();
        //check if typing is successful
        Espresso.onView(withId(R.id.editText)).check(matches(withText(sampleString)));
    }

    @Test
    public void seekbarProgressCheckAnyProgress() {
        Espresso.onView(withId(R.id.seekbar)).perform(setProgress(50));
        Espresso.onView(withId(R.id.editText)).check(matches(withText("50")));
    }

    @Test
    public void seekbarProgressCheckFullProgress() {
        Espresso.onView(withId(R.id.seekbar)).perform(swipeRight());
        Espresso.onView(withId(R.id.editText)).check(matches(withText("100")));
    }

    @Test
    public void seekbarProgressCheckNoProgress() {
        Espresso.onView(withId(R.id.seekbar)).perform(swipeLeft());
        Espresso.onView(withId(R.id.editText)).check(matches(withText("0")));
    }

    @Test
    public void enableSwitchCheckWhenDisabled() {
        //Enable checkbox
        Espresso.onView(withId(R.id.switch_view)).perform(click());
        //Check if enabled
        Espresso.onView(withId(R.id.switch_view)).check(matches(isChecked()));
    }

    @Test
    public void disableSwitchCheckWhenEnabled() {
        //Enable checkbox
        Espresso.onView(withId(R.id.switch_view)).perform(click());
        //Disable checkbox again clicking
        Espresso.onView(withId(R.id.switch_view)).perform(click());
        //Check if disabled
        Espresso.onView(withId(R.id.switch_view)).check(matches(isNotChecked()));
    }

    @Test
    public void enableCheckboxCheckWhenDisabled() {
        //Enable checkbox
        Espresso.onView(withId(R.id.checkbox)).perform(click());
        //Check if enabled
        Espresso.onView(withId(R.id.checkbox)).check(matches(isChecked()));
    }

    @Test
    public void disableCheckboxCheckWhenEnabled() {
        //Enable checkbox
        Espresso.onView(withId(R.id.checkbox)).perform(click());
        //Disable checkbox
        Espresso.onView(withId(R.id.checkbox)).perform(click());
        //Check if disabled
        Espresso.onView(withId(R.id.checkbox)).check(matches(isNotChecked()));
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(1000);
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}