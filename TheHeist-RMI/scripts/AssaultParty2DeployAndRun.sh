echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the AssaultParty2 side node."
rm -rf ServerAssaultParty2.zip
zip -rq ServerAssaultParty2.zip dir_ServerAssaultParty2

echo "Transfering data to the AssaultParty2 side node."
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'rm -f ServerAssaultParty2.zip'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'rm -f AssParty2ServerSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'rm -rf dir_ServerAssaultParty2/'
sshpass -f password scp ServerAssaultParty2.zip sd0410@l040101-ws06.ua.pt:.

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'cp dir_ServerAssaultParty2/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'cp dir_ServerAssaultParty2/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws06.ua.pt 'cp dir_ServerAssaultParty2/AssParty2ServerSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node."
sshpass -f password ssh sd0410@l040101-ws06.ua.pt './AssParty2ServerSide_com.sh'
