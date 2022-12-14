package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.activities.ListMeetingActivity;
import com.example.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    @Rule
    public ActivityScenarioRule<ListMeetingActivity> mActivityRule =
            new ActivityScenarioRule<>(ListMeetingActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<ListMeetingActivity> mActivity = mActivityRule.getScenario();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerview_list_meeting))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        // This is fixed
        int ITEMS_COUNT = 6;

        onView(ViewMatchers.withId(R.id.recyclerview_list_meeting))
                .check(withItemCount(ITEMS_COUNT));


        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.recyclerview_list_meeting))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(1, new DeleteViewAction()));


        // Then : the number of element is 5
        onView(ViewMatchers.withId(R.id.recyclerview_list_meeting))
                .check(withItemCount(ITEMS_COUNT -1));

    }

}
