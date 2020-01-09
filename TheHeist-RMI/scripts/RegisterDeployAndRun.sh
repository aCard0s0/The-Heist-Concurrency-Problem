echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the Registry side node."
rm -rf Registry.zip
zip -rq Registry.zip dir_Registry

echo "Transfering data to the Register side node."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'rm -f Registry.zip'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'rm -f set_rmiregistry.sh'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'rm -f registry_com.sh'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'rm -rf dir_Registry/'
sshpass -f password scp Registry.zip sd0410@l040101-ws09.ua.pt:.

echo "Decompressing data sent to the Registry side node."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'unzip -q Registry.zip'

echo "Compiling program files at the Registry side node."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cd dir_Registry ; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cp dir_Registry/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cp dir_Registry/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cp dir_Registry/set_rmiregistry.sh /home/sd0410'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt 'cp dir_Registry/registry_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node."

sshpass -f password ssh sd0410@l040101-ws09.ua.pt './set_rmiregistry.sh'
sshpass -f password ssh sd0410@l040101-ws09.ua.pt './registry_com.sh'