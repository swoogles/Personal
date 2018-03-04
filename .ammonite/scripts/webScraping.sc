/* First forays into scraping the list of engineers off of the intranet site. */
// 
//load.ivy("org.jsoup" %% "jsoup" % "1.8.3")
// After this, I was able to figure out how to connect to a page and download the contents purely through the REPL
//val intranetConn = org.jsoup.Jsoup.connect("http://intranet.communitect.com/")
//val intranetContent = intranetConn.get
// Unfortunately at this point I realized all the content is happening behind some JS that could make it hard to access the data
//Looks like the JavaFX WebView might be the right way to go here. Since JSoup is only meant to parse HTML, you need a more
//full browser to interact with heavy JS sites.
// Scalafx should wrap things up in a pleasing way.
//load.ivy( "org.scalafx" %% "scalafx" % "8.0.60-R9")
//import scalafx.scene.web.WebView
//

