package scalafix.sbt

import sbt._
import sbt.Keys._

object ScalafixCodegenPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = noTrigger
  override def requires: Plugins = ScalafixPlugin

  object autoImport {
    val scalafixCodegenRule: SettingKey[String] =
      settingKey[String](
        "The rule used for code generation."
      )

    val scalafixCodegenDestination: SettingKey[SettingKey[Seq[Task[Seq[File]]]]] =
      settingKey[SettingKey[Seq[Task[Seq[File]]]]](
        "Destination project and scope for generated code."
      )

    val scalafixCodegen: TaskKey[Seq[File]] =
      taskKey[Seq[File]](
        "Generate code using scalafix."
      )
  }

  import autoImport._

  override lazy val projectSettings: Seq[Setting[_]] = Def.settings(
    inConfig(Compile)(
      Seq[Setting[_]](
        Def.settingDyn {
          Keys.sourceGenerators += scalafixCodegen.taskValue
        }
      )
    )
  )

}
