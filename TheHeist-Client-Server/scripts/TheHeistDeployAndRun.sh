xterm  -T "General Repository Server"   -hold -e "./GeneralRepositoryDeployAndRun.sh" &

sleep 3

xterm  -T "Concentration Site Server"   -hold -e "./ConcentrationSiteDeployAndRun.sh" &
xterm  -T "Control Collection Server"   -hold -e "./ControlCollectionSiteDeployAndRun.sh" &
xterm  -T "AssaultParty 1 Server"       -hold -e "./AssaultParty1DeployAndRun.sh" &
xterm  -T "AssaultParty 2 Server"       -hold -e "./AssaultParty2DeployAndRun.sh" &
xterm  -T "Museum Server"               -hold -e "./MuseumDeployAndRun.sh" &

sleep 2

xterm  -T "Ordinary Thieves Client" -hold -e "./OrdThiefDeployAndRun.sh" &

sleep 2

xterm  -T "Master Thief Client"     -hold -e "./MasterThiefDeployAndRun.sh" &