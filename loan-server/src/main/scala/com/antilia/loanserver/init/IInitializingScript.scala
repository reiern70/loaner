package com.antilia.loanserver.init

/**
 * Scripts to fo initialization task.
 */
trait IInitializingScript extends Serializable {
  /**
   * execute the scripts.
   */
  def execute(): Unit

  /**
   * Unique ID identifying the script.
   *
   * @return
   */
  def getId: String

  /**
   * If executions of script define after this should happen if
   * execution of this script fails.
   * @return
   */
  def shouldContinueOnError: Boolean
}
