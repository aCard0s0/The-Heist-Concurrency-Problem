echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the MuseumServer side node."
rm -rf MuseumServer.zip
zip -rq MuseumServer.zip dir_ServerMuseum

echo "Transfering data to the MuseumServer side node."
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'rm -f MuseumServer.zip'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'rm -f MuseumServerSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'rm -rf dir_ServerMuseum/'
sshpass -f password scp MuseumServer.zip sd0410@l040101-ws04.ua.pt:.

echo "Decompressing data sent to the MuseumServer node."
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'unzip -q MuseumServer.zip'

echo "Compiling program files at the Registry side node."
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'cd dir_ServerMuseum; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'cp dir_ServerMuseum/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'cp dir_ServerMuseum/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws04.ua.pt 'cp dir_ServerMuseum/MuseumServerSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node."

sshpass -f password ssh sd0410@l040101-ws04.ua.pt './MuseumServerSide_com.sh'
