
/* Goal:
 * look through all .java and .jsp files, then count & sort most-imported classes
 *
 * Challenges
 * 1. Read file contents as a Stream
 *  a. Stop reading once you know import section is over
 *    -Whitelist as long as (package.*, import.*, or whitespace/comments)
 *    -Blacklist (class.*, html tags, other jsp weirdness
 *
 * B. Collect fully qualified classes
 * C. ZipWith length
 * D. Sort by length
 * E. Display nicely
 * /
