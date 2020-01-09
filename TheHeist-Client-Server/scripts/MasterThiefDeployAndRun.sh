echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Didércio Bucuane 83457"

echo "Compressing data to be sent to the MasterThiefClient side node.\n"
rm -rf MasterThiefClient.zip
zip -rq MasterThiefClient.zip client configurations entities mainProgram message server sharedRegions

echo "Transfering data to the MasterThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'mkdir -p mt/TheHeist'
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'rm -rf mt/TheHeist/*'
sshpass -f password scp MasterThiefClient.zip sd0410@l040101-ws10.ua.pt:mt/TheHeist

echo "Decompressing data sent to the MasterThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'cd mt/TheHeist ; unzip -q MasterThiefClient.zip'

echo "Compiling program files at the MasterThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'cd mt/TheHeist ; javac */*.java'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'cd mt/TheHeist ; java mainProgram.MasterThiefMain'
