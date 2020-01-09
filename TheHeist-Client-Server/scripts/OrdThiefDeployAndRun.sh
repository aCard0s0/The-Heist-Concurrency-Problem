echo ""
echo "	The Heist - Problem developer with Socket Comunication"
echo "		by André Cardoso 65069 &  Didércio Bucuane 83457\n"

echo "Compressing data to be sent to the OrdThiefClient side node."
rm -rf OrdThiefClient.zip
zip -rq OrdThiefClient.zip client configurations entities mainProgram message server sharedRegions

echo "Transfering data to the OrdThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'mkdir -p ot/TheHeist'
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'rm -rf ot/TheHeist/*'
sshpass -f password scp OrdThiefClient.zip sd0410@l040101-ws10.ua.pt:ot/TheHeist

echo "Decompressing data sent to the OrdThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'cd ot/TheHeist ; unzip -q OrdThiefClient.zip'

echo "Compiling program files at the OrdThiefClient side node."
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'cd ot/TheHeist ; javac */*.java'

sleep 1

echo "Executing program at the server side node.\n"
sshpass -f password ssh sd0410@l040101-ws10.ua.pt 'cd ot/TheHeist ; java mainProgram.OrdThiefMain'
