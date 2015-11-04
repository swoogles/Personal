
/* Goal detect duplicate lines in source files
 * 1. Don't get too clever with near matches, but handle obvious ignorables like whitespace lines
 * 2. Find most repetitive author <- Careful with the results of this one...
 * 3. Find term that's used most ofen on these lines
 *    -Filter out language constructs. Too much noise there.
 */

val dupValue = "hi"
