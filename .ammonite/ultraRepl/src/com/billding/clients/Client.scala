package com.billding.clients

import ammonite.ops._
import ammonite.ops.ImplicitWd._

trait Client {
  def execute(args: String*): Unit
  def c(args: String*): Unit = execute(args: _*)
  def c(args: List[String]): Unit = execute(args: _*)
  def apply(args: String*): Unit =
    execute(args:_*)
}

class SubClient(parent: ClientBuilder) extends ClientBuilder {

}

class ClientBuilder(executables: String*) extends Client {
  def execute(args: String*): Unit = {
    println(executables.mkString("") + ":" + args.mkString(""))
    %(executables ++: args)
  }
    
  def subclient(subCommand: String): ClientBuilder =
    new ClientBuilder((executables :+ subCommand):_*)
}


