import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "AngelhackLocationBookmarks",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-14",
    libraryDependencies ++= Seq(
      "io.spray" %%  "spray-json" % "1.2.3"
    ),
    unmanagedBase <<= baseDirectory { base => base / "lib" }
  )

  val proguardSettings = Seq (
    useProguard in Android := true
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.8" % "test"
      )
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "AngelhackLocationBookmarks",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "AngelhackLocationBookmarksTests"
    )
  ) dependsOn main
}
