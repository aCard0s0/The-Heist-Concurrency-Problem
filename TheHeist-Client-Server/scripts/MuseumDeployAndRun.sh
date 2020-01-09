echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Dércio Bucuane 83457\n"

echo "Compressing data to be sent to the MuseumServer side node."
rm -rf MuseumServer.zip
zip -rq MuseumServer.zip client configurations entities mainProgram message server sharedRegions

echo "Transfering data to the MuseumServer side node."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'mkdir -p teste/TheHeist'
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'rm -rf teste/TheHeist/*'
sshpass -f password scp MuseumServer.zip sd0410@l040101-ws07.ua.pt:teste/TheHeist

echo "Decompressing data sent to the MuseumServer side node."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cd teste/TheHeist ; unzip -q MuseumServer.zip'

echo "Compiling program files at the MuseumServer side node."
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cd teste/TheHeist ; javac */*.java'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws07.ua.pt 'cd teste/TheHeist ; java server.MuseumServer'
