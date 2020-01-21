//java parser

/* ensure the quit statement can be properly evaluated
 * allows for extended chars to be accepted but not alphabetical
 * @param terminateStr takes in quit arguement to be processed.
 *
 * Note:  Examples of shutdown sequences include: 'quit',
 * 'q123u$#i66t', 'q0u0i0t', 'q u i t' etc. while these sequences
 * DO NOT lead to a shutdown: 'qauaiat', 'quuit', 'q u ii t'
*/
void parseQuit(char terminate)
  {

    /*
     * split the string to be processed
     */
    char entry = split(terminate);

   /*
    * place to hold valid intermingled charactors
    */
    char specialChars = "[$#1234567890]";

    /*
     * charactors that cause quit to fail does not include
     * the letters 'q', 'u', 'i', 't'
     */
    char failChars = "[abcdefghjklmnoprsvwxyz]";
    char capFailChars = failChars.upper();

    /*
     * iterate though the string to ensure validity
     */
    for(int i = 0; i < terminate.length(); i++)
      {

        /*
         * ensure chars q, u, i, t only appear once
        */
        int numQs = 0
        int numUs = 0
        int numIs = 0
        int numTs = 0

        /*
         * ensures duplicate 'q','u','i','t' charactors FAIL.
        */
        else if numQs > 1 || numUs > 1 || numIs > 1 || numTs > 1
        return false;

       /*
        * if entry is exactly quit with non-special chars FAIL
        */
        else
        return false;

       /*
        * if entry is exactly quit CONTINUE
        */
        if entry = 'quit';
        return true;

        /*
         * if entry is exactly quit with special chars CONTINUE
         */
        else if entry = specialChars + 'q' + specialChars + 'u' +
                        specialChars + 'i' + specialChars + 't' + specialChars;
        return true;
      }
  }

  /* ensure the quit statement can be properly evaluated
   * takes in input and processes it by charactor
   * @param input takes in quit arguement to be processed.
   * terminates the current connection
   */

//will treat each byte in that stream as character data and sends it back,
// character-by-character, to the client immediately

// any character received will only be sent back, if that character
// is a small or capital letter of the English alphabet.
void parseInput(char input)
  {

   /*
    * splits the string to be processed
    */
    char entry = split(input)

   /*
    * iterate though the string to ensure validity
    */
    for(int i = 0; i < input.length(); i++)
    {

     /*
      * if the string is 'quit' pass to quit function
      */
      if entry = 'quit'
        return parseQuit(char input);

     /*
      * ensures valid user input to be sent is [a-z A-Z]
      */
      else if entry = '[a-z,A-Z]';
        return true
     /*
      * if the entry does not match format FAIL
      */
      else
        return false;
    }

  }
