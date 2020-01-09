echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Didércio Bucuane 83457"

echo "Compressing data to be sent to the Ordinary Thief side node."
rm -rf ClientOrdinaryThief.zip
zip -rq ClientOrdinaryThief.zip dir_ClientOrdinaryThief

echo "Transfering data to the Ordinary Thief side node."
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'rm -f ClientOrdinaryThief.zip'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'rm -f OTclientSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'rm -rf dir_ClientOrdinaryThief/'
sshpass -f password scp ClientOrdinaryThief.zip sd0410@l040101-ws08.ua.pt:.

echo "Decompressing data sent to the ClientOrdinaryThief side node."
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'unzip -q ClientOrdinaryThief.zip'

echo "Compiling program files at the ClientOrdinaryThief side node."
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'cd dir_ClientOrdinaryThief ; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'cp dir_ClientOrdinaryThief/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'cp dir_ClientOrdinaryThief/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws08.ua.pt 'cp dir_ClientOrdinaryThief/OTclientSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the client side node."
sshpass -f password ssh sd0410@l040101-ws08.ua.pt './OTclientSide_com.sh'
