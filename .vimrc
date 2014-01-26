" An example for a vimrc file.
"
" Maintainer:	Bram Moolenaar <Bram@vim.org>
" Last change:	2001 Jan 20
"
" To use it, copy it to
"     for Unix and OS/2:  ~/.vimrc
"	      for Amiga:  s:.vimrc
"  for MS-DOS and Win32:  $VIM\_vimrc
"	    for OpenVMS:  sys$login:.vimrc

" execute pathogen#incubate()
" execute pathogen#runtime_append_all_bundles()
execute pathogen#infect()
execute pathogen#helptags()

" Use Vim settings, rather then Vi settings (much better!).
" This must be first, because it changes other options as a side effect.
" Begin my lines - ordinary
set autoindent
set sw=2
set sta
set ts=2
set et
" End of my lines -ordinary
" Begin of my lines -change indents
map #1 :s/^   //
map #2 :s/^/   /
" End of my lines -change indents

"Begin of my lines - F3 to latex current file
"                    from command or insert
map #3 :w:! latex % 
map! #3 :w:! latex % i
" End of my lines - latex

" cmap w!! %!sudo tee > /dev/null %
cmap w!! w !sudo dd of=%

" Begin my lines - for OpenGL
ab glB glBegin
ab glC glClear
ab glC3f glColor3f
ab glCC glClearColor
ab glE glEnd
ab glF glFlush
ab glLI glLoadIdentity
ab glMM glMatrixMode
ab glV2f glVertex2f
ab GL_C_B_B GL_COLOR_BUFFER_BIT
ab GL_PO GL_POLYGON
ab GL_PR GL_PROJECTION
ab gluO gluOrtho
ab glutCW glutCreateWindow
ab glutDF glutDisplayFunc
ab glutI glutInit
ab glutIDM glutInitDisplayMode
ab glutIWP glutInitWindowPosition
ab glutIWS glutInitWindowSize
ab glutML glutMainLoop
" End of my lines -for OpenGL

set nocompatible

" Used for powerline
set laststatus=2

set bs=2		" allow backspacing over everything in insert mode
set ai			" always set autoindenting on

" Turned off backups to not interfere with Project code
"if has("vms")
"  set nobackup		" do not keep a backup file, use versions instead
"else
"  set backup		" keep a backup file
"endif

set history=50		" keep 50 lines of command line history
set ruler		" show the cursor position all the time
set incsearch		" do incremental searching
set ignorecase "Case insensitive search
set hidden "Remember undo after quitting


" For Win32 GUI: remove 't' flag from 'guioptions': no tearoff menu entries
" let &guioptions = substitute(&guioptions, "t", "", "g")

" Don't use Ex mode, use Q for formatting
map Q gq

" Make p in Visual mode replace the selected text with the "" register.
vnoremap p <Esc>:let current_reg = @"<CR>gvs<C-R>=current_reg<CR><Esc>

" Switch syntax highlighting on, when the terminal has colors
" Also switch on highlighting the last used search pattern.
if &t_Co > 2 || has("gui_running")
  syntax on
  set hlsearch
endif

" Only do this part when compiled with support for autocommands.
if has("autocmd")

  " Enable file type detection.
  " Use the default filetype settings, so that mail gets 'tw' set to 72,
  " 'cindent' is on in C files, etc.
  " Also load indent files, to automatically do language-dependent indenting.
  filetype plugin indent on

  " For all text files set 'textwidth' to 78 characters.
  au FileType text setlocal tw=78

  " When editing a file, always jump to the last known cursor position.
  autocmd BufReadPost *
    \ if line("'\"") > 0 && line("'\"") <= line("$") |
    \   exe "normal g`\"" |
    \ endif

endif " has("autocmd")

au FileType * setl fo-=cro

" au Filetype java :so ~/JavaKit/javakit/vim/JavaKit.vim

set wildmenu

" Update vimrc on write
au! BufWritePost .vimrc source % 

" Indention based Folding + manual
"augroup vimrc
"  au BufReadPre * setlocal foldmethod=indent
"  au BufWinEnter * if &fdm == 'indent' | setlocal foldmethod=manual | endif
"augroup END

" Manual Folding
"au BufReadPre * setLocal foldmethod=indent


"Abbreviations
ab #b /*******************************
ab #e *******************************/

"Java Abbreviations"
ab jp/ System.out.println(
iab jc/ /*
\<CR>  *
\<CR>  *
\<CR>  */

" Macros
:let @a='_m`"tyw$b"nywA^M^Rt^Rn^[b~biget^[OF() {^Mreturn ^Rn;^M|<80>kb}^Mvoid ^Rn^[b~biset^[OF( <80>kb^Rt^Rn^[b~binew^[bywA) {^M^Rn = ^R";^M}^M^[a'

" Make quick backup of current file
:let @b=':! cp % %_backup'
" Delete backup file
:let @d=':! rm %_backup'

" ctags options
set tags=tags;/
" ctags mappings
map <A-]> :vsp <CR>:exec("tag ".expand("<cword>"))<CR> 

"MiniBufExplorer options
let g:miniBufExplMapWindowNavVim = 1
let g:miniBufExplMapWindowNavArrows = 1
let g:miniBufExplMapCTabSwitchBufs = 1
let g:miniBufExplModSelTarget = 1 

" Open new splits on the right
set splitright
set splitbelow 


" Highlight cursor after jump
function! s:Cursor_Moved()
  let cur_pos = winline()
  if g:last_pos == 0
    set cul
    let g:last_pos = cur_pos
    return
  endif
  let diff = g:last_pos - cur_pos
  if diff > 1 || diff < -1
    set cul
  else
    set nocul
  endif
  let g:last_pos = cur_pos
endfunction
autocmd CursorMoved,CursorMovedI * call s:Cursor_Moved()
let g:last_pos = 0

colors zellner
colors torte


" Eclim stuff
let mapleader = ","
:nnoremap <silent> <buffer> <leader>i :JavaImport<cr>

" Make Capital Y behave like other capitals
map Y y$

" Better behavior while scrolling through wrapped lines
nnoremap j gj
nnoremap k gk

" Using H & L to move to beginning and end of line respectively
map H ^
map L $


" Highlight line after jump

"Paste over visual selection, without copying selection into default register
vnoremap p "_dP

"Buffer Diffing
let g:diffed_buffers=[]
function! DiffText(a, b, diffed_buffers)
  " enew
  execute 'tab split | enew'
  setlocal buftype=nowrite
  call add(a:diffed_buffers, bufnr('%'))
  call setline(1, split(a:a, "\n"))
  diffthis
  vnew
  setlocal buftype=nowrite
  call add(a:diffed_buffers, bufnr('%'))
  call setline(1, split(a:b, "\n"))
  diffthis
endfunction
function! WipeOutDiffs(diffed_buffers)
  for buffer in a:diffed_buffers
    execute 'bwipeout! '.buffer
  endfor
  call remove(a:diffed_buffers, 0, -1)
endfunction
:nnoremap <special> <F7> :call DiffText(@a, @b, g:diffed_buffers)
:nnoremap <special> <F8> :call WipeOutDiffs(g:diffed_buffers) | let g:diffed_buffers=[]

:map <F2> :echo 'Current time is ' . strftime('%c')<CR>
:map! <F3> a<C-R>=strftime('%c')<CR><Esc>

if has('cscope')
  set cscopetag cscopeverbose

  if has('quickfix')
    set cscopequickfix=s-,c-,d-,i-,t-,e-
  endif

  cnoreabbrev csa cs add
  cnoreabbrev csf cs find
  cnoreabbrev csk cs kill
  cnoreabbrev csr cs reset
  cnoreabbrev css cs show
  cnoreabbrev csh cs help

  command! -nargs=0 Cscope cs add $VIMSRC/src/cscope.out $VIMSRC/src
endif

function! LatexSuite()
  source ~/.vim/ftplugin/tex_latexSuite.vim
endfunction

function! LoadCscope()
  let db = findfile("cscope.out", ".;")
  if (!empty(db))
    let path = strpart(db, 0, match(db, "/cscope.out$"))
    set nocscopeverbose " suppress 'duplicate connection' error
    exe "cs add " . db . " " . path
    set cscopeverbose
  endif
endfunction
au BufEnter /* call LoadCscope()
call LoadCscope()

""""" For vim-latex stuff """"" 
" REQUIRED. This makes vim invoke Latex-Suite when you open a tex file.
filetype plugin on

" IMPORTANT: win32 users will need to have 'shellslash' set so that latex
" can be called correctly.
set shellslash

" IMPORTANT: grep will sometimes skip displaying the file name if you
" search in a singe file. This will confuse Latex-Suite. Set your grep
" program to always generate a file-name.
set grepprg=grep\ -nH\ $*

" OPTIONAL: This enables automatic indentation as you type.
filetype indent on

" OPTIONAL: Starting with Vim 7, the filetype of empty .tex files defaults to
" 'plaintex' instead of 'tex', which results in vim-latex not being loaded.
" The following changes the default filetype back to 'tex':
let g:tex_flavor='latex'
""""" /For vim-latex stuff """"" 
