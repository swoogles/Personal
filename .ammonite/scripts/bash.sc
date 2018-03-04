import ammonite.ops.{%, %%, Path}

def sudo(args: String*)(implicit wd: Path) = 
  %sudo(args)

def backgroundCommand(command: String)(implicit wd: Path) =
  %%bash("-c", s"$command &")

def backgroundSudoCommand(command: String)(implicit wd: Path) = {
  %("sudo", "echo", "Getting that sweet sudo access.")
  %bash("-c", s"sudo $command &")
}
