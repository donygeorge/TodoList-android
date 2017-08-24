# Pre-work - *TodoList*

**Name of your app** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Dony George**

Time spent: **4** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Add app icons

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/QKmwqAb.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I have developed a couple of android apps around 4 years ago. Compared to back then, I don't see any major changes while using 'LinearLayout'. 'ConstraintLayout' is certainly new but isn't the most intuitive to use in my opinion. Android's layout approach seems similar in many ways to different ways of doing layout on iOS and Windows (I've worked on these platforms as well). With the LinearLayout, one improvement would be to make it easier to visually align elements with existing elements.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** The ArrayAdapter is a layer between the arraylist of items which just contain the data and the UI component: list view. The ArrayAdaptor has logic to convert each of these individual items to a View that is displayed in a ListView. ArrayAdapter only creates views for the items that are on the screen (and maybe a couple extra depending ons crolling speed). When an item moves off screen, the View used to display the item is reused for the next item displayed on the screen.

If convertView is null, there is no View that is not being currently used. In such cases, we inflate a new View. However, in case convertView is not null, we can reuse this View to display the current Item.

## Notes

Describe any challenges encountered while building the app.

- It was tricky to create the UI using the UI builder in Android Studio. I particularly struggled a bit with aligning the individual UI elements correctly.
- I briefly switched to using Kotlin instead of Java. However, I switched back to Java due to time constraints and my unfamiliarity with Kotlin.