echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the ControlCollectionSiteServer side node."
rm -rf ControlCollectionSiteServer.zip
zip -rq ControlCollectionSiteServer.zip client configurations entities mainProgram message server sharedRegions

echo "Transfering data to the ControlCollectionSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'mkdir -p teste/TheHeist'
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'rm -rf teste/TheHeist/*'
sshpass -f password scp ControlCollectionSiteServer.zip sd0410@l040101-ws05.ua.pt:teste/TheHeist

echo "Decompressing data sent to the ControlCollectionSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cd teste/TheHeist ; unzip -q ControlCollectionSiteServer.zip'

echo "Compiling program files at the ControlCollectionSiteServer side node."
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cd teste/TheHeist ; javac */*.java'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws05.ua.pt 'cd teste/TheHeist ; java server.ControlCollectionSiteServer'
