colorPack="ColorSamplerPack.zip"
curl http://www.vim.org/scripts/download_script.php?src_id=12179 > $colorPack 
unzip $colorPack
rm $colorPack

colorScroller="ScrollColor.vim"
curl http://www.vim.org/scripts/download_script.php?src_id=5966 > $colorScroller
mv $colorScroller plugin/

vim -c ":SCROLL"

