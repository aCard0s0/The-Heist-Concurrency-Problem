echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Didércio Bucuane 83457"

echo "Compressing data to be sent to the MasterThiefClient side node.\n"
rm -rf MasterThiefClient.zip
zip -rq MasterThiefClient.zip dir_ClientMasterThief

echo "Transfering data to the MasterThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'rm -f MasterThiefClient.zip'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'rm -f MTclientSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'rm -rf dir_ClientMasterThief/'
sshpass -f password scp MasterThiefClient.zip sd0410@l040101-ws07.ua.pt:.

echo "Decompressing data sent to the MasterThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'unzip -q MasterThiefClient.zip'

echo "Compiling program files at the MasterThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cd dir_ClientMasterThief ; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cp dir_ClientMasterThief/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cp dir_ClientMasterThief/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cp dir_ClientMasterThief/MTclientSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws07.ua.pt './MTclientSide_com.sh'