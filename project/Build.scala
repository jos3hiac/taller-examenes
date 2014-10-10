import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "examen"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "mysql" % "mysql-connector-java" % "5.1.25"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    //resourceGenerators in Compile <+= JavascriptCompiler(fullCompilerOptions =None),
    //javascriptEntryPoints <<= (sourceDirectory in Compile)(base => ((base / "assets" ** "*.js") --- (base / "assets" ** "_*")))
    //javascriptEntryPoints <<= (sourceDirectory in Compile)(base => ((base / "assets" / "js" ** "*.js")))    
  )

}