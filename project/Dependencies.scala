import sbt._

object Dependencies {

    // Modules
    val reflection: Seq[ModuleID] = Seq(
        "org.reflections" % "reflections"   % "0.9.10",
        "org.scala-lang"  % "scala-reflect" % "2.11.5"
    )
    val apache:     ModuleID = "org.apache.commons" %  "commons-io"           % "1.3.2"
    val scalaz:     ModuleID = "org.scalaz"         %% "scalaz-core"          % "7.2.7"
    val javax:      ModuleID = "javax.inject"       %  "javax.inject"         % "1"

    // Common Modules
    val common: Seq[ModuleID] = Seq(
        apache,
        scalaz,
        javax
    ) ++ reflection
}