name := "ScalaUtils"

// advent project
lazy val advent = (project in file("."))
        .settings(Commons.settings: _*)
        .settings(
            libraryDependencies ++= Dependencies.common
        )