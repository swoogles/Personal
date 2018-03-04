val whiteSpace = 
  "\\s+"

def stdSpacing(line: String): String =
  line
    .trim
    .replaceAll(whiteSpace, " ")


