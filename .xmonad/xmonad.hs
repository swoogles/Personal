import XMonad
import XMonad.Config.Desktop
import XMonad.Hooks.SetWMName
import XMonad.Hooks.DynamicLog

import Control.Concurrent

import qualified Data.Map as M
import qualified XMonad.StackSet as W

myKeys x  = M.union (M.fromList (newKeys x)) (keys defaultConfig x)

spawnToWorkspace :: String -> String -> X ()
spawnToWorkspace program workspace = do
                                      spawn program     
                                      windows $ W.greedyView workspace

launchCommunication :: X()
launchCommunication = spawnToWorkspace "wavebox" "4:communication"

-- Figure out where I can stick this to deal with Intellij startup lag.
-- Concurrent.threadDelay 10

launchIde :: X()
launchIde = spawnToWorkspace "/home/bill.frasure/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/182.4323.46/bin/idea.sh" "2:ide"

launchDatagrip :: X()
launchDatagrip = spawnToWorkspace "datagrip" "6:database"

launchPostman :: X()
launchPostman = spawnToWorkspace "~/Postman/Postman" "7:postman"

openSprintBoard :: MonadIO m => m()
openSprintBoard = spawn "firefox https://jira.collectivemedicaltech.com/secure/RapidBoard.jspa?rapidView=69"

--{{{ Keybindings 
--    Add new and/or redefine key bindings
-- TODO Figure out type here...
-- newKeys :: [((KeyMask, KeySym), X ())]
newKeys conf@(XConfig {XMonad.modMask = modm}) = [
  -- ((modm, xK_p), spawn "dmenu_run -nb '#3F3F3F' -nf '#DCDCCC' -sb '#7F9F7F' -sf '#DCDCCC'"),  --Uses a colourscheme with dmenu
  ((modm, xK_b), spawn "firefox")
  , ((modm, xK_s), openSprintBoard)
  , ((modm, xK_i), launchIde)
  , ((modm, xK_c), launchCommunication)
  , ((modm, xK_d), launchDatagrip)
  , ((modm, xK_a), launchPostman)
  , ((modm , xK_l), spawn "gnome-power-statistics")
  , ((mod4Mask, xK_h), windows $ W.greedyView "1:terminal")
  , ((mod4Mask, xK_j), windows $ W.greedyView "2:ide")
  , ((mod4Mask, xK_k), windows $ W.greedyView "3:browser")
  , ((mod4Mask, xK_l), windows $ W.greedyView "4:communication")
  , ((mod4Mask, xK_semicolon), windows $ W.greedyView "5:music")
  , ((mod4Mask, xK_d), windows $ W.greedyView "6:database")
	
  -- , ((xK_Super_L , xK_h), W.shift, 1)
  -- ((modm, xK_f), spawn "urxvt -e mc"),
  -- ((modm, xK_g), spawn "chromium --app='https://www.nirvanahq.com/app'"),
  -- ((0, xK_Print), spawn "scrot"),
  -- ((modm, xK_v), spawn "VirtualBox"),
  -- ((modm, xK_z), goToSelected myGSConfig),
  -- ((0, xF86XK_AudioMute), spawn "amixer -q set PCM toggle"),
  -- ((0, xF86XK_AudioRaiseVolume), spawn "amixer -q set PCM 2+"),
  -- ((0, xF86XK_AudioLowerVolume), spawn "amixer -q set PCM 2-"),
  -- ((0, xF86XK_AudioPlay), spawn "exaile -t"),
  -- ((0, xF86XK_AudioStop), spawn "exaile -s"),
  -- ((modm, xK_i), sendMessage MirrorExpand)
   ]
--}}}

myWorkspaces = ["1:terminal", "2:ide", "3:browser", "4:communication", "5:music", "6:database", "7:postman"]

onScr :: ScreenId -> (WorkspaceId -> WindowSet -> WindowSet) -> WorkspaceId -> X ()
onScr n f i = screenWorkspace n >>= \sn -> windows (f i . maybe id W.view sn)

-- Needed for opening Swing applications, namely Intellij

configureForSwing :: X()
configureForSwing = setWMName "LG3D"

main = xmonad =<< xmobar  desktopConfig {
  keys          = myKeys
  , workspaces = myWorkspaces
  , startupHook = onScr 1 W.greedyView "web" <+> configureForSwing
  }

--   where
--     mykeys (XConfig {modMask = modm}) = M.fromList $
--          [ ((modm , xK_x), spawn "gnome-power-statistics")
--          , ((xK_Super_L , xK_h), W.shift, 1) ]


