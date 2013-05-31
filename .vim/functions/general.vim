" function! CapitalizeCenterAndMoveDown()
"   s/\<./\u&/g   "Built-in substitution capitalizes each word
  " center        "Built-in center command centers entire line
  " +1            "Built-in relative motion (+1 line down)
" endfunction

" nmap <silent>  \C  :call CapitalizeCenterAndMoveDown()<CR>

function! CheckStyleCurrentFile()
  let @f=@%
  let $file=@%
  " echom s:file
  new ${file}_CheckStyle
  r! java -cp ~/CheckStyle/checkstyle-5.6/checkstyle-5.6-all.jar com.puppycrawl.tools.checkstyle.Main -c ~/CheckStyle/checkstyle-5.6/checks/bill-checkstyle-checks.xml $file
  " new register('s')
  " new &s

  " :help bufexists()
  " let s:checkFileExists = bufexists(${file}_CheckStyle)
  " if s:checkFileExists
  "   bd ${file}_CheckStyle
  " else
  "   r! java com.puppycrawl.tools.checkstyle.Main -c ~/CheckStyle/checkstyle-5.6/checks/bill-checkstyle-checks.xml $file
  " endif
endfunction

nmap <silent>  \C  :call CheckStyleCurrentFile()<CR>
