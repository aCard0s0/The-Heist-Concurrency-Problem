echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the ConcentrationSiteServer side node."
rm -rf ConcentrationSiteServer.zip
zip -rq ConcentrationSiteServer.zip dir_ServerConcentrationSite

echo "Transfering data to the ConcentrationSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'rm -f ConcentrationSiteServer.zip'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'rm -f ConSiteServerSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'rm -rf dir_ServerConcentrationSite/'
sshpass -f password scp ConcentrationSiteServer.zip sd0410@l040101-ws02.ua.pt:.

echo "Decompressing data sent to the ConcentrationSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'unzip -q ConcentrationSiteServer.zip'

echo "Compiling program files at the ConcentrationSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'cd dir_ServerConcentrationSite ; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'cp dir_ServerConcentrationSite/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'cp dir_ServerConcentrationSite/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws02.ua.pt 'cp dir_ServerConcentrationSite/ConSiteServerSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws02.ua.pt './ConSiteServerSide_com.sh'