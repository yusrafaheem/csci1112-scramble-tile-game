/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2024
author: Yusra Faheem 

This class encapsulates the game logic needed to support a Scrabble like
tile based spelling game
--------------------------------------------------------------------------*/
class TileGame {
    //                            A,B,C,D, E,F,G,H,I,J,K,L,M,N,O,P, Q,R,S,T,U,V,W,X,Y, Z
    /// the points for a given alpha
    static final int[] points  = {1,3,3,2, 1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    /// the maximum number of tiles (uses) for a given alpha in a single word
    static final int[] tilebag = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2, 1,6,4,6,4,2,2,1,2, 1};
    public static final int MAXTILES = 98;  // ignores 2 blanks


    /// Looks up the points associated with a given character and returns
    /// that point value
    /// @param ch the character to look up a point value for
    /// @return a positive point value representing the point value for 
    ///         that character if ch is an uppercase alpha; otherwise, a 
    ///         zero value
    public static int getTilePoints( char ch ) {
        // Convert ch into its ordinal alphabetical position 
        int ord = (int)ch - (int)'A';

        // sanity check - validate ch is in the range of uppercase alphas
        if(ord < 0) { return 0; }
        if(ord >= 26) { return 0; }

        // ch must be an uppercase alpha, so return the value in the ordinal
        // position of the points table
        return points[ord];
    }

    /// Computes the total score for a word and returns that point total
    /// @param word a character array that contains the word that we need
    ///        the point total for 
    /// @return the total score for the word passed in.  If the function
    ///         fails for any reason, it returns zero
    public static int getWordScore( char[] word ) {
        // sanity check - validate that the word is valid
        if( word == null ) { return 0; }

        // the running total for the summation
        int total = 0;

        // iterate over every character in the word and add its points 
        // to the total  
        for( int i = 0; i < word.length; i++ ) {
            total += getTilePoints( word[i] );
        }

        // return the total
        return total;
    }

    /// Make a deep copy of the model tilebag array.  The model must not be 
    /// changed, so need to copy the array and use the copy as a 'scratchpad'
    /// for computing tile usage
    /// @return a copy of the tilebag array/an array of the number of tiles
    ///         available for each alpha ordinal position.  
    public static int[] copyTileBag() {
        // TODO : Add your code here

        //MY COMMENTS: Author's comments 
        // We first created a new array independent of the first one so that we were able to create a true deep copy 
        // If we had just initialized a new array to the previous one, we would be pointing to the memory addresses in the array instead
        // That would have been a shallow copy instead of a deep one 
        // So we called this new array a Deep Copy because that is what it is 
        // This deep copy was set to tilebag's length and we iterated through tilebag and made sure that for every letter in tilebag 
        // Or that for every position in tilebag, that same letter was copied over to our deep Copy 
        // Now our new copy of tilebag's letters is in an independent array 
        // We then returned deepCopy because that is what the function asked for and that was the purpose 

    
        int [] deepCopy = new int[tilebag.length];
        for (int i=0; i<tilebag.length; i++){
            deepCopy[i]= tilebag[i];

        }


        return deepCopy;
    }

    /// Draw a random subset of tiles from the tilebag
    /// @param count the number of tiles to draw from the tilebag
    /// @return an array of characters drawn from the tilebag 
    ///         according to the limits of the number of tiles
    ///         for a given character; or null if the count is 
    ///         invalid or the operation fails for any reason
    public static char[] drawHand( int count ) {
        // sanity check - validate that the count does not exceed
        // the number of available tiles
        if( count > MAXTILES ) { return null; }

        // deep copy the tilebag.  Why?  because if we don't
        // any changes to the data would destroy the model and the
        // game would no longer have consistent rules from round to
        // round.
        int[] tb = copyTileBag();
        // create a hand of the specified size
        char[] hand = new char[count];

        // draw a tile to fill the next open hand position
        for( int i = 0; i < count; i++ ) {
            // but the tile must be valid 
            // can't draw more tiles than are available in tilebag
            boolean valid = false;
            while( !valid ) {
                // generate a random number from a uniform distribution
                int pos = UniformRandom.uniform(0,25);
                // if the tile generated is available
                if( tb[pos] > 0 ) {
                    valid = true;   // it's valid
                    tb[pos]--;      // decrement the available count
                    hand[i] = (char)((int)'A' + pos);  // put in hand
                }
            }
        }
        return hand;
    }
   
    /// Determines where the characters in hand can be used to 
    /// spell the word.  Each character in hand can only be used once.
    /// If we are trying to spell a word with two A's in it, there
    /// must be at least two A's in hand.
    /// @param hand the jumble of characters available to spell with
    /// @param word the word that we are testing trying to spell
    /// @return true if the characters in hand can be used to spell
    ///         the word; otherwise, false.
    public static boolean canSpell( char[] hand, char[] word ) {
        // TODO : Add your code here

        // AUTHOR's COMMENTS 
        // We first check to see if either the case of our hand or the word have nothing in them, which would give us false as an output 
        // This is necessary, because otherwise we wouldn't really be able to spell anything, so the function would be pointless
        // We then created a new array with all the letters of the english alphabet so we set it to the same length of 26 
        // We will use this to keep track of the number of times each one of our letters appears 
        // This is important to do because each letter shows up a different amount of times and we cant use a letter twice that we only have once 
        // We then go through each character in our hand and store the number accordingly based on which letter 
        // This works because starting from A, we will be able to know which letter it is from its apporximate distance from A 
        //We then go through each letter in word and we m,ake sure we can spell the word actually from what we have in our hand 
        //Then we compare the word to our hand, and we make sure we are able to spell it 
        // Essentially the idea is that if we have a 0 or less, we are unable to use the wordbecause either it's already been used (like the AMAZE problem) 
        // or we never had it to begin with 
        // We then return true if we have all of the necessary letters and can spell the word 
        // 



        if (hand == null){ 
            return false;
        }
        if (word == null){
            return false;
        }

        int [] handcharacterCount = new int[26]; 

        for (int i=0 ; i< hand.length; i++){ 
            char handchar = hand[i]; 
            handcharacterCount[handchar - 'A']++; 

        }

        for (int i=0; i<word.length; i++){ 
            char handch = word [i]; 

            if (handcharacterCount[handch - 'A'] <= 0){ 
                return false; 
            }

        }



 
        return true;
    }

    /// Returns the highest scoring word according to getWordScore from 
    /// dictionary that can be spelled by the characters in hand according
    /// to canSpell
    /// @param hand the jumble of characters available to spell with
    /// @param dictionary an array of words that we test against
    /// @return the highest scoring word that can be spelled given the
    ///         input hand according to getWordScore; otherwise, null
    ///         if no word in dictionary can be spelled with hand
    /// 
    ///
    public static String getBestWord( char[] hand, String[] dictionary ) {
        // TODO : Add your code here


        // AUTHORS COMMENTS
        // We first check to see if either our hand is empty, or our dictionary is, because otherwise we could not make a comparison 
        // We then initialize the highest scoring word to 0 as we havent found it yet and do the same for max score 
        // Then we go through every word in the dictionary and convert the value of the word from a string to char so we can use our helper functions i think 
        // Because the helper functions take char, and I cant change those 
        // Then we use one of the helper functions to calculate the score of our word and save it as current score since we need to get the best one 
        // If we have a current score thats larger than the maxcimum, then that becomes our new highest score 
        // If we didnt't find the highest score, our function would yield null 
        // However, if it is found, we now return the highest scoring word for real as an updated word and that is our best one. 


        if (hand == null){ 
            return null;
        }
        if (dictionary == null){
            return null;
        }

            String highestscoringWord = null; 
            int maxscore = 0; 

            for (int i=0; i< dictionary.length; i++){ 
                String word = dictionary[i]; 
                char [] wordArray = word.toCharArray(); 

                if (canSpell(hand,wordArray)) { 
                    int currentscore = getWordScore(wordArray); 

                    if (currentscore > maxscore){ 
                        maxscore = currentscore; 
                        highestscoringWord = word; 




                    }
                    


            
                }

            }



        }






        return highestscoringWord;
    }
}
