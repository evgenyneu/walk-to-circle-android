package com.evgenii.walktocircle.walkCongrats;

import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.utils.WalkRandomNumberGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * This class helps choosing a congratulation phrase shown to the user when a circle is reached.
 *
 * There are different types of phrases which depend on the number of circles reached by the user.
 *
 * For example, when a fifth circle is reached, the code get the Map of all phrases,
 * gets the list of congratulation phrases for the key 5
 * and shows a random one from this list.
 */
public class WalkCongratsPhrase {
    private static Map<Integer, String[]> mPhrases;

    // Phrases that were already seen today.
    // This is used to show different phrase when a new circle is reached.
    public static Set<String> mPhrasesSeenToday = new HashSet<String>();

    // Stores the current phrase to show to the user.
    // The purpose of it is to avoid showing different phrases when screen orientation is changed on congratulations screen.
    // This variable is set to null on map screen. This mean that a new phrase will be shown when the user reaches the circle.
    public static String mCurrentPhrase;

    // Random number generator that is used to pick a random phrase.
    // The property is null normally but in the unit test has a fake random number generator instance.
    public static WalkRandomNumberGenerator mRandomNumberGenerator;

    public static Map<Integer, String[]> getPhrases() {
        addPhrases();
        return mPhrases;
    }

    /**
     * @return a random congratulatory phrase that is shown to user after reaching a circle.
     */
    public static String getRandomPhrase() {
        int circlesReached = MainActivityState.getInstance().getCirclesReachedToday();
        return getRandomPhraseForCirclesReached(circlesReached);
    }

    /**
     * @param circlesReached number of circles reached today.
     * @return returns a random phrase for the given number of circles reached.
     */
    public static String getRandomPhraseForCirclesReached(int circlesReached) {
        if (WalkCongratsPhrase.mCurrentPhrase != null) {
            // Already generated the phrase, show the same one until a new circle is reached.
            return WalkCongratsPhrase.mCurrentPhrase;
        }

        if (circlesReached < 1) { circlesReached = 1; }
        String[] phrases = getUnseenPhrasesForCirclesReached(circlesReached);

        if (phrases.length == 0) {
            // All phrases seen, reset and start showing phrases again
            mPhrasesSeenToday = new HashSet<String>();
            phrases = getUnseenPhrasesForCirclesReached(circlesReached);
        }

        int randomIndex = getRandomNumberGenerator().getRandomIntUntil(phrases.length);
        String randomPhrase = phrases[randomIndex];
        mPhrasesSeenToday.add(randomPhrase);
        WalkCongratsPhrase.mCurrentPhrase = randomPhrase; // Remember current phrase
        return randomPhrase;
    }

    public static String[] getUnseenPhrasesForCirclesReached(int circlesReached) {
        String[] phrases = getPhrasesForCirclesReached(circlesReached);
        return excludePhrases(phrases, mPhrasesSeenToday);
    }

    /**
     *
     * @param circlesReached number of circles reached today.
     * @return Returns the array of phrases corresponding to the number of circles reached.
     */
    public static String[] getPhrasesForCirclesReached(int circlesReached) {
        if (circlesReached <1) { return new String[]{}; }
        Map<Integer, String[]> phrases = getPhrases();

        Set<Integer> keySet = phrases.keySet();
        Integer[] keys = keySet.toArray(new Integer[keySet.size()]);
        Arrays.sort(keys);

        String[] phrasesForCirclesReached = phrases.get(keys[0]);

        for (Integer minCirclesReached : keys) {
            if (circlesReached >= minCirclesReached) {
                phrasesForCirclesReached = phrases.get(minCirclesReached);
            } else {
                break;
            }
        }

        return phrasesForCirclesReached;
    }

    public static String[] excludePhrases(String[] fromPhrases, Set<String> exclude) {
        Vector filteredPhrases = new Vector();

        for (String phrase : fromPhrases){
            if (!exclude.contains(phrase)) {
                filteredPhrases.addElement(phrase);
            }
        }

        String[] filteredPhrasesArray = new String[filteredPhrases.size()];
        filteredPhrases.copyInto(filteredPhrasesArray);

        return filteredPhrasesArray;
    }

    /**
     *
     * @return real a random generator. A fake one will be returned in unit tests.
     */
    private static WalkRandomNumberGenerator getRandomNumberGenerator() {
        if (mRandomNumberGenerator == null) {
            return new WalkRandomNumberGenerator();
        } else {
            return mRandomNumberGenerator;
        }
    }

    private static void addPhrases() {
        if (mPhrases != null) { return; }

        mPhrases = new HashMap<Integer, String[]>();

        String[] values = {
                "Good start!",
                "Good job!",
                "Good work!",
                "OK!",
                "Good remembering!",
                "That's good!"};

        mPhrases.put(1, values);


        // ----------------------

        String[] values2 = {
                "Congratulations!",
                "Good going!",
                "You remembered!",
                "Nice going!",
                "Now you have the hang of it!",
                "Now you've figured it out!",
                "You've got it now!",
                "Way to go!",
                "Keep it up!",
                "Great!",
                "That's it!",
                "You got it right!",
                "You're doing a good job!",
                "That's the way!",
                "That's great!",
                "You're doing fine!",
                "You're on the right track now!",
                "Wonderful!"};

        mPhrases.put(2, values2);

        // ----------------------

        String[] values5 = {
                "That's really nice!",
                "Nothing can stop you now!",
                "Well, look at you go!",
                "You're really going to town!",
                "Excellent job!",
                "You did that very well!",
                "You did a very fine job!",
                "You're doing beautifully!",
                "You've done a great job!"};

        mPhrases.put(5, values5);


        // ----------------------

        String[] values8 = {
                "Terrific!",
                "Super duper!",
                "Fantastic!",
                "Perfect!",
                "You make it look easy!",
                "Sensational!",
                "Superb!",
                "Top-notch!",
                "Tremendous!",
                "Splendid!",
                "Incredible!",
                "Let’s tell the boss!"};

        mPhrases.put(8, values8);


        // ----------------------

        String[] values13 = {
                "That deserves a hug!",
                "You outdid yourself today!",
                "You are very good at that!",
                "You certainly did well today!",
                "That's the best ever!",
                "That's better than ever!",
                "You must have been practising!",
                "Best yet!",
                "Dynamite!",
                "Outstanding!",
                "You've just about mastered that!",
                "Beyond cool!",
                "Now you know the way better than a taxi driver!",
                "Amazing. We almost ran out of 'good job' phrases!"};

        mPhrases.put(13, values13);


        // ----------------------

        String[] values20 = {
                "Holy Figs!",
                "Oh My Glob!",
                "Algebraic!",
                "Mathematical!",
                "Shmowzow!"};

        mPhrases.put(20, values20);
    }
}
