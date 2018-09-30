import XMonad
import XMonad.Config.Desktop
import XMonad.Hooks.SetWMName
import XMonad.Hooks.DynamicLog

import XMonad.Actions.TopicSpace
import XMonad.Actions.DynamicProjects

import XMonad.Prompt

import Control.Concurrent

import qualified Data.Map as M
import qualified XMonad.StackSet as W

launchIde :: X()
launchIde = spawn "/home/bill.frasure/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/182.4323.46/bin/idea.sh"

terminalProject = 
  Project { 
    projectName      = "terminal"
    , projectDirectory = "~/CmtRepositories/dev"
    , projectStartHook = Just $ do spawn "terminology"
  }


communicationProject = 
  Project { 
    projectName      = "communication"
    , projectDirectory = "~/Downloads"
    , projectStartHook = Just $ do spawn "wavebox"
  }

-- Figure out where I can stick this to deal with Intellij startup lag.
-- Concurrent.threadDelay 10
ideProject = 
  Project { 
      projectName      = "ide"
      , projectDirectory = "~/Downloads"
      , projectStartHook = Just $ do launchIde
  }

databaseProject = 
  Project { 
      projectName      = "database"
      , projectDirectory = "~/Downloads"
      , projectStartHook = Just $ do spawn "datagrip"
  }


browserProject = 
  Project { 
    projectName      = "browser"
    , projectDirectory = "~/Downloads"
    , projectStartHook = Just $ do spawn "firefox"
  }

musicProject = 
  Project { 
    projectName      = "music"
    , projectDirectory = "~/Downloads"
    , projectStartHook = Just $ do spawn "spotify"
  }

postmanProject = 
  Project { 
    projectName      = "api"
    , projectDirectory = "~/Downloads"
    , projectStartHook = Just $ do spawn "~/Postman/Postman"
  }

projects :: [Project]
projects =
  [ Project { projectName      = "scratch"
            , projectDirectory = "~/"
            , projectStartHook = Nothing
            }
  , terminalProject
  , browserProject
  , ideProject
  , communicationProject 
  , databaseProject
  , musicProject
  , postmanProject
  ]

myKeys x  = M.union (M.fromList (newKeys x)) (keys defaultConfig x)

spawnToWorkspace :: String -> String -> X ()
spawnToWorkspace program workspace = do
                                      spawn program     
                                      windows $ W.greedyView workspace

launchPostman :: X()
launchPostman = spawnToWorkspace "~/Postman/Postman" "7:postman"

openSprintBoard :: MonadIO m => m()
openSprintBoard = spawn "firefox https://jira.collectivemedicaltech.com/secure/RapidBoard.jspa?rapidView=69"

--{{{ Keybindings 
-- newKeys :: [((KeyMask, KeySym), X ())]
newKeys conf@(XConfig {XMonad.modMask = modm}) = [
  -- ((modm, xK_p), spawn "dmenu_run -nb '#3F3F3F' -nf '#DCDCCC' -sb '#7F9F7F' -sf '#DCDCCC'"),  --Uses a colourscheme with dmenu
  ((modm, xK_b), switchProject browserProject)
  , ((modm, xK_s), openSprintBoard)
  , ((modm, xK_i), switchProject ideProject)
  , ((modm, xK_d), switchProject databaseProject)
  , ((modm, xK_m), switchProject musicProject)
  , ((modm, xK_a), switchProject postmanProject)
  , ((modm, xK_t), switchProject terminalProject)
  -- , ((modm, xK_space), switchProjectPrompt)
  -- , ((modm, xK_slash), shiftToProjectPrompt)
  -- , ((modm , xK_l), spawn "gnome-power-statistics")
  , ((modm, xK_c), switchProject communicationProject  )
  -- ((modm, xK_f), spawn "urxvt -e mc"),
  -- ((0, xK_Print), spawn "scrot"),
  -- ((0, xF86XK_AudioMute), spawn "amixer -q set PCM toggle"),
  -- ((0, xF86XK_AudioRaiseVolume), spawn "amixer -q set PCM 2+"),
  -- ((0, xF86XK_AudioLowerVolume), spawn "amixer -q set PCM 2-"),
  -- ((0, xF86XK_AudioPlay), spawn "exaile -t"),
  -- ((0, xF86XK_AudioStop), spawn "exaile -s"),
   ]

-- TODO Consider deleting this, since I'm using DynamicWorkspaces now
onScr :: ScreenId -> (WorkspaceId -> WindowSet -> WindowSet) -> WorkspaceId -> X ()
onScr n f i = screenWorkspace n >>= \sn -> windows (f i . maybe id W.view sn)

configureForSwing :: X()
configureForSwing = setWMName "LG3D"

-- something =  xmonad $ dynamicProjects projects def

main = xmonad $ ( dynamicProjects projects desktopConfig {
  keys          = myKeys
  , startupHook = onScr 1 W.greedyView "web" <+> configureForSwing
  })

-- main = xmonad  =<< xmobar  desktopConfig { ... }
