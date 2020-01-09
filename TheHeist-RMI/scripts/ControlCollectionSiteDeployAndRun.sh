echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the ControlCollectionSiteServer side node."
rm -rf ControlCollectionSiteServer.zip
zip -rq ControlCollectionSiteServer.zip dir_ServerControlCollectionSite

echo "Transfering data to the ControlCollectionSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'rm -f ControlCollectionSiteServer.zip'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'rm -f ConCollServerSide_com.sh'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'rm -rf dir_ServerControlCollectionSite/'
sshpass -f password scp ControlCollectionSiteServer.zip sd0410@l040101-ws03.ua.pt:.

echo "Decompressing data sent to the ControlCollectionSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'unzip -q ControlCollectionSiteServer.zip'

echo "Compiling program files at the ControlCollectionSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'cd dir_ServerControlCollectionSite ; javac */*.java'

echo "Copying file to the directories."
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'mkdir -p /home/sd0410/Public/classes'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'mkdir -p /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'mkdir -p /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'cp dir_ServerControlCollectionSite/interfaces/*.class /home/sd0410/Public/classes/interfaces'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'cp dir_ServerControlCollectionSite/structures/*.class /home/sd0410/Public/classes/structures'
sshpass -f password ssh sd0410@l040101-ws03.ua.pt 'cp dir_ServerControlCollectionSite/ConCollServerSide_com.sh /home/sd0410'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws03.ua.pt './ConCollServerSide_com.sh'