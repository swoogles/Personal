developers := List(Developer(
  id = "bfrasure",
  name = "Bill Frasure",
  email = "bill@billdingsoftware.com",
  url = url("https://github.com/swoogles")
))

publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

Global / onChangedBuildSource := ReloadOnSourceChanges
