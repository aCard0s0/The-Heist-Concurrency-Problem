echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Didércio Bucuane 83457\n"

echo "Compressing data to be sent to the GeneralRepositoryServer side node."
rm -rf GeneralRepositoryServer.zip
zip -rq GeneralRepositoryServer.zip client configurations entities mainProgram message server sharedRegions

echo "Transfering data to the GeneralRepositoryServer side node."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'mkdir -p teste/TheHeist'
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'rm -rf teste/TheHeist/*'
sshpass -f password scp GeneralRepositoryServer.zip sd0410@l040101-ws01.ua.pt:teste/TheHeist

echo "Decompressing data sent to the GeneralRepositoryServer side node."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cd teste/TheHeist ; unzip -q GeneralRepositoryServer.zip'

echo "Compiling program files at the GeneralRepositoryServer side node."
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cd teste/TheHeist ; javac */*.java'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws01.ua.pt 'cd teste/TheHeist ; java server.GeneralRepositoryServer'
