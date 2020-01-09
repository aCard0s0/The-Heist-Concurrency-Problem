echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the AssaultParty1 side node."
rm -rf ServerAssaultParty1.zip
zip -rq ServerAssaultParty1.zip dir_ServerAssaultParty1

echo "Transfering data to the AssaultParty1 side node."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'rm -f ServerAssaultParty1.zip'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'rm -f AssParty1ServerSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'rm -rf dir_ServerAssaultParty1/'
sshpass -f password scp ServerAssaultParty1.zip sd0410@l040101-ws05.ua.pt:.

echo "Compiling program files at the AssaultParty1 side node."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cd dir_ServerAssaultParty1; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cp dir_ServerAssaultParty1/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cp dir_ServerAssaultParty1/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cp dir_ServerAssaultParty1/AssParty1ServerSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt './AssParty1ServerSide_com.sh'
