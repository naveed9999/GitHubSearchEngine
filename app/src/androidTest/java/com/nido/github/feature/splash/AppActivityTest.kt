package com.nido.github.feature.splash


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nido.github.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun appActivityTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.authenticate_with_github), withText("Continue with GitHub"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_controller),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.search_repository_recycler_view),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()

        val linearLayout = onView(
            allOf(
                withId(R.id.search_sort_parent),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_controller),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val linearLayout2 = onView(
            allOf(
                withId(R.id.search_sort_parent),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_controller),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        linearLayout2.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.search_query),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_query_parent),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("android test"), closeSoftKeyboard())

        val checkableImageButton = onView(
            allOf(
                withId(R.id.text_input_end_icon),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkableImageButton.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.search_repository_recycler_view),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton2 = onView(
            allOf(
                withId(R.id.repository_more_owner), withText("More on owner"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    9
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        pressBack()

        pressBack()

        val recyclerView3 = onView(
            allOf(
                withId(R.id.search_repository_recycler_view),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                )
            )
        )
        recyclerView3.perform(actionOnItemAtPosition<ViewHolder>(4, click()))

        pressBack()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
